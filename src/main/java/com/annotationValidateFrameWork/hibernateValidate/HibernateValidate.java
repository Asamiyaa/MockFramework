package com.annotationValidateFrameWork.hibernateValidate;

import com.annotationValidateFrameWork.User;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import static org.junit.Assert.assertEquals;
//import javax.xml.validation.Validator;  注意引包

/**
 * 0.https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/
 *          validator构建和validator.validate( car )会在整合了spring后，到上下文中
 *
 *          javax.validate 和 hibernate 实现关系：规范 - 默认实现 所以要使用必须引入hibernate .代码中引用的是接口  https://bbs.csdn.net/topics/392487810
 *
 * 1.范围  -->场景
 *      field constraints
        property constraints               允许在get逻辑中对orgin field处理 只要最终返回满足即可
        container element constraints      private Map<@NotNull String, List<@NotNull String>> partManufacturers = new HashMap<>();  @Valid 容器级
        class constraints

   2.Object graphs
        @Valid, these references will be followed up by the validation engine as well. The validation engine will ensure that no infinite loops occur during
        cascaded validation, for example if two objects hold references to each other.

        private List<@NotNull @Valid Person> passengers = new ArrayList<Person>();
        private Map<@Valid Part, List<@Valid Manufacturer>> partManufacturers = new HashMap<>();

    3.选择性校验
         * false 因为验证整理对象   validator.validate( car );
        // true   validator.validateProperty(car, "manufacturer");
        validator.validateValue(Car.class, "manufacturer", null);
 *
 * 4.结果获取
 *      Iterator<ConstraintViolation<Car>> i = constraintViolations.iterator();
        while(i.hasNext()){
        ConstraintViolation<Car> now = i.next(); //这里必须保留获取信息而不能 next
        System.out.println(now.getMessage() + "property:" + now.getPropertyPath() + " value：" + now.getInvalidValue());
        }

    5.注解  -- 每个注解都有属性：message, groups and payload.
         @AssertFalse
         @AssertTrue
         @DecimalMax(value=, inclusive=)
         @DecimalMin(value=, inclusive=)
         @Digits(integer=, fraction=)
         @Email
         @Future
         @FutureOrPresent
         @Max(value=)
         @Min(value=)
         @NotBlank
         @NotEmpty
         @NotNull
         @Negative
         @NegativeOrZero
         @Null
         @Past
         @PastOrPresent
         @Pattern(regex=, flags=)
         @Positive
         @PositiveOrZero
         @Size(min=, max=)


 ------------------hibnernate addation -----------
         @CreditCardNumber(ignoreNonDigitCharacters=)
         @Currency(value=)
         @DurationMax(days=, hours=, minutes=, seconds=, millis=, nanos=, inclusive=)
         @DurationMin(days=, hours=, minutes=, seconds=, millis=, nanos=, inclusive=)
         @EAN
         @ISBN
         @Length(min=, max=)
         @CodePointLength(min=, max=, normalizationStrategy=)
         @LuhnCheck(startIndex= , endIndex=, checkDigitIndex=, ignoreNonDigitCharacters=)
         @Mod10Check(multiplier=, weight=, startIndex=, endIndex=, checkDigitIndex=, ignoreNonDigitCharacters=)
         @Mod11Check(threshold=, startIndex=, endIndex=, checkDigitIndex=, ignoreNonDigitCharacters=, treatCheck10As=, treatCheck11As=)
         @Range(min=, max=)
         @SafeHtml(whitelistType= , additionalTags=, additionalTagsWithAttributes=, baseURI=)
         @ScriptAssert(lang=, script=, alias=, reportOn=)
         @UniqueElements
         @URL(protocol=, host=, port=, regexp=, flags=)

 *  6.method constraint  以上信息是对bean的约束，这里是对bean方法的约束及调用， -->preconditions / post
 *       详见：testPramReturn()
 *
 *
 *  7.Interpolating constraint error messages

    8.取值   {min} - 取注解设置的值  、 ${name} = 取对象属性值  通常用来message中对校验结果回复的准确性  见testEL()          *
 *
 *  9.group

   10.defined customer contrains
         1.定义注解  - 可能需要定义枚举,枚举可以直接比较 ，不用必须有属性  /     String value(); 这里含义的一定有，因为要拿到注解规则
         2.定义实现器
         3.使用
         //定义了list注解内部  - 允许统一注解的不同配置 - 这里不行是因为二者这里是矛盾的
             @MyCaseValidate(value = CaseMode.LOWER)
             @MyCaseValidate(value = CaseMode.UPPER)
             @NotNull(message = "aaa is null")
             private String aaa;

        //多注解
             @NotNull
             @Size(min = 2, max = 14)
             @CheckCase(CaseMode.UPPER)
             private String licensePlate;

      11.标识注解 - 注解本身无限制，只是用来触发某种逻辑 @Valid  ---> 类注解
             见：ValidPassengerCountValidator.class

       12.支持spi   META-INF/validator.xml  -- 修改默认配置 configure  -- 扩展点

       13. integrating other framework
                orm / jsf-seam / cdi /javaee /javafx


 TODO:  相对于AnnotationUtils.class 省略了许多底层反射，注解属性的获取(框架完成)，这里只是按照约定嵌入了自定义的校验器
 *
 *
 *
 *
 * 源码：MessageInterpolator ValidationMessages
 *  message-interpolator, traversable-resolver, constraint-validator-factory, parameter-name-provider and clock-provider
 *   BeanDescriptor  PropertyDescriptor MethodDescriptor  ElementDescriptor ContainerDescriptor   ConstraintDescriptor
 *   Fail fast mode
 *   annotationProcessor
 */


