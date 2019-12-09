package com.busi.seckill.controller;

import com.busi.seckill.error.BusiException;
import com.busi.seckill.error.EmBusiError;
import com.busi.seckill.response.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class BaseController {
    //用于指定前端提交方式
    public static final  String  CONTENT_TYPE_FORMED = "application/x-www-form-urlencoded";

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handlerException(HttpServletRequest request, Exception ex) {
        Map<String, Object> responseData = new HashMap<>();
        if (ex instanceof BusiException) {
            BusiException busiException = (BusiException) ex;
            responseData.put("errorCode", busiException.getErrorCode());
            responseData.put("errorMsg", busiException.getErrorMsg());
        } else {
            responseData.put("errorCode", EmBusiError.UNKNOWN.getErrorCode());
            responseData.put("errorMsg", EmBusiError.UNKNOWN.getErrorMsg());
        }
        return CommonReturnType.create(responseData, "fail");
    }
}
