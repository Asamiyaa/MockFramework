package com.utils;

import java.io.File;
import java.util.List;

/**
 * @author YangWenjun
 * @date 2019/7/7 17:25
 * @project hook
 * @title: FileCompressUtil
 * @description:
 */
public class FileCompressUtil {

    public static boolean unCompressZips(List<File> zipFiles){
        for (File file :
                zipFiles) {
         if(!unCompressZip(file)){
             return Boolean.FALSE ;
         }
        }
        return Boolean.TRUE;
    }

    public static boolean unCompressZip(File zipFile){
        //zipInputStream
        return Boolean.TRUE;
    }


    public static boolean compressZip(List<File> zipFiles){
        //sequenceInputStream
        return Boolean.TRUE;
    }

    public static boolean compressZip(File zipFile){
        //zipInputStream  . jarInputStream
        return Boolean.TRUE;
    }

    //不同的压缩形式处理
    public static boolean unCompressRar(File rarFile){

        return Boolean.TRUE;
    }
}
