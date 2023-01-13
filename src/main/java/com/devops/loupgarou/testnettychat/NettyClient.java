package com.devops.loupgarou.testnettychat;

import io.netty.bootstrap.Bootstrap;

import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.util.Scanner;

public class NettyClient {
    private final String host;
    private final int port;
    private final Bootstrap bootstrap;

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
        EventLoopGroup group = new NioEventLoopGroup();
        bootstrap = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new NettyClientInitializer(this));
    }

    public void run() throws Exception {
        // Connect to the server
        Channel channel = bootstrap.connect(host, port).sync().channel();
        Scanner scanner = new Scanner(System.in);
        // Read input from user
        System.out.println("Tapez votre pseudo : ");
        String pseudo = scanner.nextLine();
        channel.writeAndFlush(pseudo + "\r\n");

        while (true) {
            System.out.print("Entrez votre message : ");
            String message = scanner.nextLine();
            channel.writeAndFlush(message + "\r\n");
        }
    }

    public static void main(String[] args) throws Exception {
        new NettyClient("localhost", 8081).run();
    }
}


