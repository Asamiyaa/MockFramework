package com.log.annotation;

/**
 * @author YangWenjun
 * @date 2019/10/16 9:33
 * @project hook
 * @title: Student
 * @description:
 */
public class Student {
    /**
     * 1.默认是value
     */
    @MaxNumber(name = "money" ,maxNumber=99.99)
    private double money;

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
