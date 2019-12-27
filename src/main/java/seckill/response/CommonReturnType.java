package seckill.response;

public class CommonReturnType {
    //表明对应请求的返回结果为success或者fail
    private String status ;
    //若status为success,则data内返回前端需要的json数据
    //若status为fail，则data返回通用错误码
    private Object data ;

    //为了测试fail情况，创建fail需要的属性----
    //private String errorCode ;
    //private String errorMsg ;

   /* public CommonReturnType(String status, String errorCode, String errorMsg) {
        this.status = status;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }*/
    //----------------------------------------
    public CommonReturnType(){}
    public CommonReturnType(String status, Object data) {
        this.status = status;
        this.data = data;
    }

    /**
     * 思考1.自己类中，可以定义构建自己的方法 construct
     *                 ...      获取.........  返回自己       vs 构造方法
     *                     vo..mapping .. 等用于传递值得可以直接new ,其他情况应该封装，而不是客户端去new
     *                 ......   获取自己属性..
     *                 .....   对比和别的.......
     *         打破那种不能自己获得自己的思想
     * @return
     */

    //定义一个通用的创建方法，并构建方法重载，所以也是public
    public static CommonReturnType create(Object result){
        return CommonReturnType.create(result , "success");
    }

    public static CommonReturnType create(Object result, String status) {
        return new CommonReturnType(status ,result);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

  /*  public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }*/
}
