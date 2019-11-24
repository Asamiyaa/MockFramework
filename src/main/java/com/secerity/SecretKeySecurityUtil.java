package com.secerity;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 秘钥处理类
 */
public class SecretKeySecurityUtil {

    public static void main(String[] args) throws Exception {
        md5("yangwenjun","salt");
    }

    public static String md5(String text, String key) throws Exception {
        //加密后的字符串
        String encodeStr= DigestUtils.md5Hex(text + key);
        System.out.println("MD5加密后的字符串为:encodeStr="+encodeStr);//encodeStr=753e158507a58380658909c54804b93a
        return encodeStr;
    }

}
