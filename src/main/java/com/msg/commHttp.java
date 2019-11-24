package com.msg;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * http实现  和httpClient关系是啥？
 *
 * TODO :    httpClient作为封装了http操作，应该自己实现普通的servlet.明确上下文数据获取 --> 其他框架上下文间获取操作
 *          完成所有的communication后 ， 结合前面的xml解析 完整化  +  mq引入 +
 *          参照这个完善：https://blog.csdn.net/shuxing520/article/details/79917348
 *          client参数：https://blog.csdn.net/ITNoobie/article/details/48262785
 */
public class commHttp {

    public static String doPost(String url) throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost(url);

        List<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("name","yangwenjun"));
        parameters.add(new BasicNameValuePair("age","27"));
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters);
        httpPost.setEntity(formEntity);

        //伪装浏览器
        httpPost.setHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/53 7.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");

        CloseableHttpResponse httpResponse = null ;
        httpResponse = httpClient.execute(httpPost);
        if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String content = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
            FileUtils.writeStringToFile(new File("C:\\aabbcc\\a.txt"),content,"UTF-8");
        }

        httpClient.close();
        httpResponse.close();

        System.out.println("--ok--");
        return null;
    }


    public static void main(String[] args) throws IOException {
        commHttp.doPost("https://blog.csdn.net/");//出现有点网站访问不到
    }
}


//}
