package com.product;

public class Package {

    /**
     *
     *
     *1.为了熟练使用linux相关知识命令以便于排查问题
     2.部署包

     1. su
     2.输入当前用户密码  进入root


     3.复制-在xshell黏贴 修改文件 yum源  -- 注意版本  不要随意执行 这里是32位而不是64

     wget http://mirrors.163.com/centos/7/os/x86_64/Packages/

     wget http://mirrors.163.com/centos/7/os/x86_64/Packages/

     rpm -ivh --force rpm-4.11.3-40.el7.x86_64.rpm  yum-metadata-parser-1.1.4-10.el7.x86_64.rpm python-urlgrabber-3.10-9.el7.noarch.rpm yum-3.4.3-163.el7.centos.noarch.rpm    yum-plugin-fastestmirror-1.1.31-52.el7.noarch.rpm --nodeps --force


     rpm -ivh --nodeps http://mirrors.163.com/centos/7.5.1804/os/x86_64/Packages/yum-metadata-parser-1.1.4-10.el7.x86_64.rpm
     rpm -ivh --nodeps http://mirrors.163.com/centos/7.5.1804/os/x86_64/Packages/yum-plugin-fastestmirror-1.1.31-45.el7.noarch.rpm
     rpm -ivh --nodeps http://mirrors.163.com/centos/7.5.1804/os/x86_64/Packages/yum-3.4.3-158.el7.centos.noarch.rpm


     wget http://mirrors.163.com/centos/6/os/i386/Packages/yum-3.2.29-81.el6.centos.noarch.rpm
     wget http://mirrors.163.com/centos/6/os/i386/Packages/yum-metadata-parser-1.1.2-16.el6.i686.rpm
     wget http://mirrors.163.com/centos/6/os/i386/Packages/yum-plugin-fastestmirror-1.1.30-41.el6.noarch.rpm
     wget http://mirrors.163.com/centos/6/os/i386/Packages/python-iniparse-0.3.1-2.1.el6.noarch.rpm


     rpm -ivh python-iniparse-0.3.1-2.1.el6.noarch.rpm
     rpm -ivh yum-metadata-parser-1.1.2-16.el6.i686.rpm
     rpm -ivh yum-3.2.29-81.el6.centos.noarch.rpm yum-plugin-fastestmirror-1.1.30-41.el6.noarch.rpm --nodeps --force



     wget --no-cookies --no-check-certificate --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" "https://download.oracle.com/otn-pub/java/jdk/8u191-b12/2787e4a523244c269598db4e85c51e0c/jdk-8u191-linux-x64.tar.gz"

     4.配置相关工具

     5. jdk tomcat
     ---报错wget --no-check-certificate --no-cookies --header "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/8u181-b13/96a7b8442fe848ef90c96a2fad6ed6d1/jdk-8u181-linux-x64.tar.gz
     上传 rz 下载sz https://www.cnblogs.com/yyl6/p/9705980.html
     解压 - 安装 ：https://www.jianshu.com/p/5f9b4682a6c5
     tomcat :https://www.cnblogs.com/yw-ah/p/9770971.html

     当前问题 设置不生效。 需要手动在shell 中执行才可以 ---TODO***************
     export JAVA_HOME=/usr/jdk/jdk1.8.0_112
     export CLASSPATH=$JAVA_HOME/lib/
     export PATH=$PATH:$JAVA_HOME/bin
     export PATH JAVA_HOME CLASSPATH

     export JAVA_HOME=/usr/jdk/jdk1.8.0_112
     export JRE_HOME=$JAVA_HOME/jre
     sh /usr/tomcat/apache-tomcat-8.0.38/bin/startup.sh


     直接编译没作用



     6.固定ip  -- 暂时没有弄，因为担心网络会连不上外网 。linux系统host修改 https://www.cnblogs.com/heruiguo/p/7943006.html
     service iptables stop(但是重启后又会开启，所以是暂时的)：https://blog.csdn.net/qiuwenjie123/article/details/79394525
     未实：https://blog.csdn.net/qq_30421153/article/details/86065885  网络设置问题


     7.使用idea通过mvn连接远程服务器并部署包  -- mvn相关学习
     远程执行	https://www.cnblogs.com/lingluo2017/p/11529679.html
     打包没有找到。路径有问题
     直接打jar / war 过程对比

     0.肯定是idea可以启动并访问才去打包
     1.C:\YangWenjunData\mySrc\MockFramework11>mvn clean package -Dmaven.test.skip
     2.idea 打包的jar运行报 “XXX中没有主清单属性”:https://blog.csdn.net/banjing_1993/article/details/83073210
     3.打包后本地访问没问题 推到linux ----这个完全可以自动化
     4.nohup java -jar MockFramework-1.0-SNAPSHOT.jar &
     5.在虚拟机内部访问 因为远程访问不了 网络问题 ------TODO
     6.远程不是访问8080吗？端口看哪里？--后台一直在报错。连接不到jdbc --注意分析日志 而不是简单的报错信息
     7.拆出项目 不连db 否则启动都有问题  重新建一个项目-导入setting** --按照上面打包测试 没问题
     8.测试性能 - 关闭访问并没有解决因为后台是死循环，机器声音加大，因为cpu 巨大



     8。远程调试


     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     */

}
