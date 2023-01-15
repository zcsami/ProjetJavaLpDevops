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
    //private final Bootstrap bootstrap;

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
        
    }

    public void run() throws Exception {

        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new NettyClientInitializer(this));
        // Connect to the server
        Channel channel = bootstrap.connect(host, port).sync().channel();

        Scanner scanner = new Scanner(System.in);
        // Read input from user
        System.out.print("Tapez votre pseudo: (NettyClient) ");

        String pseudo = scanner.nextLine();


        channel.writeAndFlush(pseudo + "envoyé depuis WriteandFlush après pseudo");



        while (true) {
            System.out.print("Entrez votre message (boucle WHILE TRUE NettyCleint) : ");
            String message = scanner.nextLine();

            channel.writeAndFlush(message + "envoyé depuis Nettyclient (WHILE RUN)");
        }
    }

    public static void main(String[] args) throws Exception {
        new NettyClient("localhost", 8081).run();
    }
}


