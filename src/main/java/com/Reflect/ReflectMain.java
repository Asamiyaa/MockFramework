package com.Reflect;

/**
 *
 * ******TODO  用来整合、迭代 反射与内部类、异常、枚举、泛型、注解关联 于Class类api结合************
 * ******TODO 除了Class -Filed -Method ...以及粘合了 枚举、注解....等类的api*******************
 *          https://www.processon.com/diagraming/5dc3eac4e4b0335f1e52d611
 *             
                0.String是什么：
 *                      1.String就是话。用来描述事物。但他具有实操性，只有对象有。所以强转。交流很多，所以String很多
 *                      2.string的组成是byte。基本单位。网络间传输
 *              
                1.内部类(有的公司不允许使用内部类，编译会造成checklist问题)
 *                      1.为了代码的紧凑和权限控制 public protect final static .....
 *                      2.类级别 控制
 *
 *              2.异常
 *                      1.自定义异常将Exception中的message..方法重写，调用super(),添加自己的输出。
 *                        调用父类而不是完全重写是为了扩展，并且符合调用链及代码低层、高层父类做的事情也因为super调用而调用了一遍
 *
 *                        log 错误日志
 *
 *              3.枚举  EnumMain.java  EnumHelper.java     <=== 分组 - 过滤 / 入参限定  / 语义明确(给定了范围)
 *                      1.自带属性 order ..  自定义属性
 *                      2.枚举可以放在类定义的开始，相当于内部类的位置。减少暴露。使得入参是固定枚举类型范围而不是client端自己随意
 *
 *              4.泛型  GenericClass.java -- 集合map\list baseDao 处理逻辑和具体bean无关为‘不同类’-经常于Class - 反射结合
 *                     GenericUtils.java
 *                      1.对于可以使用Object地方(Object[]为了代码中写for...说明传参可能是数组，也可以在代码中进行类型判断。这样默认传入[]则代表单个也可以传入)
 *                        当返回值和入参有‘类型关系’时则使用或者说T,R.入t返r.
 *                      2.入参有限制需要上下限控制  <T extends Map>..super
 *                      3.擦除 提前校验
 *
 *              5.注解  AnnotationUtils   <==  配合 annBoy
 *                      1.标识 类似于标识接口，这里更灵活。
 *                      2.注解复杂的地方是该注解的处理类，得到标识后如何处理，以及标识定义的属性如何处理.这些底层和spring\context..结合
 *                      3.注解是对xml的简化 注解>xml>代码。springboot注解式开发往往伴随着通过代码配置bean
 *
 *
 *              1.异常+枚举    CommonError 限定了项目中的异常定义，为统一异常、统一返回提供基础  - baseController 二者实现统一接口 统一异常处理 EnumHelper对传递值和Enum进行对比
 *              2.枚举+泛型
 *              3.泛型+反射    定义泛型类型、值  genericUtil
 *              4.注解+反射    获得属性值、注解、注解值并进行逻辑处理  AnnotationUtils、CheckCaseValidator、
 *
 *              6.io/nio
 *              7.serializable
 *              8.clone
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
public class ReflectMain {
}
