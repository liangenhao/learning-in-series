package com.enhao.learning.in.java.netty.basic;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 服务器端业务处理
 * 服务端这里用来读数据，继承ChannelInboundHandlerAdapter
 *
 * @author enhao
 * @see ChannelInboundHandlerAdapter
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取数据
     *
     * @param ctx 上下文对象
     * @param msg 数据
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("客户端发来的消息：" + byteBuf.toString(CharsetUtil.UTF_8));
    }

    /**
     * 数据读取完毕
     *
     * @param ctx 上下文对象
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 数据读取完毕，回复一个消息
        ctx.writeAndFlush(Unpooled.copiedBuffer("收到消息", CharsetUtil.UTF_8));
    }

    /**
     * 异常捕获
     *
     * @param ctx   上下文对象
     * @param cause 异常
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
