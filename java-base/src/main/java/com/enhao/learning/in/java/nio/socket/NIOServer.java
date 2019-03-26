package com.enhao.learning.in.java.nio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.security.Key;
import java.util.Iterator;
import java.util.Set;

/**
 * 服务端
 *
 * @author enhao
 */
public class NIOServer {

    public static void main(String[] args) throws IOException {
        // 得到ServerSocketChannel
        ServerSocketChannel channel = ServerSocketChannel.open();
        // 选择器对象
        Selector selector = Selector.open();
        // 绑定端口号
        channel.bind(new InetSocketAddress(9999));
        // 设置非阻塞方式
        channel.configureBlocking(false);
        // 注册：把ServerSocketChannel注册到Selector
        channel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            // 监控客户端
            // select 表示客户端被监控到的通道
            int select = selector.select(2 * 1000L);
            if (select == 0) { // 非阻塞优势，可以有其他操作
                System.out.println("Server：没有客户端，可以做的别的事");
                continue;
            }
            // 有客户端，监控事件
            // 得到 selectionKey
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey selectionKey = keyIterator.next();

                if (selectionKey.isAcceptable()) { // 客户端连接事件
                    System.out.println("OP_ACCEPT");
                    try {
                        SocketChannel socketChannel = channel.accept();
                        socketChannel.configureBlocking(false);
                        // 该通道监听读事件
                        socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (selectionKey.isReadable()) { // 读取客户端数据事件
                    System.out.println("OP_READ");
                    SocketChannel selectableChannel = (SocketChannel) selectionKey.channel();
                    ByteBuffer attachment = (ByteBuffer) selectionKey.attachment();
                    try {
                        selectableChannel.read(attachment);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Server：客户端发来数据：" + new String(attachment.array()));
                }

                // 手动从集合中移除当前key，防止重复处理
                keyIterator.remove();
            }
        }
    }
}
