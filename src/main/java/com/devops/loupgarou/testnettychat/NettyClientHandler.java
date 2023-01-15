package com.devops.loupgarou.testnettychat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    private final NettyClient client;

    public NettyClientHandler(NettyClient client) {
        this.client = client;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        System.out.println(msg.toString());
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}

/*
Voici un exemple de classe NettyClientHandler qui peut être utilisée pour gérer les événements de communication pour un client Netty:
Cette classe hérite de ChannelInboundHandlerAdapter, elle contient une référence à l'instance de NettyClient qui est passée
lors de la construction de l'objet. Elle surcharge la méthode channelRead() pour afficher les messages reçus du serveur sur
la console, et la méthode exceptionCaught() pour gérer les exceptions qui pourraient survenir lors de la communication avec
le serveur. Il est important de noter que c'est un exemple très basique et il y a beaucoup de choses à prendre en compte pour
une utilisation efficace de net
 */
/**
 * La classe NettyClient que vous avez fournie est une implémentation de base d'un client Netty. Elle utilise le modèle de
 * client-serveur pour se connecter à un serveur à l'aide de l'adresse IP et du port spécifiés.
 *
 * Elle initialise un objet Bootstrap qui va servir à configurer et connecter le client au serveur.
 * Elle utilise un objet EventLoopGroup pour gérer les événements de connexion et de communication avec le serveur.
 * Elle utilise un objet NioSocketChannel pour créer un canal de communication avec le serveur.
 * Elle utilise un objet NettyClientInitializer pour initialiser le pipeline de traitement des données pour les nouveaux canaux de
 * clients connectés.
 * Elle utilise la méthode connect de Bootstrap pour se connecter au serveur en utilisant l'adresse IP et le port spécifiés.
 * En ce qui concerne l'envoi des messages en broadcast, vous devrez ajouter une logique pour gérer les messages reçus et
 * les diffuser à tous les clients connectés.
 *
 * Il est possible de faire cela en utilisant la liste des canaux de clients connectés
 * que vous avez maintenue dans la classe NettyServer et en utilisant la méthode writeAndFlush pour envoyer le message à t
 * ous les clients connectés. Il est important de vérifier si toutes les étapes de broadcast sont bien effectuées coté serveur
 * pour pouvoir diffuser le message à tous les clients.
 */