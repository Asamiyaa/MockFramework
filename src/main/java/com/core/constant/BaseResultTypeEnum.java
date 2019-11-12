package com.core.constant;

import com.fasterxml.jackson.databind.ser.Serializers;

/**
 * @author YangWenjun
 * @date 2019/11/8 10:19
 * @project MockFramework
 * @title: BaseResultTypeEnum
 * @description:
         * System.out.println(xxConstant.STUDENT_NUM);
        System.out.println(BaseResultTypeEnum.PARAM_ERROR.getCode());   1
        System.out.println(BaseResultTypeEnum.PARAM_ERROR.getMsg());   参数有误
        System.out.println(BaseResultTypeEnum.PARAM_ERROR.name());      PARAM_ERROR
        System.out.println(BaseResultTypeEnum.PARAM_ERROR.ordinal());   0
 */
public enum BaseResultTypeEnum implements BaseEnum {

    NO_KNOWN(0,"未知异常"),
    PARAM_ERROR(1,"参数有误") ;

    private int code ;
    private String msg ;

    BaseResultTypeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    //定义其他比较的方法 equals ...+ 根据需要填写  --->属性间的传递
    public String getMsgByCode(){
        for (BaseResultTypeEnum baseResultTypeEnum:BaseResultTypeEnum.values()) {
            if(baseResultTypeEnum.getCode() == this.code){
                return baseResultTypeEnum.getMsg();
            }
        }
        return "未找到对应内容" ;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
