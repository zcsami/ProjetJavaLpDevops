package com.devops.loupgarou.testnettychat;




import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast("encoder",new ObjectEncoder());
        ch.pipeline().addLast( "decoder",new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
        ch.pipeline().addLast(new NettyServerHandler());
    }
}
/**
 * La classe NettyServerInitializer est une sous-classe de ChannelInitializer, qui est utilisée pour initialiser les canaux de communication
 * pour les clients connectés.
 * Cette classe est spécifique à l'implémentation de Netty et est utilisée pour ajouter des traitements de données ou
 * des gestionnaires de messages aux canaux pour les clients connectés.
 *
 * L'annotation @Override indique que la méthode initChannel est une surcharge de la méthode de même nom de la classe parente ChannelInitializer.
 * La méthode initChannel prend en paramètre un objet SocketChannel qui représente le canal de communication pour un client connecté.
 *
 * La méthode initChannel ajoute un nouveau traitement de données (NettyServerHandler) à la pipeline (pipeline()) de traitement de données du canal
 * de communication en utilisant la méthode addLast().
 * NettyServerHandler est une classe qui gère les événements de communication (données entrantes et sortantes) pour un client connecté via ce canal.
 * Les traitements ajoutés à la pipeline seront exécutés dans l'ordre dans lequel ils ont été ajoutés.
 */