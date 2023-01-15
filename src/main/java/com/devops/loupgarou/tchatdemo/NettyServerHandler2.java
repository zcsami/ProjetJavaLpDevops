package com.devops.loupgarou.tchatdemo;

import io.netty.channel.ChannelHandler;
import io.netty.util.concurrent.EventExecutorGroup;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.Map;

public class NettyServerHandler2 extends ChannelInboundHandlerAdapter {
    private static final Map<String, String> pseudos = LeServeur.getPseudos();
    private static final Map<String, String> clients = LeServeur.getClients();
    private static final Map<String, ChannelFuture> channels = LeServeur.getChannels();

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        String address = ctx.channel().remoteAddress().toString();
        clients.put(ctx.channel().id().asShortText(), address);
        channels.put(ctx.channel().id().asShortText(), ctx.channel().closeFuture());

        System.out.println("Client connected : " + address);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        String message = (String) msg;
        String pseudo = pseudos.get(ctx.channel().id().asShortText());
        if (pseudo == null) {
            if (pseudos.containsValue(message)) {
                ctx.writeAndFlush("Ce pseudo est déjà utilisé, choisissez un autre pseudo : ");
            } else if (message.isEmpty()) {
                ctx.writeAndFlush("Vous devez entrer un pseudo : ");
            } else {
                pseudos.put(ctx.channel().id().asShortText(), message);
                ctx.writeAndFlush("Bienvenue " + message + " !\n");
                LeServeur.broadcast(message + " s'est connecté !\n");
            }
        } else {
            LeServeur.broadcast(pseudo + " : " + message + "\n");
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        String pseudo = pseudos.get(ctx.channel().id().asShortText());
        if (pseudo != null) {
          LeServeur.broadcast(pseudo + " s'est déconnecté !\n");
            pseudos.remove(ctx.channel().id().asShortText());
        }
        clients.remove(ctx.channel().id().asShortText());
        channels.remove(ctx.channel().id().asShortText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
