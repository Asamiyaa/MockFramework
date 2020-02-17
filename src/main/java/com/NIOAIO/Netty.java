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
            2.
 *
 *
 *
 */
public class Netty {



}
