package com.utils;

public class Transcoding {
	
	/**
	 * 转码
	 * @param obj
	 * @return
	 */
   
    private static String UTFToiso8859(Object obj) {
        try {
            if (obj == null)
                return "";
            else
                return new String(obj.toString().getBytes("UTF-8"), "iso-8859-1");  //不同平台需要转码 toString 相当于构造器创建对象，不能简单的看到sysout，平时重写只是为了验证。
            //底层将 对象 -->以String展示 “”“”“” ...所以说string是特殊的类型  都可以作为转换中介。
        }
        catch (Exception e) {
            return "";
        }
    }
}
