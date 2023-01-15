/**
 * La classe NettyServerHandler est une sous-classe de ChannelInboundHandlerAdapter,
 * qui est utilisée pour gérer les événements de communication pour les clients connectés au serveur.
 * Cette classe est spécifique à l'implémentation de Netty et permet de gérer les données entrantes et sortantes,
 * les connexions et les déconnexions des clients, ainsi que les exceptions éventuelles.
 * La classe définit trois maps statiques finales pseudos, clients et channels qui sont utilisées pour stocker les pseudonymes,
 * les informations des clients et les canaux de communication pour chaque client connecté. Ces maps sont récupérées à partir de
 * la classe NettyServer via les méthodes statiques getPseudos(), getClients() et getChannels().
 *
 * L'annotation @Override indique que les méthodes de cette classe sont des surcharges de méthodes de même nom de la classe parente ChannelInboundHandlerAdapter.
 */


package com.devops.loupgarou.testnettychat;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Map;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    private static final Map<String, String> pseudos = NettyServer.getPseudos();
    private static final Map<String, String> clients = NettyServer.getClients();
    private static final Map<String, ChannelFuture> channels = NettyServer.getChannels();

    /**
     La méthode channelActive est exécutée lorsqu'un nouveau client se connecte au serveur.
     Il enregistre l'adresse IP du client connecté dans la map "clients", ajoute le futur canal de communication pour ce client
     dans la map "channels" et affiche un message indiquant que le client s'est connecté.
     */

    /**
     *La classe ChannelHandlerContext est un objet fourni par Netty qui permet aux développeurs de gérer les événements de communication
     * pour les clients connectés. Il est passé en paramètre pour les méthodes d'événements de la classe ChannelInboundHandlerAdapter
     * (comme channelActive, channelRead, channelInactive, etc.).
     *
     * Il offre une variété de fonctionnalités pour gérer les canaux de communication, y compris l'accès aux données entrantes et sortantes,
     * la gestion des états de connexion, l'envoi de messages aux clients, etc. Il est utilisé dans cette méthode pour récupérer l'adresse IP du client connecté,
     * pour récupérer l'identifiant unique du canal de communication pour ce client, et pour récupérer l'état futur de ce canal de communication.
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        String address = ctx.channel().remoteAddress().toString();
        clients.put(ctx.channel().id().asShortText(), address);
        channels.put(ctx.channel().id().asShortText(), ctx.channel().closeFuture());

        System.out.println("Client connected : " + address );
        System.out.println(ctx.channel().id().toString());
        System.out.println(pseudos.values());
        //broadcast("je broadcast");
        //System.out.println("je print");

    }


    /**
     *
     La méthode channelRead est exécutée lorsqu'un client envoie des données au serveur. Il vérifie si le client a déjà choisi un pseudo,
     si ce n'est pas le cas il vérifie si le pseudo est déjà utilisé, si il est vide ou non. Si il est valide, il est enregistré dans
     la map "pseudos" et un message de bienvenue est envoyé au client. Sinon il broadcast le message reçu avec le pseudo du client.
     */
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
                ctx.writeAndFlush("Bienvenue " + message + "!");
                broadcast(message + " s'est connecté !");
            }
        } else {
            broadcast(pseudo + " : " + message + "\n");
        }
    }


    /**
     *La méthode channelInactive est exécutée lorsqu'un client se déconnecte du serveur. Il supprime les informations du client de
     * la map "pseudos", "clients" et "channels" et affiche un message indiquant que le client s'est déconnecté.

     */

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        String disconnectedClient = pseudos.get(ctx.channel().id().asShortText());
        pseudos.remove(ctx.channel().id().asShortText());
        clients.remove(ctx.channel().id().asShortText());
        channels.remove(ctx.channel().id().asShortText());
        System.out.println("Client disconnected : " + disconnectedClient);
        broadcast(disconnectedClient + " s'est déconnecté !\n");
    }


    /**
     *
     La méthode exceptionCaught est exécutée lorsqu'une exception est levée lors de la communication avec un client.
     Il affiche un message d'erreur et ferme le canal de communication avec ce client.
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    /**
     La méthode privée broadcast permet de diffuser un message à tous les clients connectés en utilisant
     la map "channels" pour accéder aux canaux de communication de chaque client et en
     utilisant la méthode writeAndFlush pour envoyer le message à chaque client.
     */
    private void broadcast(String message) {
        channels.forEach((id, channel) -> channel.channel().writeAndFlush(message));
    }


}

