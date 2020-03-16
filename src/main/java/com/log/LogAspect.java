package com.log;

import com.alibaba.fastjson.JSON;
import com.cache.ICache;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
@Aspect
@Order(0)
public class LogAspect {

    private static final String START_TIME = "startTime";
    private static final String REQUEST_PARAMS = "requestParams";
    //    private ThreadLocal<Map> threadLocal = new ThreadLocal<>();---直接初始化*** map也是这种逻辑
    private ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<Map<String, Object>>(); /*{  //暂时用不到
        public Map initMap() {
            return new HashMap();
        }
    };*/
    private static final Logger logger = LoggerFactory.getLogger(LogMain.class);
    private static final String DElIMITER = ",";
    private static final String NAME = "name";


    @Pointcut("execution(* com.log..*.*(..))")
    public void LogPoint() {
    }


    @Before("@annotation(log)")/*@Before(value = "LogPoint()& &  @annotation(Log)")*/ //TODO:这里使用&&进行绑定 引入
    public void doBefore(JoinPoint joinPoint, Log log) {
        // 开始时间。
        long startTime = System.currentTimeMillis();
        Map<String, Object> threadInfo = new HashMap<>();
        threadInfo.put(START_TIME, startTime);
        // 请求参数。
        StringBuilder requestStr = new StringBuilder();

        System.out.println("getSignature() :" + joinPoint.getSignature());       //String com.log.LogMain.doAspect(String, String)
        System.out.println("getKind() :" + joinPoint.getKind());                  //method-call
        System.out.println("getTarget() :" + joinPoint.getTarget());              //     com.log.LogMain@4b033eac
        System.out.println("getSourceLocation() :" + joinPoint.getSourceLocation()); //LogTest.java:20
        System.out.println("getThis():" + joinPoint.getThis());                    //com.log.LogTest@69c532af
        System.out.println("getStaticPart():" + joinPoint.getStaticPart());       //call(String com.log.LogMain.doAspect(String, String))
        System.out.println("toShortString():" + joinPoint.toShortString());       //execution(LogMain.doAspect(..))
        System.out.println("toLongString():" + joinPoint.toLongString());         //execution(public java.lang.String com.log.LogMain.doAspect(java.lang.String, java.lang.String))
        System.out.println("toString():" + joinPoint.toString());                  //execution(String com.log.LogMain.doAspect(String, String))

        /**
         *   ServletRequestAttributes attributes =
             (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
             HttpServletRequest request = attributes.getRequest();
             logger.info(request.getRequestURL().toString()); //http://127.0.0.1:8080/hello/getName
             logger.info(request.getRemoteAddr()); //127.0.0.1
             logger.info(request.getMethod()); //GET

         */


        /**
         *  this与target
              this与target在用法上有些重合，理解上有对比性。
               this表示当前切入点表达式所指代的方法的对象的实例，即代理对象是否满足this类型
               target表示当前切入点表达式所指代的方法的目标对象的实例   即是否是为target类做的代理
             如果当前对象生成的代理对象符合this指定的类型，则进行切面，target是匹配业务对象为指定类型的类，则进行切面。
              生成代理对象时会有两种方法，一个是CGLIB一个是jdk动态代理。
               用下面三个例子进行说明：     
         this(SomeInterface)或target(SomeInterface)：这种情况下，无论是对于Jdk代理还是Cglib代理，其目标对象和代理对象都是实现SomeInterface接口的（Cglib生成的目标对象的子类也是实现了SomeInterface接口的），因而this和target语义都是符合的，此时这两个表达式的效果一样；
         this(SomeObject)或target(SomeObject)，这里SomeObject没实现任何接口：这种情况下，Spring会使用Cglib代理生成SomeObject的代理类对象，由于代理类是SomeObject的子类，子类的对象也是符合SomeObject类型的，因而this将会被匹配，而对于target，由于目标对象本身就是SomeObject类型，因而这两个表达式的效果一样；
         this(SomeObject)或target(SomeObject)，这里SomeObject实现了某个接口：对于这种情况，虽然表达式中指定的是一种具体的对象类型，但由于其实现了某个接口，因而Spring默认会使用Jdk代理为其生成代理对象，Jdk代理生成的代理对象与目标对象实现的是同一个接口，但代理对象与目标对象还是不同的对象，由于代理对象不是SomeObject类型的，因而此时是不符合this语义的，而由于目标对象就是SomeObject类型，因而target语义是符合的，此时this和target的效果就产生了区别；这里如果强制Spring使用Cglib代理，因而生成的代理对象都是SomeObject子类的对象，其是SomeObject类型的，因而this和target的语义都符合，其效果就是一致的。
         */

        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            for (Object arg : args) {
//                if(arg.){
//                  不能获得参数列表 只能获得完成的方法。截取
//                }
                requestStr.append(arg.toString()).append(DElIMITER);
            }
        }
        threadInfo.put(NAME, StringUtils.isEmpty(log.name()) ? joinPoint.getSignature().toShortString() : log.name());
        threadInfo.put(REQUEST_PARAMS, requestStr.deleteCharAt(requestStr.length() - 1).toString());
        threadLocal.set(threadInfo);
//        (Map)(threadInfo.get(REQUEST_PARAMS))
//        String requestData = "";
//        for (Map.Entry entry :(Map.Entry[]) threadInfo.get(REQUEST_PARAMS)) {    -  lamada...
//            requestData+=entry.
//        }
        logger.info("{} interface begin voik :requestData={}", threadInfo.get(NAME) /*StringUtils.isEmpty(log.name() )?joinPoint.getSignature().toShortString():log.name()*/, threadInfo.get(REQUEST_PARAMS));
    }


    /**
     * log 用来bundle ,传递规则，传递信息
     *
     * @param log
     * @param res
     */

    @AfterReturning(value = "@annotation(log)", returning = "res")
    public void doAfterReturning(Log log, Object res) {
        Map<String, Object> threadInfo = threadLocal.get();
        long takeTime = System.currentTimeMillis() - (long) threadInfo.getOrDefault(START_TIME, System.currentTimeMillis());
        if (log.intoDb()) {
            insertResult(threadInfo.get(NAME), (String) threadInfo.getOrDefault(REQUEST_PARAMS, ""),
                    JSON.toJSONString(res), takeTime);
        }
        threadLocal.remove();
        logger.info("{} interface invoke ={}ms,result={}", threadInfo.get(NAME),
                takeTime, res);
    }

    private void insertResult(Object o, String orDefault, String s, long takeTime) {
        System.out.println("insert done successfully");
    }

    @AfterThrowing(value = "@annotation(log)", throwing = "throwable")
    public void doAfterThrowing(Log log, Throwable throwable) {
        Map<String, Object> threadInfo = threadLocal.get();
        if (log.intoDb()) {
            insertError(log.name(), (String) threadInfo.getOrDefault(REQUEST_PARAMS, ""),
                    throwable);
        }
        threadLocal.remove();
        logger.error("{}接口调用异常，异常信息{}", log.name(), throwable);
    }

    private void insertError(String name, String orDefault, Throwable throwable) {
    }

}






