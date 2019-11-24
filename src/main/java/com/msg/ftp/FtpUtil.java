package com.msg.ftp;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;

/**
 * 
 *  @author  YangWenjun
 *  @date   2018年10月8日
 *  @description          定时任务
 *   *                             1.是什么   
 *                                                       1.分类及优缺点                                                                                                    优点                                                                                                                                    缺点
 *                                                             1.操作系统（OS）级别(linux的crontab、windows自带的计划任务)             
 *                                                                     优点：OS级不用专门开启监听器，占用系统资源较少，而且操作简便，是定时任务首选的实现方式
 *                                                                     缺点：当任务数量非常大，而且任务与任务之间有因果关系、先后顺序、竞争条件的话，OS级别的定时任务管理器就很难满足需求了；
 *                                                             2.编程语言自带的定时任务管理器，例如Java的timer scheduled和TimeTask
 *                                                                     优点：API提供的接口功能简单
 *                                                                     缺点：由于所有任务都是由同一个线程来调度，因此所有任务都是串行执行的，同一时间只能有一个任务在执行，前一个任务的延迟或异常都将会影响到之后的任务。往往不能满足用户定时任务设置需要，所以在项目开发过程中很少使用；
 *                                                              2.1ScheduledExecutor  线程池
 *                                                                     优点：解决并发
 *                                                                     缺点：Timer 和 ScheduledExecutor 都仅能提供基于开始时间与重复间隔的任务调度，不能胜任更加复杂的调度需求。比如，设置每星期二的 16:38:10 执行任务。
 *                                                              2.2 ScheduledExecutor 和 Calendar 实现复杂任务调度
 *                                                             3.Quartz框架
 *                                                               
 *                                                                 ***以上代码实现：https://www.ibm.com/developerworks/cn/java/j-lo-taskschedule/index.html
 *                                                                 
 *                                                                 对于简单的基于起始时间点与时间间隔的任务调度，使用 Timer 就足够了；如果需要同时调度多个任务，基于线程池的 ScheduledTimer 是更为合适的选择；
 *                                                                 当任务调度的策略复杂到难以凭借起始时间点与时间间隔来描述时，Quartz 与 JCronTab 则体现出它们的优势。
 *                                                                 熟悉 Unix/Linux 的开发人员更倾向于 JCronTab，且 JCronTab 更适合与 Web 应用服务器相结合。Quartz 的 Trigger 与 Job 松耦合设计使其更适用于 Job 与
 *                                                                 Trigger 的多对多应用场景。
 *                                 2.为什么
 *                                 3.如何做
 *                                                             quarz使用：http://student-lp.iteye.com/blog/2093395
 *                                 4.对比 原理  改进 
 *  @todo  TODO
 */


/**
 *
 * FTP工具类
 *
 * 下面只做了upload  相应的download到时再分析
 *
 * 参数来自propertyLoad工具获取
 */
public class FtpUtil {
//静态方法 vs 对象
    //log对象
    private static Logger log= Logger.getLogger(FtpUtil.class);
    private static final String[] FILE_TYPE = {"文件" , "目录" , "符号链接" , "未知类型"};
    private static final int BINARY_FILE_TYPE = FTP.BINARY_FILE_TYPE;
    private static final int ASCII_FILE_TYPE     = FTP.ASCII_FILE_TYPE ;

    //批量上传
    //将链接要素放置property并加载

   public static void uploadFiles(String[] configs , String[] remoteFiles , String[] localFiles , boolean ...  isBinary) throws Exception{
        //核对参数
       checkArguments(configs, remoteFiles ,localFiles , isBinary);
       FTPClient client = null ;

       try {//写完代码之后也可以try catch   选中-右键 ...  重构也可以通过右键 ...充分利用ide快捷键
           //创建链接
           client = connectServer(configs[0], Integer.valueOf(configs[1]), configs[2], configs[3]);
           //上传文件
           File remoteFile ;
           String workDir = "";
           for(int i = 0 ; i< remoteFiles.length ; i++){
                remoteFile = new File(remoteFiles[i]);
               try {
                   workDir = remoteFile.getParent();          //通过file来属性来完成linux操作
                   //本地创建文件夹	file.mkdirs() -> 服务器上创建创建文件夹：FTPClient client.mkdir..  其实是不分只是这里使用client无须再取new File....xx
                   if(!client.changeWorkingDirectory((workDir)))  makeDirectory(client,workDir) ;
                   client.changeWorkingDirectory((workDir)) ;
            } catch (Exception e) {
                e.printStackTrace();
                //log 文件上传失败    --具体原因是在log中的日志记录中
            }
               uploadFile(client ,remoteFile.getName() , localFiles[i] , getFileType(i , isBinary));
       }
}finally{
                  closeConnect(client);
}
}

