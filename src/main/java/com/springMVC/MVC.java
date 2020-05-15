package com.springMVC;

import com.annotationValidateFrameWork.Result;
import com.annotationValidateFrameWork.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 *
 * 1.springmvc和模板引擎  -- mvc model 流程以及前台展示方式 值传递方式
 * 1.@RequestBody vs @ @ModelAttribute  https://www.cnblogs.com/thiaoqueen/p/7400353.html  前者用于jsonString 后者用户jsonObj
 * 2.@ModelAttribute注解的使用总结  https://blog.csdn.net/leo3070/article/details/81046383
 * 3.@RequestParam和@PathVariable的用法与区别  https://blog.csdn.net/a15028596338/article/details/84976223
 *
 *req对象和reponse对象:https://blog.csdn.net/huangyuhuangyu/article/details/52910772
 *
 *深入了解SpringMVC执行流程:https://www.zhihu.com/tardis/sogou/art/42602265
 *
 *
 */


public class MVC {

    //例子：VolidateController
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