//TODO---------------------------------------下面内容是对aspect演进梳理--------------------------------

/*


*/
/*@author YangWenjun
* @date 2019/11/23 11:30
* @project MockFramework
*           ---日志是重要的，好的项目日志是清晰明了的；定位问题是迅速的。控制台，日志文件---
         2019-11-23 11:56:57.117 ERROR 15552 --- [   main] TestRulerDraft : ---测试数据传入---
*
* @title:         1.日志aop逻辑
*                 2.日志util在每个实现类中具体实现。也可以抽象
*
*
*
/**
 * xml配置方式
 *       <aop:config>
             <aop:pointcut id="loggerCutpoint"
             expression=
                 "execution(* com.how2java.service.ProductService.*(..)) "/>
                 <aop:aspect id="logAspect" ref="loggerAspect">
                 <aop:after pointcut-ref="loggerCutpoint" method="log"/>
             </aop:aspect>
         </aop:config>

    --使用场景--
             logging, tracing, profiling and monitoring　记录跟踪　优化　校准
             Error handling 错误处理
             Caching 缓存
             Authentication 权限 - validate
             Context passing 内容传递
             Lazy loading　懒加载
             Debugging　　调试
             Performance optimization　性能优化
             Persistence　　持久化
             Resource pooling　资源池
             Synchronization　同步
             Transactions 事务
        --TODO:具体实现  就类似于这里的log aspect 会涉及到 反射/注解/泛型

    --底层--
           TransactionInterceptor
           BeanNameAutoProxyCreator

* *//*


import com.log.annotation.AfterLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


    @Aspect
    @Component
    public class LogAspect {

        //@Autowired
        */
