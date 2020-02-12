package com.annotationValidateFrameWork;


public class Result {
    private int code;
    private Object data;
    private String msg;

    public static Result error(Object data, String msg) {
        Result r = new Result();
        r.setCode(-1);
        r.setData(data);
        r.setMsg(msg);
        return r;
    }

    public static Result success() {
        Result r = new Result();
        r.setCode(1);
        return r;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}