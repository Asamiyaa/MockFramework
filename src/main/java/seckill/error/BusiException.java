package seckill.error;
//自定义异常
public class BusiException extends  Exception  implements  CommonError {

    //强关联异常的种类，枚举
    private CommonError commonError ;

    //直接接受EmBusiError的传参用于构造业务异常
    public BusiException(CommonError commonError){
        //super()是否有必要
        //1.在重写spring中的postProcessor时，我们是在父类的基础上添加，而不是完全的让spring钩子处调用我们的方法，所以要写父类完成的事情即super()
        //2.需要明确调用父类的带参构造器 比如super(xx,xx,xx)
        /**
         * //有参的构造方法
         public CustomException(String message){
         super(message);

         }
         // 用指定的详细信息和原因构造一个新的异常
         public CustomException(String message, Throwable cause){

         super(message,cause);
         }
         //用指定原因构造一个新的异常
         public CustomException(Throwable cause) {
         super(cause);
         }
         */
        this.commonError = commonError ;
    }

    //接收自定义的errMsg的方式构造业务异常
    public BusiException(CommonError commonError , String errorMsg){
        this.commonError = commonError ;
        commonError.setErrorMsg(errorMsg);
    }

    /***
     * 使用包装器模式的含义：
     *        实现了同一接口，一个子类关联另一个子类(被包含的子类填充该接口，包含的子类定义流程)
     *        为什么这么做：
     *
     * @return
     */
    @Override
    public int getErrorCode() {
        return this.commonError.getErrorCode();
    }

    @Override
    public String getErrorMsg() {
        return this.commonError.getErrorMsg();
    }

    @Override
    public CommonError setErrorMsg(String errorMsg) {
        this.commonError.setErrorMsg(errorMsg);
        return this;
    }
}