/* private  LogService logService ;*//*

        //private Logger logger = LoggerFactory.getLogger(LogAspect.class);
        //前置通知
        @Before("execution(* seckill.controller.UserController.getTest(..))")
        public void before(JoinPoint joinPoint) throws ClassNotFoundException {
            System.out.println("---前置通知执行---");
            System.out.println(joinPoint.getArgs()[0]);
            System.out.println(joinPoint.getSignature() + "  this is signature----");

            */
/**
 * 融合 reflect 获取类信息，进一步从切面进行信息传递
 * 1.JoinPoint对象封装了SpringAop中切面方法的信息,在切面方法中添加JoinPoint参数,就可以获取到封装了该方法信息的JoinPoint对象.
 常用api:
 方法名	功能
 Signature getSignature();	获取封装了署名信息的对象,在该对象中可以获取到目标方法名,所属类的Class等信息
 Object[] getArgs();	获取传入目标方法的参数对象
 Object getTarget();	获取被代理的对象
 Object getThis();	获取代理对象
 *//*

            // 拿到切点的类名、方法名、方法参数
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            Object[] args = joinPoint.getArgs();
            // 反射加载切点类，遍历类中所有的方法
            Class<?> targetClass = Class.forName(className);
            Method[] methods = targetClass.getMethods();
            for (Method method :
                    methods) {
                // 如果遍历到类中的方法名与切点的方法名一致，并且参数个数也一致，就说明切点找到了 -- 有漏洞
                */
/***反射中方法使用，以及对方法的访问***//*

                if (method.getName().equalsIgnoreCase(methodName)) {
                    Class<?>[] clazzs = method.getParameterTypes();
                    if (clazzs.length == args.length) {
                        // 获取到切点上的注解
                        AfterLog logAnnotation = method.getAnnotation(AfterLog.class);
                        if (logAnnotation != null) {
                            // 获取注解中的日志信息，并输出
                            String logStr = logAnnotation.description();
                            //logger.error("获取日志：" + logStr);
                            // 数据库记录操作...
                            break;
                        }
                    }
                }
            }

            */
/***
 *ProceedingJoinPoint对象
 ProceedingJoinPoint对象是JoinPoint的子接口,该对象只用在@Around的切面方法中,
 添加了
 Object proceed() throws Throwable //执行目标方法
 Object proceed(Object[] var1) throws Throwable //传入的新的参数去执行目标方法
 两个方法.
 *
 *
 *//*

        }

       @Around("execution(* seckill.controller.UserController.getTest(..))")
        public Object aroundMethod(ProceedingJoinPoint pjd) {
            Object result = null;

            try {
                //前置通知
                System.out.println("目标方法执行前...");
                //执行目标方法
                //result = pjd.proeed();
                //用新的参数值执行目标方法
                result = pjd.proceed(new Object[]{"newSpring", "newAop"});
                //返回通知
                System.out.println("目标方法返回结果后...");
            } catch (Throwable e) {
                //异常通知
                System.out.println("执行目标方法异常后...");
                throw new RuntimeException(e);
            }
            //后置通知
            System.out.println("目标方法执行后...");

            return result;
        }

    }

    ;

    */
/***
 * 同一个切入点只能是一个吗？
 *//*

    //后置通知
    */
/*@AfterReturning("execution(* com.secKill.controller.UserController.getTest(..))")
    public void afterReturning(){
        System.out.println("---后置通知执行---");
    };
    //环绕通知
    @Around("execution(* com.secKill.controller.UserController.getTest(..))")
    public void around(){
        System.out.println("---环绕通知执行---");
    };
    //异常通知
    @AfterThrowing("execution(* com.secKill.controller.UserController.getTest(..))")
    public void afterThrowing(){
        System.out.println("---异常通知执行---");
    };
    //最终通知
    @Before("execution(* com.secKill.controller.UserController.getTest(..))")
    public void after(){
        System.out.println("---最终通知执行---");
    };*//*

    //自定义日志注解处理，如何触发：将该方法放到类似于@before等hook下，在调用时触发该方法


*/
