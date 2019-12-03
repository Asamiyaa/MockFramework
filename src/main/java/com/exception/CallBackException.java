package com.exception;

/**
 * @author YangWenjun
 * @date 2019/12/3 15:57
 * @project MockFramework
 * @title: CallBackException
 * @description:
 */
public class CallBackException extends Exception {


    public CallBackException() {
        super();
    }

    public CallBackException(String message) {
        super(message);
    }

    public CallBackException(String message, Throwable cause) {
        super(message, cause);
    }

    public CallBackException(Throwable cause) {
        super(cause);
    }

    protected CallBackException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
