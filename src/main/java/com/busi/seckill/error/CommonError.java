package com.busi.seckill.error;

public interface CommonError {

    int getErrorCode();

    String getErrorMsg();

    //问题1：CommonError中没有属性，这里set的含义是什么？
    CommonError setErrorMsg(String errorMsg);
}

