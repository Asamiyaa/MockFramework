package com.product;

/***
 *
 *1.devops--敏捷开发--将本地代码快速推到测试环境--docker...
 *  基本的linux-shell 网络(查看监听端口）  -- 从原有pdf进行解析记录
 *
 * ***** oracle.com相关指令文档 *** 去官网查看
 *
 * 2.基于JDK命令工具
 *      1.-xx参数
 *             1.-XX:[+1]<name> 启用或禁用参数
 *             2.-XX:<name>=<value>
 *         -Xms 等价于 -XX:initilaHeapSize
 *         -Xmx等价于  -XX:MaxHeapSize
 *         -Xss        -XX:ThreadstackSize
 *      2.jinfo
 *        获取参数值
 *           jinfo -flag ThreadstackSize 进程号
 *           jinfo -flags 进程号    查看进程下所有设置修改过的参数，可能是自己设置的，也可能是tomcat设置的
 *        java -XX:PrintFlagsFinal -version 得到参数键值  = 默认值   := 被用户或者jvm修改后的值
 *
 *        > a.txt 重定向到文件
 *      3.jstat
 *
 *      4.jmap+mat
 *            方式1.-XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=./   -->C:\YangWenjunData\mySrc\MockFramework11
 *            方式2.使用jmap  --> jps查看已开启进程 jmap -help  jmap -dump:format=b,file=heap.hprof 进程号
 *                      jmap -heap 进程号 查看进程信息
 *        jprofile初试
 *
 *       5.jstack 进程号 - 线程间状态转化 - 代码层级
 *          实战死循环导致cpu飙高：
 *                  1.现在本地此时代码可以访问
 *                  2.打包远程 --为了结合linux命令
 *                          1.mvn clean package
 *                          2.
 *          远程推送代码并启动：1.为了部署流程  2.为了 使用linux相关命令进行定位问题
 *                  1.切到项目目录 mvn clean package -Dmaven.test.skiip
 *                  2.nohup java -jar xxxx.jar &
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
 *
 *
 *
 */



public class PerformTool {



}
