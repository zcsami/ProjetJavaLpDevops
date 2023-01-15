package com.devops.loupgarou.tchatdemo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public class LeServeur {
    private static final Map<String, String> pseudos = new HashMap<>();
    private static final Map<String, String> clients = new HashMap<>();
    private static final Map<String, ChannelFuture> channels = new HashMap<>();

    public static void main(String[] args) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(8888))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel channel) {
                            channel.pipeline().addLast(new NettyServerHandler2());
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind().sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }

    public static Map<String, String> getPseudos() {
        return pseudos;
    }

    public static Map<String, String> getClients() {
        return clients;
    }

    public static Map<String, ChannelFuture> getChannels() {
        return channels;
    }

    public static void broadcast(String message) {
        for (Map.Entry<String, ChannelFuture> entry : channels.entrySet()) {
            entry.getValue().channel().writeAndFlush(message);
        }
    }
}