public class HibernateValidate {

    //测试方法  main - test - spring-test(上下文)

    private static Validator validator;

    //提前加载
    @BeforeClass
    //java.lang.Exception: Method getVolidator() should be void     public static Validator getVolidator(){
    public static void  getVolidator(){
        validator =  Validation.buildDefaultValidatorFactory().getValidator();
    }

    //验证属性
    @Test
    public void testField() {
        Car car = new Car( null, "DD-AB-123", 4 );

        //TODO:这些都是公共，完全可以切面 公共处理
        Set<ConstraintViolation<Car>> constraintViolations =
                validator.validate( car );

        assertEquals( 1, constraintViolations.size() );
        assertEquals( "manufacturer is null", constraintViolations.iterator().next().getMessage() ); //对比自己实现的 也是放到list 遍历
    }

    //验证方法
    @Test
    public void testProperty() {
        Car car = new Car( "111", "DD-AB-123", 4,null );

        Set<ConstraintViolation<Car>> constraintViolations =
                validator.validate( car );

        assertEquals( 1, constraintViolations.size() );
        assertEquals( "property is null", constraintViolations.iterator().next().getMessage() );
    }


    //验证容器约束  @ValidPart / @MinTowingCapacity无法引用 这些注解是注解和元素组合
    @Test
    public void testContainer() {
        Car car = new Car( "111", "DD-AB-123", 4,null );

        Set<ConstraintViolation<Car>> constraintViolations =
                validator.validate( car );

        assertEquals( 3, constraintViolations.size() );
        assertEquals( "{property is null", constraintViolations.iterator().next().getMessage() );
    }

    //验证对象导航

    /**
     *   // car 中添加对象导航校验
         @NotNull(message = "validate user fail")
         @Valid
     private User user;
     */
    @Test
    public void testObjGraphs() {
        Car car = new Car("111", "DD-AB-123", 4, "11");

        Set<ConstraintViolation<Car>> constraintViolations =
                validator.validate(car);

        assertEquals(2, constraintViolations.size());
//        assertEquals( "validate user fail", constraintViolations.iterator().next().getMessage() );
        // for (;constraintViolations.iterator().hasNext() == true ;  constraintViolations.iterator().next()){
        for (; constraintViolations.iterator().hasNext() == true; constraintViolations.iterator().next()) {
            Iterator<ConstraintViolation<Car>> i = constraintViolations.iterator();
            int i1 = 0;
            while (i.hasNext()) {
                ConstraintViolation<Car> now = i.next(); //这里必须保留获取信息而不能 next
                //为了跳过测试其他案例
                if (i1 == 0) {
                    i1++;
                    continue;
                }
                System.out.println(now.getMessage() + "property:" + now.getPropertyPath() + " value：" + now.getInvalidValue());
            }

        }
    }
    @Test
    public void testValideProperty() {
        Car car = new Car( "111", "DD-AB-123", 4,null );

        Set<ConstraintViolation<Car>> constraintViolations =
//             false 因为验证整理对象   validator.validate( car );
//             true   validator.validateProperty(car, "manufacturer");
                validator.validateValue(Car.class, "manufacturer", null);
        assertEquals( 1, constraintViolations.size() );
//        assertEquals( "validate user fail", constraintViolations.iterator().next().getMessage() );
    }

