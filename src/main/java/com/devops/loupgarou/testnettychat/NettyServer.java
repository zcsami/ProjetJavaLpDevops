package com.devops.loupgarou.testnettychat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.HashMap;
import java.util.Map;

public class NettyServer {
    private final int port;

    // La classe débute en définissant trois Maps statiques finales pour stocker les pseudonymes, les informations des clients et les canaux de communication
    // pour chaque client connecté.
    private static final Map<String, String> pseudos = new HashMap<>();
    private static final Map<String, String> clients = new HashMap<>();
    private static final Map<String, ChannelFuture> channels = new HashMap<>();

    public NettyServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        // On crée deux objets EventLoopGroup, un pour gérer les événements de connexion (bossGroup)
        // et un pour gérer les événements de communication (workerGroup).
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //On  crée un objet ServerBootstrap, qui est utilisé pour configurer et lancer le serveur.
            ServerBootstrap b = new ServerBootstrap();

            /**La méthode group de la classe ServerBootstrap permet de définir les groupes d'événements de connexion et
             * de communication (bossGroup et workerGroup)
             */


            b.group(bossGroup, workerGroup);

            /**La méthode channel de la classe ServerBootstrap permet de définir le type de canal utilisé
            // par le serveur Netty pour accepter les connexions entrantes. En utilisant NioServerSocketChannel.class,
            // le serveur utilise un canal de type NIO (Non-blocking Input/Output) pour gérer les connexions entrantes.
            // Les canaux NIO sont utilisés pour les systèmes d'entrée/sortie non bloquants, ce qui permet au serveur de gérer plusieurs connexions
            // simultanément sans bloquer l'exécution du programme.
             */
            b.channel(NioServerSocketChannel.class);


            /**La méthode childHandler de la classe ServerBootstrap permet de définir le gestionnaire d'événements qui sera utilisé pour initialiser
            // les canaux de communication pour les clients connectés. Le constructeur new NettyServerInitializer() crée une nouvelle instance de la classe
            // NettyServerInitializer qui est en charge de l'initialisation des canaux pour les clients connectés. Cela peut inclure la définition
            // des protocoles de communication, des traitements de données entrantes/sortantes, des gestionnaires de messages, etc.
             */
            b.childHandler(new NettyServerInitializer());

            /**permet de lier le serveur à un port spécifique et de synchroniser son démarrage.
            //La méthode bind de la classe ServerBootstrap permet de lier le serveur à un port spécifique, en utilisant la valeur de port définie
            // dans le constructeur de la classe NettyServer. Cette méthode retourne un objet ChannelFuture qui représente l'état futur du canal
            // de communication une fois que le serveur est lié au port.
            //La méthode sync de l'objet ChannelFuture permet de synchroniser le démarrage du serveur. Cela signifie que l'exécution
            // du programme est bloquée jusqu'à ce que le serveur soit entièrement démarré et prêt à accepter les connexions entrantes.
            // La variable "f" stockera l'état futur du canal une fois le serveur démarré.
            //En résumé, cette ligne de code permet de lier le serveur à un port spécifique et de synchroniser son démarrage,
            // la variable "f" stockera l'état futur du canal une fois le serveur démarré
             */

            ChannelFuture f = b.bind(port).sync();

            System.out.println("serveur connecté sur le port  :" + port );

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

