package com.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

/**
 * 
 *  @author  YangWenjun
 *  @date   2018年11月7日
                编码绝对不是加密。加密是可以算是一种编码的操作
				编码和加密的区别说通俗一点，在于编码是通常希望别人解码的。 而加密是不希望的。
				编码更多的是为了转换格式，加密是为了安全
				
				这些算法的加密对象都是基于二进制数据，如果要加密字符串就使用统一编码（如：utf8）进行编码后加密。
 */
public class EncodeUtil2 {

	    //Base64编码 
	public static String base64Encode(InputStream is) throws IOException{
		return Base64.getEncoder().encodeToString(getBytes(is));
	}
	
	private static byte[]  getBytes(InputStream is)  throws IOException{
		ByteArrayOutputStream bos = new ByteArrayOutputStream(1024) ;
		byte[] b = new byte[1024];
		int n =0 ;
		while((n = is.read()) != -1){
			bos.write(b,0,n);   //write到哪里？
		}
		bos.close();              //流都关闭了 。为什么可以拿到 bos.toByteArray() ?
		return bos.toByteArray() ;  //为什么不要要引入ByteArrayOutputStream在转到byte[] 而不是直接塞入到byte[]?
	}
	
	//注意：1.string / byte[] /  流  / 编码 相互之间转换   byte作为基本单位，所以发送，转换时使用到bytes
            	// 加密和编码中使用到大量的getBytes() --  System.out.println(Arrays.toString("123".getBytes())); // [49, 50, 51]  编码层的最基本，所以用来转换。
	
	//         2.io 种类  reader / inputStream  流作为“java内存模型”和外界交互的工具。byte字节作为基准
	
	//         3.buffer作用  https://segmentfault.com/a/1190000003804439
	
	
	
	
	
	
	
	
	
}
