 package com.pattern.f;

import com.cache.ICache;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;

/***
 *
 *资源获取
 *      从Emain中定位问题衍生而来。
 *
      *1.容器分类和扩展 - map
           tomcat - spring - session - thread-threadlocal-inhiriteThreadLocal-多线程下的threadlocal 
           自定义的Holder - redis分布式下中心上下文
               1.applicationHolder :
                一般的应用场景即是上面所示的bean的工厂。以上面的类为例，我们可以通过SpringBeanFactory.getBean("myBean", MyBean.class)来获取bean。这使得在一些无法注入的地方我们也可以获取到bean，比如 ‘ Util ’类中，为我们带来极大的便利。工具类中使用spring特性
                转化为静态引用，脱离spring.这就是中间加了一层‘ 适配 ’。。一方是spring/一方是工具类 。 
                https://blog.csdn.net/nlznlz/article/details/102829094

       2.上下文思考
            1.jbpm作为老式流程框架，如何传递 : jBPM是一个流程+状态机+前台展示+持久化节点(事物上下文)  vs 
            2.上下界如何做到合理范围，不臃肿
       3.当使用流程优化的时候，除了对流程过程，取值优化；借助上下文同样可以减少操作
       4.指导：
             1.cacheHolder  流程较长时，对步骤中的值暂存，后续的流程中某个节点可能会用到。暂存 vs 直接参数传递--直接调用紧接着。上下文
              解决这种不挨着又需要的情况
             2.上下文对象的主要目标是以独立于协议的方式共享系统信息，从而提高应用程序的可重用性和可维护性。
               在分层体系结构中，如果我们想要跨不同的系统层共享系统信息
               https://www.jdon.com/51975
             3.设计一个自定义的系统上下文 ： https://juejin.cn/post/6844904166356156429
               SOFABolt 源码分析16 - 上下文机制的设计 : https://www.jianshu.com/p/0ec5026c57bf
             4.上下文对象如何初始化和传递？

                 1.通过工具类 内部有threadlocal<map>..工具类手动塞取。

                 2.调用链系列四：调用链上下文传递：https://juejin.cn/post/6844903795571294215
 
                 3.父类abstractxxx/basexxx  -
                   存在问题，在多线程的情况下，abstract成员安全。
                   参考spring的AbstractApplicationContext中的成员 1.提供private成员  2.通过构造来实例化，而不是属性=new xx 属性初始化
                 spring源码中的AbstractApplicationContext ，初始化成员，因为每个子类初始化的时候都会调用这个部分。
                  public AbstractApplicationContext() {
                       this.logger = LogFactory.getLog(this.getClass());
                       this.id = ObjectUtils.identityToString(this);
                       this.displayName = ObjectUtils.identityToString(this);
                       this.beanFactoryPostProcessors = new ArrayList();
                       this.active = new AtomicBoolean();
                       this.closed = new AtomicBoolean();
                       this.startupShutdownMonitor = new Object();
                       this.applicationListeners = new LinkedHashSet();
                       this.resourcePatternResolver = this.getResourcePatternResolver();
                   }

 *
 *      ----参考自：*** 为什么那么多框架都设计有Context?(https://www.zhihu.com/question/269905592?utm_source=wechat_session&utm_medium=social&utm_oi=820973752312545280) *******
 *          1.现场保存  cpu切换
 *          2.多模块间数据传递，交互( 模块解耦权衡，因为相互之间肯定有联系，如何联系呢？问题：倘若分布式部署，都不是一个spring容器如何处理呢？集群部署呢？ )
 *                  所以如何合理的选择局部变量、成员、静态、threadLocal..以及context...是对整个的考虑(尽量减少范围、尽量不适用多线程)，不建议用户代码对context处理，由框架完成
 *                  生命周期 lifestyle
 *                  哪些信息放到spring容器中呢?哪些又不放呢？
 *          3.上下文是依赖注入的使用  上帝模式
 *
 *     ----常见结构
 *          1.http head/body
 *                  https://blog.csdn.net/u010256388/article/details/68491509
 *          2.pjs xml
 *                  1-191 head / body head中第几个字节是代码什么...进行对应解析，body schema....映射 ‘ 对应对象 ’，因为head肯定是公用的，所以抽取出来
 *          3.统一返回
 *                  1.head - 成功标识、失败码、流水号、..../body
 *
 *
 *
 */
public class Fmain {
    @Autowired
    private ApplicationContext applicationContext;

//    获取指定注解所有的 Bean
    Map<String,Object> objectMap = applicationContext.getBeansWithAnnotation(Service.class);
//    获取指定注解所有的 Bean 的名字
    String[] beanNames = applicationContext.getBeanNamesForAnnotation(Service.class);        //通过spring 直接  @Autowire list<Service> selist 机制可以注入
//    获取容器中指定某类型、或实现某接口、或继承某父类所有的 Bean
    Map<String, Test> objectMap2= applicationContext.getBeansOfType(Test.class);
//    获取容器中指定某类型、或实现某接口、或继承某父类所有的 Bean 的名称
    String[] beanNames2 = applicationContext.getBeanNamesForType(Service.class);
    Test testbean = (Test)applicationContext.getBean("testbean");
//    获取指定名字、类型的 Bean，指定的类型可以是其父类或所实现的接口
    Object object = applicationContext.getBean("testbean", ICache.class);
//    获取指定类型、或接口、或某类的子类的 Bean
    Object object2 = applicationContext.getBean(ICache.class);
    int beanCount = applicationContext.getBeanDefinitionCount();
    String[] beanNames3 = applicationContext.getBeanDefinitionNames();


}

@EnableAsync
@Component
 class SpringContextUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext; // Spring应用上下文环境

    /*
     *
     * 实现了ApplicationContextAware 接口，必须实现该方法；
     *
     * 通过传递applicationContext参数初始化成员变量applicationContext
     *
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static <T> T getBean(Class<T> requiredType) {
        return (T) applicationContext.getBean(requiredType);
    }

    public static void publishEvent(ApplicationEvent event) {
        applicationContext.publishEvent(event);
    }
}
