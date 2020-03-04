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
import org.springframework.web.bind.MethodArgumentNotValidException;
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
    public void doBefore(JoinPoint point) throws IOException, MethodArgumentNotValidException {
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
                //遍历参数类型 -- 而不是这里的指定1
                if (paramObj[1] instanceof BindingResult) {
                    BindingResult result = (BindingResult) paramObj[1];
                    Result errorMap = this.validRequestParams(result);
                    if (errorMap != null) {
                        //这些对response的封装都不需要 统一到baseController中
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
                            //TODO：        1.throw到base 统一处理　(MethodParameter parameter, BindingResult bindingResult 前一个参数不知道如何获取？
                                                //对于切面如何和整体流程柔和起来
                            //TODO          2.定义ControllerAdvice.java 这样就无须定义每个切点 ，以及逻辑 统一起来处理  -- 需要做的，比如解码，转码等信息
                                                //对于和spring扩展点 结合起来
                            //TODO:         3.在baseController中直接写添加@restContolleradvice -- 可以debug到 --vs 啥时候使用切面
                                                //多个扩展之间的顺序 上面的定义ControllerAdvice肯定还在base的外层
                            //TODO:     https://hibernate.org/validator/documentation/getting-started/ 结合 AnnotationUtils.java 结合proceeson
                            //TODO:     对枚举、注解、反射、接口进行再次梳理，如何选择、联通。---> 思考

                            throw  new MethodArgumentNotValidException(null,result);
                           // output.write(error.getBytes("UTF-8"));
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


