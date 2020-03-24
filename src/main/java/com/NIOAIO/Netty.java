package com.NIOAIO;

/**
 * @author YangWenjun
 * @date 2020/1/22 17:30
 * @project MockFramework
 * @title: Netty
 * @description:
 *          1.对比netty和tomcat
 *              真正需要业务代码来实现的就两个部分：一个是把服务初始化并启动起来，还有就是，实现
                收发消息的业务逻辑 MyHandler。而像线程控制、缓存管理、连接管理这些异步网络 IO
                中通用的、比较复杂的问题

                // 创建一组线性
                EventLoopGroup group = new NioEventLoopGroup();

                try{
                // 初始化 Server
                ServerBootstrap serverBootstrap = new ServerBootstrap();
                serverBootstrap.group(group);
                serverBootstrap.channel(NioServerSocketChannel.class);
                serverBootstrap.localAddress(new InetSocketAddress("localhost", 9999));

                // 设置收到数据后的处理的 Handler
                serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {

                protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(new MyHandler());
                }
                // 绑定端口，开始提供服务
                ChannelFuture channelFuture = serverBootstrap.bind().sync();
                channelFuture.channel().closeFuture().sync();


            2.流的包装和恰当选择
                netty 框架 - 使用场景
                nio模型更像服务员，传统线程池就是固定的几个服务员，一致到做完或者异常。
                这里就好像服务员，当吃饭(不可读 无需求)去服务其他。
                整体的线程就好像cpu,不保存状态，状态由各个io保存。只是无状态的执行。selector
                一个 I/O 线程可以并发处理 N 个客户端连接和读写操作，这从根本上解决了传统同步阻塞 I/O 一连接一线程模型，架构的性能、弹性伸缩能力和可靠性都得到了极大的提升。

                基于 Buffer 操作不像传统 IO 的顺序操作，NIO 中可以随意地读取任意位置的数据。


                我们设计一个事件处理模型的程序有两种思路：

                1）轮询方式：线程不断轮询访问相关事件发生源有没有发生事件，有发生事件就调用事件处理逻辑；

                2）事件驱动方式：发生事件，主线程把事件放入事件队列，在另外线程不断循环消费事件列表中的事件，调用事件对应的处理逻辑处理事件。事件驱动方式也被称为消息通知方式，其实是设计模式中观察者模式的思路。

                netty 通信级别  微服务底层... 框架 - 服务器（netty tomcat）


 *
 *
 *
 */
public class Netty {



}
