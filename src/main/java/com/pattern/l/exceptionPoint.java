package com.pattern.l;

/**
 *
 *   1.如何处理异常合理-极客
     2.异常传递信息vs返回值VS父类msg进行判断。commonvalidator。了解要的点
     3.异常到底捕获还是跑出。上层知道处理，没有默认值，严重
     4.将对其他模块调用返回进行太try。。cstch。全局只是兜底。代码异常处理还是老样子 VS 不允许捕获异常。全部到controller
         https://blog.csdn.net/dinglang_2009/article/details/93346333
         由于一堆健壮的try-catch和空判断，导致问题发现很晚，可能很小一个问题最后变成了一个大事件，在一些IT系统里面，尤其常见。
         更加不会出现前台返回成功，后台有异常什么也没有做的场景

         当存在空可以不处理，继续那也就是a.xx时才需要判空，如果是空一定不可以做。及是空return。不影响主流程。。如果需要调用着感知，则不判断能有啥报错。运行时异常直接往外抛
         https://zhuanlan.zhihu.com/p/29005176
     根据业务逻辑定于借口时，判断是否要抛出异常
 *
 */
public class exceptionPoint {
}
