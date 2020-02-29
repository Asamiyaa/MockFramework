package com.annotationValidateFrameWork.hibernateValidate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

//public class CheckCaseValidator implements ConstraintValidator{
public class CheckCaseValidator implements ConstraintValidator<MyCaseValidate, String>{  // <注解,属性类型> isValidate比较两值
    private CaseMode caseMode;

    @Override
    public void initialize(MyCaseValidate constraintAnnotation) {
        this.caseMode = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String object, ConstraintValidatorContext constraintContext) {
        /**
         * If null is not a valid value for an element, it should be annotated with @NotNull explicitly.
         */
        if ( object == null ) {
            return true;
        }
        boolean isValid;
        if ( caseMode == CaseMode.UPPER ) {
//            return object.equals( object.toUpperCase() );
            isValid = object.equals( object.toUpperCase() );
        }
        else {
            isValid = object.equals( object.toLowerCase() );
        }
//ConstraintValidatorContext object, it is possible to either add additional error messages or completely
// disable the default error message generation and solely define custom error messages.
        if ( !isValid ) {
            constraintContext.disableDefaultConstraintViolation();
            constraintContext.buildConstraintViolationWithTemplate(
//                    Note that the custom message template is passed directly to the Expression Language engine.
                    "{org.hibernate.validator.referenceguide.chapter06." +
                            "constraintvalidatorcontext.CheckCase.message}"
            )
                    .addConstraintViolation();
        }

        return isValid;
    }
}
