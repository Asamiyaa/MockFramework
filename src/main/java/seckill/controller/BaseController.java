package seckill.controller;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import seckill.error.BusiException;
import seckill.error.EmBusiError;
import seckill.response.CommonReturnType;
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
       //将切面的判断放到这里统一
        }else if(ex instanceof MethodArgumentNotValidException){
            MethodArgumentNotValidException methodArgumentNotValidException =(MethodArgumentNotValidException)ex;
            for (FieldError fieldError :methodArgumentNotValidException.getBindingResult().getFieldErrors()) {
                responseData.put("errorCode",fieldError);
                responseData.put("errorMsg", fieldError.getDefaultMessage());
            }
        }
        else {
            responseData.put("errorCode", EmBusiError.UNKNOWN.getErrorCode());
            responseData.put("errorMsg", EmBusiError.UNKNOWN.getErrorMsg());
        }
        return CommonReturnType.create(responseData, "fail");
    }
}
