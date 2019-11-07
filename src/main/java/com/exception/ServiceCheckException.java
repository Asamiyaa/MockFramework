package com.exception;

/**
 * @author YangWenjun
 * @date 2019/11/7 18:21
 * @project MockFramework
 * @title: ServiceCheckException
 * @description:  alt+enter  直接重写父类
 */
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
