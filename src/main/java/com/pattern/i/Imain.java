package com.pattern.i;

/**
 *0.背景
 *      1.简单的使用if判断和数据库保存状态 vs 状态机
             规模化，复杂化就这些才会出现。比如我可可以简单的if而不用状态机也不用jbpm。
             如果流程围绕某个事物的状态变化进行，显而易见，该轮到状态机图上场了。一个状态机图中只描述一个事物，该事物有多个状态，不同的动作作用到状态上导致状态的转换。
             并不是所有的流程都是以状态流转为主的。比如这里的自己处理，调用系统a，，，等。复合在一起。这里自身的状态塞值就可以使用状态机。

 *      2.状态机为每种状态新建类是否造成臃肿
 *          变种？
 *      3.能不能通俗的讲解下什么是状态机？https://www.zhihu.com/tardis/sogou/art/84642629  https://www.zhihu.com/tardis/sogou/ans/1046397412
 *        状态机编程实例及适用范围：https://blog.csdn.net/shuzhe66/article/details/26154891?locationNum=10&fps=1
 *      4.jBPM最佳实践
 *              (jBPM Best Practices):https://blog.csdn.net/weixin_33725272/article/details/89866848
 *      5.注册中心对比 https://blog.csdn.net/kuaipao19950507/article/details/103449705
 * 1.状态模式 - spring状态机 - bbsp状态机 - 登记中心
 */
public class Imain {



}
