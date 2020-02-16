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
 *            方式2.使用jmap  --> jps jmap -help  jmap -dump:format=b,file=heap.hprof 进程号
 *                      jmap -heap 进程号 查看进程信息
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
