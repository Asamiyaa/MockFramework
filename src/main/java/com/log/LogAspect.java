package com.log;

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
             Authentication 权限
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

* */

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

        @Autowired
        /* private  LogService logService ;*/
        //private Logger logger = LoggerFactory.getLogger(LogAspect.class);
        //前置通知
        //@Before("execution(* com.secKill.controller.UserController.getTest(..))")
        public void before(JoinPoint joinPoint) throws ClassNotFoundException {
            System.out.println("---前置通知执行---");
            System.out.println(joinPoint.getArgs()[0]);
            System.out.println(joinPoint.getSignature() + "  this is signature----");

            /**
             * 融合 reflect 获取类信息，进一步从切面进行信息传递
             * 1.JoinPoint对象封装了SpringAop中切面方法的信息,在切面方法中添加JoinPoint参数,就可以获取到封装了该方法信息的JoinPoint对象.
             常用api:
             方法名	功能
             Signature getSignature();	获取封装了署名信息的对象,在该对象中可以获取到目标方法名,所属类的Class等信息
             Object[] getArgs();	获取传入目标方法的参数对象
             Object getTarget();	获取被代理的对象
             Object getThis();	获取代理对象
             */
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
                /***反射中方法使用，以及对方法的访问***/
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

            /***
             *ProceedingJoinPoint对象
             ProceedingJoinPoint对象是JoinPoint的子接口,该对象只用在@Around的切面方法中,
             添加了
             Object proceed() throws Throwable //执行目标方法
             Object proceed(Object[] var1) throws Throwable //传入的新的参数去执行目标方法
             两个方法.
             *
             *
             */
        }

        @Around("execution(* com.secKill.controller.UserController.getTest(..))")
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

    /***
     * 同一个切入点只能是一个吗？
     */
    //后置通知
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
    };*/
    //自定义日志注解处理，如何触发：将该方法放到类似于@before等hook下，在调用时触发该方法


