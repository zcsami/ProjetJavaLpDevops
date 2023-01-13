package com.devops.loupgarou.testnetty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // Send the first message when the channel is active
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // Handle incoming data
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Handle exceptions
    }
}