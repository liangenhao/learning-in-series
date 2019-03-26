package com.enhao.learning.in.java.netty.basic;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 服务器端
 *
 * @author enhao
 */
public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        // 1. 创建一个boss线程组：接收客户端连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 2. 创建一个worker线程组，处理网络io操作
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        // 3. 创建服务器端启动助手，来配置参数
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup) // 设置两个线程组
                .channel(NioServerSocketChannel.class) // 使用NioServerSocketChannel服务器端通道。
                .option(ChannelOption.SO_BACKLOG, 128) // 设置线程队列中等待连接的数量
                .childOption(ChannelOption.SO_KEEPALIVE, true) // 保持活动连接状态
                .childHandler(new ChannelInitializer<SocketChannel>() { // 创建一个通道初始化对象

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new NettyServerHandler()); // 往pipeline 添加自定义handler类
                    }
                });
        System.out.println("......Server is ready......");
        // 绑定端口，非阻塞。
        // bind方法是异步，sync是同步阻塞。
        ChannelFuture channelFuture = serverBootstrap.bind(9999).sync(); // 异步
        System.out.println("......Server is starting......");


        // 关闭通道，关闭线程组
        channelFuture.channel().closeFuture().sync(); // 异步
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();

    }
}
