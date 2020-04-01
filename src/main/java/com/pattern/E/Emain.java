package com.pattern.E;

/**服务定位  -- 哪些规则是强制、哪些是client可以扩展的、合理性、哪些需要直接定义成常量写死还是通过更高合理抽象维护其关联性
 * 1.map
 * 2.反射
 *       1.全限定类名   2.类型.class   3.对象.getClass
 * 3.服务定位器模式        https://www.runoob.com/design-pattern/service-locator-pattern.html
 *          其中包括：缓存、定位器。用于分布系统，模块之间；不直接有代码关联。比如消息返回后从报文解析得到的名字和处理器子类实现关联
 * 4.dispatcher 委派模式  https://www.jianshu.com/p/38acf37b1e1f  屏蔽了调用方和实现方，中间有leader-dispatcher
 *
 * ---自己思考的mapper和mapperObj本身生成时就是通过插件自定义的。所以这里没有固定而言。全限定类名。
 *    所以说通过代码是不可以实现的。那么只能去记录或者映射。方法1-4
 */
public class Emain {


}
