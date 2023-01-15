package com.devops.loupgarou.tchatdemo;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class LeClient {

    public static void main(String[] args) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress("localhost", 8888)
                    .handler(new NettyClientInitializer2());

            Channel channel = bootstrap.connect().sync().channel();

            System.out.println("pseudo?");
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            String pseudo = in.readLine();
            while (LeServeur.getPseudos().containsValue(pseudo)) {
                System.out.println("Pseudo déjà utilisé, veuillez en choisir un autre : ");
                pseudo = in.readLine();
            }

           LeServeur.getPseudos().put(channel.id().asShortText(), pseudo);
            System.out.println("Bienvenue " + pseudo + " !");

            while (true) {
                String message = in.readLine();
                channel.writeAndFlush(pseudo + " : " + message + "\n");
            }
        } finally {
            group.shutdownGracefully().sync();
        }
    }
}

