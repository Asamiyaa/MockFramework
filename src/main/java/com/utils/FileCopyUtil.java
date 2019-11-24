package com.utils;

import java.io.*;

/**
 *
 @Author   YangWenjun
 @Date     2019-07-03 22:03
 @description: 文件 上传 | 下载 处理类
 @TODO   策略模式  ftp实现 sftp实现  default流实现 ...
 @Version 1.0
 */
public class FileCopyUtil {

    //log
    private static final byte[] DEFAULT_BYTEARRAY = new byte[128];
    private static  boolean FLAG = Boolean.TRUE;

    public static boolean copyFile(String srcPath , String destPath) throws IllegalAccessException, IOException {

        return  copyFile( new File(srcPath) , new File(destPath) );
    }

    //重载
    public static boolean copyFile(File srcFile , File destFile) throws IllegalAccessException, IOException {
        if(!srcFile.exists() || !srcFile.canRead()){
            throw new IllegalAccessException("文件不可访问");
        }

        if(!destFile.exists()){
            //destFile.mkdirs();
            destFile.getParentFile().mkdirs();
            destFile.createNewFile();
        }
        //InputStream is = null ;
        for (File srcFileUnit:srcFile.listFiles()) {   //api对设计严谨性影响  -- 假如这里的文件夹下还有文件夹呢？多维 - 递归
            /*if(srcFileUnit.isFile()){  抽离的早
                copySimpleFile( srcFileUnit , destFile );
            }*/
            copyFolderOrFile( srcFileUnit , destFile );
        }
        return Boolean.TRUE;
    }

    private static void copyFolderOrFile(File srcFile, File destFile) {

        if(srcFile.isDirectory()){
            File newFolder=new File(destFile,srcFile.getName());
            newFolder.mkdirs();
            File[] fileArray=srcFile.listFiles();

            for(File file:fileArray){
                copyFolderOrFile(file, newFolder); //递归
            }

        }else{
            File newFile=new File(destFile,srcFile.getName());
            copySimpleFile(srcFile,newFile);
        }

    }

    private static void copySimpleFile( File srcFileUnit , File destFile ){
        try (
                InputStream bis = new BufferedInputStream(new FileInputStream(srcFileUnit));  //TODO:是否修改为reader PrintWriter?
                OutputStream bout = new BufferedOutputStream(new FileOutputStream(destFile)
                )
        ) {

            while (bis.read(DEFAULT_BYTEARRAY) != -1) { //返回的是读到数组个数,读取这个多个字节，并放到该数组中
                // bout.write(bis.read(DEFAULT_BYTEARRAY));
                bout.write(DEFAULT_BYTEARRAY);
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
            FLAG = Boolean.FALSE;
        } catch (IOException e) {
            e.printStackTrace();
            FLAG = Boolean.FALSE;
        }
    }

    public static void main(String[] args) throws IllegalAccessException, IOException {
        copyFile("C:\\a\\b" ,"D:\\tmp");//进去的路径必须保证有，才能继续   file.seprator() / 相对路径:System.getProperty("user.dir")
    }

}
