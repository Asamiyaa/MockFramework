package com.exception;

/**
 * @author YangWenjun
 * @date 2019/11/22 10:00
 * @project MockFramework
 * @title: DraftBindException
 * @description:
 */
public class DraftBindException extends Exception {

    private static final long serialVersionUID = -1L;

    public DraftBindException() {
    }

    public DraftBindException(String message) {
        super(message);
    }

    public DraftBindException(String message, Throwable cause) {
        super(message, cause);
    }

    public DraftBindException(Throwable cause) {
        super(cause);
    }

    public DraftBindException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
