package com.annotationValidateFrameWork.hibernateValidate;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/*
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.ANNOTATION_TYPE,ElementType.METHOD})
public @interface MyValidate {

    String message() default "myValidator call";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
*/
//TODO:上下对比一下
@Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy ={ CheckCaseValidator.class})  //这里一直报错，放置类型不匹配  ----> 类实现过程中泛型未指定造成
@Documented
@Repeatable(MyCaseValidate.List.class) // 这里引入的可不是java.util.List
public @interface MyCaseValidate {

    String message() default "{org.hibernate.validator.referenceguide.chapter06.CheckCase." +
            "message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { }; // 根语言环境有关的判断

    CaseMode value();  //值需要有选择时。指定 ，比如注解

    @Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
    @Retention(RUNTIME)
    @Documented
    @interface List {
        MyCaseValidate[] value();
    }
}
