package com.annotationValidateFrameWork;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 1.切面的组成和配置
 * 2.spring调用过程中，哪一步触发了自动的校验
 * 2.jointPoint..可以获取到哪些信息，这些信息如何和当前的context或者说spring的context结合
 * sysout刚开始显示，现在不显示，怀疑是不是编译有问题 - build/服务器
 */
//@Slf4j
@Aspect
@Component
public class UserAspect {

   /* @Before("execution(public * com.annotationValidateFrameWork..*(..))")
    @Before("xxxx(xxxxx)xxxx")
*/
    @Pointcut("execution(public * com.annotationValidateFrameWork.VolidateController..*(..))")
    public void log(){}
        // @Before("execution(public * com.jilinwula.springboot.helloworld.controller..*(..))")
    @Before("log()")
    public void doBefore(JoinPoint point) throws IOException {
        System.out.println("--------------------aspectj do --------------------------------------");
      /*  //TODO:这里去完成和spring自动或者说和自己判断逻辑 脱离了spring自动的validate?  -- 这里
        //是因为这里没有封装输出吗
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof BindingResult) {
                BindingResult result = (BindingResult) arg;
                if (result.hasErrors()) {
                    FieldError error = result.getFieldError();
                     Result.error(error.getField(), error.getDefaultMessage());
                }
            }
        }//return joinPoint; //如何让代码继续往下走
    }*/

            Object[] paramObj = point.getArgs();
            if (paramObj.length > 0) {
                if (paramObj[1] instanceof BindingResult) {
                    BindingResult result = (BindingResult) paramObj[1];
                    Result errorMap = this.validRequestParams(result);
                    if (errorMap != null) {
                        ServletRequestAttributes res = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                        HttpServletResponse response = res.getResponse();
                        response.setCharacterEncoding("UTF-8");
                        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                        response.setStatus(HttpStatus.BAD_REQUEST.value());

                        OutputStream output = null;
                        try {
                            output = response.getOutputStream();
                            //errorMap.setCode(null);
                            String error = "this is aspect error0";//().toJson(errorMap);  、、mock v is this is aspect error0{"code":1,"data":null,"msg":null}
                            //log.info("aop 检测到参数不规范" + error);
                            output.write(error.getBytes("UTF-8"));
                        } catch (IOException e) {
                            //log.error(e.getMessage());
                        } finally {
                            try {
                                if (output != null) {
                                    output.close();
                                }
                            } catch (IOException e) {
                              //  log.error(e.getMessage());
                            }
                        }
                    }
                }
            }
        }

        /**
         * 校验
         */
        private Result validRequestParams(BindingResult result) {
            if (result.hasErrors()) {
                List<ObjectError> allErrors = result.getAllErrors();
                List<String> lists = new ArrayList<>();
                for (ObjectError objectError : allErrors) {
                    lists.add(objectError.getDefaultMessage());
                }
                return Result.error("error11111","error11111");
            }
            return null;
        }
    }


