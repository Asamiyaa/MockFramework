package com.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *  @author  YangWenjun
 *  @date   2018年11月7日
				常用的摘要算法有MD5,SHA1。摘要算法是一个不可逆过程，就是无论多大数据，经过算法运算后都是生成固定长度的数据,一般结果使用16进制进行显示。 
				MD5和SHA1的区别：MD5结果是128位摘要，SHa1是160位摘要。那么MD5的速度更快，而SHA1的强度更高。
				
				下面统一使用MD5算法进行说明，SHA1类似。 
				主要用途有：验证消息完整性，安全访问认证，数据签名。
				
				消息完整性：由于每一份数据生成的MD5值不一样，因此发送数据时可以将数据和其MD5值一起发送，然后就可以用MD5验证数据是否丢失、修改。
				安全访问认证：这是使用了算法的不可逆性质，（就是无法从MD5值中恢复原数据）对账号登陆的密码进行MD5运算然后保存，这样可以保证除了用户之外，即使数据库管理人员都无法得知用户的密码。
				数字签名：这是结合非对称加密算法和CA证书的一种使用场景
				
				详细：http://www.iteye.com/topic/1122076****
			

 */
public class EncrypUtil {
	  public static String getMD5(String path){

	        String pathName = path;
	        String md5= "";
	        try {
	            File file = new File(pathName);
	            FileInputStream ins = new FileInputStream(file);
	            FileChannel ch = ins.getChannel();
	            MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0,file.length());       
	            MessageDigest md = MessageDigest.getInstance("MD5");
	            md.update(byteBuffer);
	            ins.close();
	            md5 = toHexString(md.digest());
	        } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return md5;
	    }
     
	  
	  //对文件进行SHA1摘要
	    public static String getSHA1(String path){
	        String pathName = path;
	        String sha1= "";
	        try {
	            File file = new File(pathName);
	            FileInputStream ins = new FileInputStream(file);
	            FileChannel ch = ins.getChannel();
	            MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0,file.length());       
	            MessageDigest sha = MessageDigest.getInstance("SHA-1");
	            sha.update(byteBuffer);
	            ins.close();
	            sha1 = toHexString(sha.digest());
	        } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return sha1;
	    }    
	        
	        final static char hex[] = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
	        public static String toHexString(byte[] tmp){
	            String s;
	            char str[] = new char[tmp.length*2];
	            int k =0;
	            for (int i = 0; i < tmp.length; i++) {
	                byte byte0 = tmp[i];
	                str[k++] = hex[byte0>>>4&0xf];
	                str[k++] = hex[byte0&0xf];
	            }
	            s=new String(str);
	            return s;
	        }
	
}
