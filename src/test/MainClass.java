package util.mail;

/**
 * 
 *  @author  YangWenjun
 *  @date   2018年10月8日
 *  @description
 *                              通常在定时任务quartz失败后会发送相关信息给管理员，JavaMail
 *   *                             1.是什么
 *                                 2.为什么
 *                                 3.如何做
 *                               					  1.下载对应的jar       
 *                                                           JavaMail是SUN提供给开发人员在应用程序中实现邮件发送和接收功能而提供的一套标准开发类库，支持常用的邮件协议，如SMTP、POP3、IMAP，
 *                                                           开发人员使用JavaMail编写邮件程序时，无需考虑底层的通信细节(Socket)，JavaMail也提供了能够创建出各种复杂MIME格式的邮件内容的API。使用JavaMail，
 *                                                           我们可以实现类似OutLook、FoxMail的软件。虽然JavaMail(仅支持JDK4及以上)也是Java的API之一，但是却没有直接加入到JDK中，所以我们需要另行下载。
 *                                                           另外，JavaMail依赖JAF(JavaBeans Activation Framework)，JAF在Java6之后已经合并到JDK中，而JDK5之前需要另外下载JAF的类库。
 *                                                           详见：https://blog.csdn.net/ghsau/article/details/17839983

                                 					  2.代码实现：http://www.runoob.com/java/java-sending-email.html
                                 
 *                                 4.对比 原理  改进                               
 *                                                1.为什么服务器内网可以发送邮件到外网？说明内网可以访问外网？那么外网可以访问内网吗？
 *                                                2.内网发往内网；自己搭建接收服务器 https://blog.csdn.net/yy339452689/article/details/78062727
 *  @todo  TODO
 */
public class MainClass{

}
