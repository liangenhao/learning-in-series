package com.enhao.learning.in.java.nio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * 客户端
 *
 * @author enhao
 */
public class NIOClient {
    public static void main(String[] args) throws IOException {
        // 得到通道
        SocketChannel channel = SocketChannel.open();
        // 设置阻塞方式:非阻塞
        channel.configureBlocking(false);
        // 提供服务器的ip地址和端口号
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 9999);
        // 连接服务器端
        boolean connect = channel.connect(address);
        if (!connect) {
            while(!channel.finishConnect()) {
                System.out.println("Client：连接服务器端的同时，还可以做别的事情");
            }
        }

        // 得到缓冲区，并存入数据
        String message = "hello,server";
        ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
        // 发送数据
        channel.write(buffer);


        System.in.read();
    }
}
