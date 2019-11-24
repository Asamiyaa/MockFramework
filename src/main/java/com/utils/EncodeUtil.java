package com.utils;

import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncodeUtil {

     /**使用MD5加密*/
     public static String EncodeByMD5(String orginVal) throws NoSuchAlgorithmException {
         //确定计算方式
         MessageDigest md5 = MessageDigest.getInstance("MD5");   //MessageDigest 类为应用程序提供信息摘要算法的功能
         BASE64Encoder base64Encoder = new BASE64Encoder() ;

         //加密
         String newVal = base64Encoder.encode(md5.digest(orginVal.getBytes()));
         return newVal;
     }

    /**使用PIN加密*/
    public static String EncodeByPIN(String orginVal){
        return null ;
    }

    /**使用MAC加密*/
    public static String EncodeByMAC(String orginVal){
        return null ;
    }

    /**使用SHA加密*/
    public static String EncodeBySHA(String orginVal){
        return null ;
    }

}
