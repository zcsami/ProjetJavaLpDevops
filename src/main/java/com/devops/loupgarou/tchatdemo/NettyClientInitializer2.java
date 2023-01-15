package com.devops.loupgarou.tchatdemo;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class NettyClientInitializer2 extends ChannelInitializer<SocketChannel> {

    @Override
    public void initChannel(SocketChannel ch) {
        ch.pipeline().addLast(new NettyClientHandler2());
    }
}
