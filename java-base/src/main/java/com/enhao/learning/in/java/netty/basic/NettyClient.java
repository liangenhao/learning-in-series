package com.enhao.learning.in.java.netty.basic;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 客户端
 *
 * @author enhao
 */
public class NettyClient {

    public static void main(String[] args) throws InterruptedException {
        // 创建一个线程组
        EventLoopGroup group = new NioEventLoopGroup();
        // 创建一个客户端启动助手，完成配置
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group) // 设置线程组
                .channel(NioSocketChannel.class) // 设置客户端通道
                .handler(new ChannelInitializer<SocketChannel>() { // 创建一个通道初始化对象
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new NettyClientHandler()); // 往pipeline链中添加自定义handler
                    }
                });
        System.out.println("......Client is ready......");
        // 启动客户端连接服务器（异步非阻塞）
        // connect是异步的，sync是同步阻塞。
        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 9999).sync();
        System.out.println("......Client is starting......");

        // 关闭连接，异步非阻塞
        channelFuture.channel().closeFuture().sync();

    }
}
