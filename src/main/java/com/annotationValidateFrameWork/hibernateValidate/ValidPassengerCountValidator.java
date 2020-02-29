package com.annotationValidateFrameWork.hibernateValidate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidPassengerCountValidator
        implements ConstraintValidator<ValidPassengerCount, Car> {

    //标识注解并不从注解本身获取信息，只是触发之意
    @Override
    public void initialize(ValidPassengerCount constraintAnnotation) {
    }

    //从被注解对象中获取该触发之意的逻辑
    @Override
    public boolean isValid(Car car, ConstraintValidatorContext context) {
        if ( car == null ) {
            return true;
        }

        return car.getPassengers().size() <= car.getSeatCount();
    }
}