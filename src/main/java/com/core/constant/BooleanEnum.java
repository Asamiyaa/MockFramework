package com.core.constant;

/**
 * @author YangWenjun
 * @date 2019/11/21 18:27
 * @project MockFramework
 * @title: BooleanEnum
 * @description:
 */
public enum BooleanEnum implements BaseEnum {
    TRUE(1,"正确") , FALSE(0,"错误");

    private int code ;
    private String msg ;

    BooleanEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return 0;
    }

    @Override
    public String getMsg() {
        return null;
    }

    /**
     *
     *对于不定义属性的枚举，可以直接借助自身的属性.name / ordinal 参考：java中Enum.class实现
     //已完结
     FEED_BACKED;

     public static EnumMap<OrderStatusEnum, String> getMap() {
     EnumMap<OrderStatusEnum, String> map = new EnumMap<>(OrderStatusEnum.class);
     Arrays.stream(OrderStatusEnum.values()).forEach(x -> map.put(x, x.name()));
     return map;
     }
     */
}
