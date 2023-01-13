package com.devops.loupgarou.testnettychat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.HashMap;
import java.util.Map;

public class NettyServer {
    private final int port;
    private static final Map<String, String> pseudos = new HashMap<>();
    private static final Map<String, String> clients = new HashMap<>();
    private static final Map<String, ChannelFuture> channels = new HashMap<>();

    public NettyServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup);
            b.channel(NioServerSocketChannel.class);
            b.childHandler(new NettyServerInitializer());
            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(port).sync();
            System.out.println("serveur connecté sur le port " + port);

            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port;
        if (args.length == 1) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8081;
        }
        new NettyServer(port).run();
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
}

/*

Dans cette classe, j'ai déclaré 3 Maps pour stocker les pseudos, les informations des clients
 connectés (IP et port) et les canaux de communication pour chaque client connecté. Il utilise la classe
 `NettyServerInitializer` pour gérer les connections des clients, stocker les pseudos et les informations
 des clients connectés, et diffuser les messages à tous les clients connectés. Il est important de noter que c'est un
 exemple très basique et il y a beaucoup de choses à prendre en compte pour une utilisation efficace de netty.
 Il est préférable de

 */


/**
 *serveur Netty. Elle utilise le modèle de client-serveur pour accepter les connexions des clients et gérer
 * les échanges de données entre les clients connectés.
 *
 * Elle utilise des objets EventLoopGroup pour gérer les événements de connexion et de communication avec les clients.
 * Elle utilise un objet ServerBootstrap pour configurer et lancer le serveur.
 * Elle utilise un objet NioServerSocketChannel pour créer un canal d'écoute pour les nouvelles connexions de clients.
 * Elle utilise un objet NettyServerInitializer pour initialiser le pipeline de traitement des données pour l
 * es nouveaux canaux de clients connectés.
 * Elle utilise la méthode bind de ServerBootstrap pour écouter les connexions entrantes sur le port spécifié.
 *
 * Dans cette implémentation, nous avons 3 Maps pour stocker
 * les pseudos,
 * les informations des clients connectés (IP et port) et
 * les canaux de communication pour chaque client connecté.
 * Cela vous permet de gérer les connections
 * des clients, stocker les pseudos et les informations des clients connectés, et diffuser les messages à tous les clients connectés.
 */