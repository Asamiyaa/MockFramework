package com.exception;

/**
 * @author YangWenjun
 * @date 2019/11/7 18:21
 * @project MockFramework
 * @title: ServiceCheckException
 * @description:  alt+enter  直接重写父类   -> 自定义异常就是为了抛出时，自定义异常直接让客户端明确，而无需详细看cause
 *                 这种直接重写+super就是为了继承父类的 throw 体系特性


            //throw new ServiceCheckException("核对不正确");
            try {
            s.toUpperCase();
            } catch (Exception e) {
            ServiceCheckException ex = new ServiceCheckException("核对不正确",e);
            throw  ex ;
            }

 */
//TODO:根据业务场景自己定义抛出，而不是依赖调用别人的抛出  -- 优先使用公共的已有异常
public class ServiceCheckException extends Exception {

    private static final long serialVersionUID = -1L;

    public ServiceCheckException() {
    }

    public ServiceCheckException(String message) {
        super(message);
    }

    public ServiceCheckException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceCheckException(Throwable cause) {
        super(cause);
    }

    public ServiceCheckException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
