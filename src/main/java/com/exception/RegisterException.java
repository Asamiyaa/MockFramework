package com.exception;

/**
 * @author YangWenjun
 * @date 2019/12/3 16:27
 * @project MockFramework
 * @title: RegisterException
 * @description:
 */
public class RegisterException extends Exception{

    public RegisterException() {
        super();
    }

    public RegisterException(String message) {
        super(message);
    }

    public RegisterException(String message, Throwable cause) {
        super(message, cause);
    }

    public RegisterException(Throwable cause) {
        super(cause);
    }

    protected RegisterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