   private static void closeConnect(FTPClient client) {
      try {
        if(client != null){
              client.logout();
              client.disconnect();
              //log
          }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

private static boolean  uploadFile(FTPClient client, String fileName, String localFile, boolean fileType) {
        boolean flag = true ;
        FileInputStream fis = null ;
        if(client == null ) return false ;
        try {
            if(fileType){
                client.setFileType(FTP.BINARY_FILE_TYPE);
            }else{
                client.setFileType(FTP.ASCII_FILE_TYPE);
            }

            client.enterLocalPassiveMode();
            client.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
            File file = new File(localFile);
            if(!file.exists()){
                //log
                //exception
            }
            fis = new FileInputStream(file);
            flag = client.storeFile(fileName, fis);
            if(flag){
                //log
            }else{
                //log
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fis != null)
                try {
                    fis.close();
                } catch (IOException e) {
                    //log
                    e.printStackTrace();
                }
        }
        return flag ;
}


public static boolean getFileType(int index , boolean[]  isBinary){
       return isBinary.length == 1 ?  isBinary[0] :isBinary[index] ;
   }

    //文件夹名称不能包含特殊字符 如\  / : * ? " < > ....
    private static boolean makeDirectory(FTPClient client, String workDir) {
     //注意不同服务器上的分割符,转义
    //关于使用FTPClient创建文件夹并上传文件遇到的问题  http://airball.iteye.com/blog/2212138  ****
if(client == null) return  false;   //通过返回boolean来判断    vs  返回void 内部通过异常来处理

workDir = StringUtils.replace(workDir, File.separator, "/");
boolean flag = true ;
String[] array = StringUtils.split("/");
String tmp = "";
for(int i = 0 ;i < array.length ;i++){               //一个一个创建目录，避免分割符造成的无法创建 详见：http://airball.iteye.com/blog/2212138 ==> 为什么不直接使用分隔符区分在去创建呢？
           tmp += "/" + array[i];
           try{
               if(!client.changeWorkingDirectory(tmp)){
                   flag = client.makeDirectory(tmp);
                   client.cwd(workDir);
                   if(flag){
                       //log
                   }else{
                       //log
                       //异常
                   }
               }
           } catch(IOException e){
               e.printStackTrace();
               //异常  自定义异常并添加描述信息   vs  自定义异常   不是每个都使用种类 乱  描述信息
           }
} return flag ;
    }




    private static void checkArguments(String[] configs, String[] remoteFiles, String[] localFiles, boolean[] isBinary) throws Exception {
        if(configs == null || configs.length != 4)  throw new Exception("登陆参数有误");
        if(remoteFiles == null || localFiles == null || remoteFiles.length ==0 || (remoteFiles.length != localFiles.length))  throw new Exception("文件参数有误");
        if(isBinary == null || configs.length != 0)  throw new Exception("文件传输类型有误");
}



    private static FTPClient connectServer(String ip, int port, String username, String password) {
        int reply ;
        FTPClient client = null ;
        try {
             client = new FTPClient();
             client.setControlEncoding("UTF-8");
             client.setDefaultPort(port);
             client.connect(ip);
             client.setDataTimeout(120000);
             client.login(username, password);
             if (!FTPReply.isPositiveCompletion(client.getReplyCode())) {
                 client.disconnect();
                //return null;
                //记录日志  + 抛异常结束
            }
        }
        catch (SocketException e) {
            //记录日志  + 抛异常结束
        }
        catch (IOException e) {
            //记录日志  + 抛异常结束
        }
        return client;
    }

    /**
     * 断开FTP链接
     *
     * @param ftp
     */
    private static void disconnectFtp(FTPClient ftp) {
        if (ftp != null && ftp.isConnected()) {
            try {
                // 退出ftp
                ftp.logout();
                ftp.disconnect();
            }
            catch (IOException ioe) {
             //   logger.error("断开FTP链接时发生IO错误：", ioe);
            }
        }
    }




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

    public static void main(String[] args) throws UnsupportedEncodingException {

    }
}




/**
 *---cron-------- 
 *    定时任务   CheckStlTotalServiceImpl 中 获得对应的service 执行指定方法
 *    Timer和TimerTask详解配合calendar          https://blog.csdn.net/ahxu/article/details/249610
 *    spring定时任务详解（@Scheduled注解） https://blog.csdn.net/qq_33556185/article/details/51852537
 *    quarz
 *    都是基于本地的cron实现的。
 *
 ----download----------https://my.oschina.net/xsh1208/blog/1036758 
      通过参数【远程地址 远程文件  本地路径】下载     通过commons-net-1.4.1-osgi.jar FTPClient  //判断 - 最后client.retrieveFile() 完成
 *           1.FtpUtil  downloadFtiles(String[] configs , String[] remoteFiles ,String[] localFiles , boolean ... isBinarys)  //提供下载多个文件  <--getProperty 通过加载本地property载入
 *           2.checkArguments
 *           3.FTPclient client = connectServer(config[0] , Integer.valueOf(config[1]) ,config[2] ,config[3] ,"/") 设置ip 端口 登陆 并设置超时..
 *           4.client chageWorkingDirectory(ermoteFile.getParent())
 *           5.downloadFile(FTPClient client ,String remoteFile ,String localFile ,boolean binaryTransfer)
 *                 1.seprator 拼接绝对路径
 *                 2.文件夹 文件是否存在  是否create File相关api
 *                 3.client.retrieveFile(remoteFile ,new FileOutputStream(localFile))
 *     解析文件入库（手动录入基础文件）  
 *           1./ zip包  通过new TarInputStream / GZIPInputStream /... 将需要下载的文件转为流  将该流转入 BufferOutputStream
 *            GzipInputStream in = new GzipInputStream(new FileInputStream(gzipPath))
 *            byte[]  buf = new byte[1024];
 *            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(file,jypath)))
 *            int len = -1 
 *            while((len = in.read(buf))!= -1){
 *                out.write(buf,0.length);   将指定 byte 数组中从偏移量 off 开始的 len 个字节写入此缓冲的输出流。
 *            }
 *            
 *            扩展：ByteArrayOutputStream  out.toString(encoding) *          
 *    入库	saveStorge  
 *           0.因为这些逻辑都是近似的，所以通过if判断来new对用的数据库对象 塞值。
 *           1.循环 readLine -->从文件每行对应着数据库每行记录 
 *           2.判断某个值obj[i] 是否为null? 0.0 / "0.0' :  类型转换 / 长度截取 / replace ......
      注意：  各种异常捕获，流的关闭 日志记录
      
      终止文件***终止标识 
 */
	
	
	
	
	/**
	 * ftpClient.makeDirectory(path) 一直返回false，无法创建目录的问题
	 * 原因： 由于我使用的是普通账号登录，所以一开始就设置chroot_local_user=YES，将用户禁锢在了宿主目录，导致始终无法创建目录。但是可以上传文件，不过，上传的文件最终也只能存放在宿主目录下，即 /home/test/xxx.txt。
	 */
	
	/**
	 * Apache FTPClient下载文件取不到的问题
	 * private static String encoding = System.getProperty("file.encoding");
		ftpClient.changeWorkingDirectory(new String(remotePath.getBytes(encoding),"iso-8859-1"));
		ftpClientInFunction.retrieveFile(new String(fInFunction.getName().getBytes("GBK"),"iso-8859-1"), is);
		
		1、编码问题
		在FTP协议里面，规定文件名编码为iso-8859-1，所以目录名或文件名需要转码。
		接下来的问题是，我们应该将什么编码转换为此格式。因此，就有了第二种解决方案——把GBK格式的转换为ISO-8859-1格式。
		而且，有的人还说，必须得这么转。其实，之所以他们能这么说，我觉得完全是巧合。它的真正原理是，既然FTP协议规定的编码格式是“ISO-8859-1”，那么我们确实得将格式转换一下，然后等服务器收到文件时再自动转换为系统自带的编码格式，因此，关键不是规定为什么格式，而是取决于FTP服务器的编码格式。因此，如果FTP系统的编码格式为“GBK”时，第二种方式肯定会成功；但是，如果系统的编码格式为“UTF-8”时，那就会仍然出现乱码啦。所以，我们只能通过代码先获取系统的编码格式，然后通过此编码格式转换为ISO-8859-1的编码格式。获取方式如下：
		private static String encoding = System.getProperty("file.encoding");
	 */
	
