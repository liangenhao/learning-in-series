package com.enhao.learning.in.java.nio.file;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 文件io操作
 *
 * @author enhao
 */
public class NIOTest {

    /**
     * 往本地文件中写数据
     */
    @Test
    public void test1() throws IOException {
        // 创建输出流
        FileOutputStream fileOutputStream = new FileOutputStream("base.txt");
        // 从流中得到通道
        FileChannel channel = fileOutputStream.getChannel();
        // 提供缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // 往缓冲区存入数据
        String content = "hello nio";
        buffer.put(content.getBytes());
        // 反转缓冲区,buffer的当前位置更改为buffer缓冲区的第一个位置
        buffer.flip();
        // 把缓冲区写入通道
        channel.write(buffer);
        // 关闭
        fileOutputStream.close();
    }

    // 从本地文件中读取数据
    @Test
    public void test2() throws IOException {
        // 创建输入流
        FileInputStream fileInputStream = new FileInputStream("base.txt");
        // 从流中得到通道
        FileChannel channel = fileInputStream.getChannel();
        // 提供缓冲区
        File file = new File("base.txt");
        ByteBuffer buffer = ByteBuffer.allocate((int) file.length());
        // 从通道读取数据并存在缓冲区
        channel.read(buffer);
        System.out.println(new String(buffer.array()));
        // 关闭流
        fileInputStream.close();
    }

    // 复制文件
    @Test
    public void test3() throws IOException {
        // 创建输入流和输出流
        FileInputStream fileInputStream = new FileInputStream("base.txt");
        FileOutputStream fileOutputStream = new FileOutputStream("base-copy.txt");
        // 得到通道
        FileChannel sourceChannel = fileInputStream.getChannel();
        FileChannel destChannel = fileOutputStream.getChannel();
        // 复制，两种方式二选一
        destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
        // sourceChannel.transferTo(0, sourceChannel.size(), destChannel);
        // 关闭
        fileOutputStream.close();
        fileInputStream.close();

    }
}
