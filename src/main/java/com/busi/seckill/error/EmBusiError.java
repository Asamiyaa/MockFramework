package com.busi.seckill.error;

//枚举实质就是将类和对象放在一起，所以属性，构造方法，方法 .....,所以getxx方法直接返回this.
//枚举中是否有属性的get,set属性?还是来自接口?
public enum EmBusiError implements CommonError {
        //10000开头为用户信息相关错误定义
        //对于分布式开发，统一全局错误的流转

        //结合setErrorMsg使用 ，便于在不同场景下，参数校验可能是邮箱没传，名字没传...
        UNKNOWN(99999,"未知错误"),
        PARAMETER_VALIDATE_ERROR(10000,"参数不合法"),
        USER_NO_EXIT(10001,"用户不存在")
    ;
    private int errorCode ;
    private String  errorMsg ;

    private EmBusiError(int errorCode ,String errorMsg){
        this.errorCode = errorCode ;
        this.errorMsg = errorMsg ;
    }
    @Override
    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMsg() {
        return errorMsg;
    }

    //用于通用错误类型,定制化改变自己...
    //this关键字
    @Override
    public CommonError setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg ;
        return this  ;
    }
}
