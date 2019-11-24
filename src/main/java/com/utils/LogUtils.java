package com.utils;

import org.apache.commons.io.IOUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

/**
 * @author YangWenjun
 * @date 2019/8/21 16:06
 * @project hook
 * @title: LogUtils
 * @description:
 */
public class LogUtils {

    public static void  doWrite(String fileDirPath , String fileName , String content) throws Exception {

        File fileDir =  new File(fileDirPath);
        boolean fileOperFlag = true ;
        if(!(fileDir.isDirectory())){
            fileOperFlag = fileDir.mkdirs();        //对比文件处理一层层判断
        }
        if(!fileOperFlag){
            throw new Exception("xx创建失败");
        }
        BufferedWriter bw  = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "utf-8"));
            bw.write(content);
        }catch (Exception e){
            /***累加  机具项目中对失败的累加处理 ，判断当前异常" 有多大的影响 " ****/
        }finally {
            /****TODO :使用apache / google 工具  StringUtils.subStringBeforeLast(".");***/
            IOUtils.closeQuietly(bw);

        }
    }
}
