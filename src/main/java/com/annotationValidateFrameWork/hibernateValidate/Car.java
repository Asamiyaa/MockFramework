package com.annotationValidateFrameWork.hibernateValidate;

import com.annotationValidateFrameWork.User;
import org.hibernate.validator.constraints.URL;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.*;

//@ValidPassengerCount
public class Car {



    @NotNull(message = "manufacturer is null")
    private String manufacturer;

    //使用list
   // @MyCaseValidate(value = CaseMode.LOWER)

    //定义了list注解内部  - 允许统一注解的不同配置
    @MyCaseValidate(value = CaseMode.LOWER)
    @MyCaseValidate(value = CaseMode.UPPER)
    @NotNull(message = "aaa is null")
    private String aaa;

//    @Size(min = 1, max = 5 ,message = "height ${height} not {min} and {max}")

    //    private Integer height;   size 不可以修改int
    @Size(
        min = 2,
        max = 14,
        message = "The license plate '${validatedValue}' must be between {min} and {max} characters long"
    )
    private String licensePlate1;
    @Min(
            value = 2,
            message = "There must be at least {value} seat${value > 1 ? 's' : ''}"  //用于计算
    )
    private int seatCount1;

    @DecimalMax(
            value = "350",
            message = "The top speed ${formatter.format('%1$.2f', validatedValue)} is higher " +
                    "than {value}",
//            groups = {Car.class}  组必须是接口
            groups = {MyIntergface.class}
    )
    private double topSpeed1;

    @NotNull
    @Size(min = 2, max = 14)
    private String licensePlate;

    @Min(2)
    private int seatCount;

    private String testProperty ;


    //对象导航校验

    @NotNull(message = "validate user fail")
    @Valid
    private User user;
    @URL
    private String url;

    public String getLicensePlate1() {
        return licensePlate1;
    }

    public void setLicensePlate1(String licensePlate1) {
        this.licensePlate1 = licensePlate1;
    }

    public String getAaa() {
        return aaa;
    }

    public void setAaa(String aaa) {
        this.aaa = aaa;
    }

    public int getSeatCount1() {
        return seatCount1;
    }

    public void setSeatCount1(int seatCount1) {
        this.seatCount1 = seatCount1;
    }

    public double getTopSpeed1() {
        return topSpeed1;
    }

    public void setTopSpeed1(double topSpeed1) {
        this.topSpeed1 = topSpeed1;
    }

    public void rentCar(
            @NotNull User customer,
            @NotNull/* @Future*/ Date startDate,
            @Min(1) int durationInDays) {
        //...
        System.out.println("rentCar is called");
    }

    @Size(min = 1)
    public List<User> getPassengers() {
        //...
        return Collections.emptyList();
    }

    public Car(){}
    public Car(@NotNull(message = "manufacturer is null") String manufacturer, @NotNull @Size(min = 2, max = 14) String licensePlate, @Min(2) int seatCount, String testProperty, @NotNull @Valid User user) {
        this.manufacturer = manufacturer;
        this.licensePlate = licensePlate;
        this.seatCount = seatCount;
        this.testProperty = testProperty;
        this.user = user;
    }


    //    private Set<@MinTowingCapacity String> parks ;
//    private Set<@Valid Part String> part ;
//private List<@ValidPart String> part = new ArrayList<>();

    private Map<@NotNull String, List<@NotNull String>> partManufacturers =
            new HashMap<>();
    private List<String> passengers;

    @NotNull(message = "{property is null")
    public String getTestProperty() {
        return testProperty;
    }

    public void setTestProperty(String testProperty) {
        this.testProperty = testProperty;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }

    public Car(@NotNull(message = "manufacturer is null") String manufacturer, @NotNull @Size(min = 2, max = 14) String licensePlate, @Min(2) int seatCount, String testProperty) {
        this.manufacturer = manufacturer;
        this.licensePlate = licensePlate;
        this.seatCount = seatCount;
        this.testProperty = testProperty;
    }

    public Car(@NotNull(message = "manufacturer is null") String manufacturer, @NotNull @Size(min = 2, max = 14) String licensePlate, @Min(2) int seatCount) {
        this.manufacturer = manufacturer;
        this.licensePlate = licensePlate;
        this.seatCount = seatCount;
    }

    //getters and setters ...
}
