package com.devops.loupgarou.testnettychat;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {
    private final NettyClient client;
    public NettyClientInitializer(NettyClient client) {
        this.client = client;
    }
    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new ObjectEncoder());
        ch.pipeline().addLast( new ObjectDecoder(ClassResolvers.cacheDisabled(null)));

        ch.pipeline().addLast(new NettyClientHandler(client));
    }
}