    /***
     * 参数－交叉参数－
     * Note that declaring method or constructor constraints itself does not automatically cause their validation upon invocation of the executable. Instead,
     * the ExecutableValidator API (see Section 3.2, “Validating method constraints”) must be used to perform the validation, which is often done using a method
     * interception facility such as AOP, proxy objects etc.
     */

    @Test
    public void testPramReturn() throws NoSuchMethodException {
        Car car = new Car("111", "DD-AB-123", 4, "1");
        //TODO:当你调用方法时提示注解 参数 说明别人在定义方法的时候已经对入参进行了控制  vs 进去在判断
//      true  car.rentCar(new User(),new Date(),10);
        car.rentCar(null, null, 10); //仍然执行成功 ，没有调用到返回值设计到参数返回值

        //直接调用没有触发注解校验,so引入对应调用------------------------------
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        ExecutableValidator executableValidator = factory.getValidator().forExecutables();
        Method method = Car.class.getMethod("rentCar", User.class, Date.class, int.class);
        Object[] parameterValues = {new User(), new Date(), 10};
        Set<ConstraintViolation<Car>> violations = executableValidator.validateParameters(
                car,
                method,
                parameterValues
        );

        Iterator<ConstraintViolation<Car>> i = violations.iterator();
        while (i.hasNext()) {
            ConstraintViolation<Car> now = i.next();
            System.out.println(now.getMessage() + "property:" + now.getPropertyPath() + " value：" + now.getInvalidValue());
        }

        //对返回值确认
        Method method1 = Car.class.getMethod("getPassengers");
        // Object[] parameterValues = { 80 };
        //TODO:这里为什么外部定义返回值呢？
        Object returnValue = Collections.<User>emptyList();
        Set<ConstraintViolation<Car>> violations1 = executableValidator.validateReturnValue(car, method1, returnValue);
        assertEquals(1, violations1.size());

//        构造器
//        ExecutableValidator#validateConstructorParameters()

//        MethodNode methodNode = propertyPath.next().as( MethodNode.class );

        //-------------问题：执行后没有触发正常逻辑调用 aop..?----------

//交叉参数
//        @ELAssert(expression = "...", validationAppliesTo = ConstraintTarget.PARAMETERS)
//        public Car buildCar(List<Part> parts) {

//级联
//        @Valid, the constraints declared on the parameter or return value object are validated as well.
//        public boolean checkCar(@Valid @NotNull Car car) {

//接口
//        public interface Vehicle {
//
//            void drive(@Max(75) int speedInMph);

// javascript 引擎
//        @ParameterScriptAssert(lang = "javascript", script = "luggage.size() <= passengers.size() * 2")
//        public void load(List<Person> passengers, List<PieceOfLuggage> luggage) {
//            //...
//        }
//        }

    }
        @Test
    public void testEL() {
        Set<ConstraintViolation<Car>> constraintViolations =
                validator.validateValue(Car.class, /*"licensePlate1"*//*"seatCount1"*/"topSpeed1", /*"1"*//*1*/900d);
        assertEquals( 1, constraintViolations.size() );
        ConstraintViolation cv =  constraintViolations.iterator().next();
        System.out.println(cv.getMessage());     //The license plate '1' must be between 2 and 14 characters long   -- 取到对应的值
        System.out.println(cv.getPropertyPath());//licensePlate1
        System.out.println(cv.getInvalidValue());//1


    }


    @Test
    public void testGroup() {
        Set<ConstraintViolation<Car>> constraintViolations =
                //由于在该方法上添加了groups ,所以没有匹配到 。 不是模糊而是完全 。 先判断组是否符合，再进来判断值
//                validator.validateValue(Car.class, /*"licensePlate1"*//*"seatCount1"*/"topSpeed1", /*"1"*//*1*/900d);
                validator.validateValue(Car.class, /*"licensePlate1"*//*"seatCount1"*/"topSpeed1", /*"1"*//*1*/900d,MyIntergface.class);
        assertEquals( 1, constraintViolations.size() );
        ConstraintViolation cv =  constraintViolations.iterator().next();
        System.out.println(cv.getMessage());     //The license plate '1' must be between 2 and 14 characters long   -- 取到对应的值
        System.out.println(cv.getPropertyPath());//licensePlate1
        System.out.println(cv.getInvalidValue());//1


    }



    @Test
    public void testCustomer() {
        Car car= new Car();car.setAaa("aaa");

        Set<ConstraintViolation<Car>> constraintViolations =
                 validator.validateProperty(car,"aaa");
        assertEquals( 0, constraintViolations.size() );

    }

    @Test
    public void testClassAnnotation() {
    }
}






