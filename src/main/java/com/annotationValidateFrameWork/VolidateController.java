package com.annotationValidateFrameWork;


import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import seckill.controller.BaseController;

import javax.validation.Valid;

@RestController
@ResponseBody
@RequestMapping({"/"})
//注意这三个注解
public class VolidateController extends BaseController{ //为了验证切面抛出异常能否到达baseController

    //传统方式
    @RequestMapping({"/volidate"})
    public String volidate(String id ,String name){
    //public String volidate(){
      System.out.println("id" + id);
        if(StringUtils.isEmpty(id) || StringUtils.isEmpty(name)){
            return "11111";
        }
        return "1122";
    }

    //使用mock测试
    @RequestMapping({"/volidateMock"})
    public String volidate2(String id ,String name){
        //public String volidate(){
        System.out.println("id" + id);
        if(StringUtils.isEmpty(id) || StringUtils.isEmpty(name)){
            return "mock1";
        }
        return "mock2";
    }

    //将信息封装到对象
    @RequestMapping({"/volidateUser"})
    public String volidate2(User user){
        System.out.println("id" + user.getId());
        if(StringUtils.isEmpty(user.getId()) || StringUtils.isEmpty(user.getNaem())){
            return "mockUser1";
        }
        return "mockUser2";
    }

    //使用path校验 1.添加user对应注解  2.修改下面方法校验方式  TODO:这里的javax.validate的实现在哪里？默认调用了
    @RequestMapping({"/volidatePath"})
    public String volidate3(@RequestBody @Valid User user , BindingResult result){
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
               // return error.getDefaultMessage(); 添加报错属性
                FieldError fieldError = result.getFieldError();
                return fieldError.getField() + "----" + fieldError.getDefaultMessage();
            }
        }
        return "mockUserPath2";
    }

    //使用同一返回json的形式 即返回对象 再由responseBody进行转化
    @RequestMapping({"/volidateJson"})
    public Result volidate4(@Valid User user , BindingResult result){
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                // return error.getDefaultMessage(); 添加报错属性
                FieldError fieldError = result.getFieldError();
                return Result.error(fieldError.getField(), fieldError.getDefaultMessage());
            }
        }
        return Result.success();
    }

    //创建切面  是否需要valida 不能，否则不走切面  -- 需要  --> @RequestBody
    @RequestMapping({"/volidateAspect"})
    public Result volidate5(@RequestBody @Valid User user/* , BindingResult result*/){//使用baseController中的@restControllerAdvice进行处理
        /*if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                // return error.getDefaultMessage(); 添加报错属性
                FieldError fieldError = result.getFieldError();
                return Result.error(fieldError.getField(), fieldError.getDefaultMessage());
            }
        }*/
        return Result.success();
    }

}

