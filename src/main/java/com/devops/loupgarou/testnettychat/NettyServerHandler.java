package com.devops.loupgarou.testnettychat;



import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.Map;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    private static final Map<String, String> pseudos = NettyServer.getPseudos();
    private static final Map<String, String> clients = NettyServer.getClients();
    private static final Map<String, ChannelFuture> channels = NettyServer.getChannels();

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        String address = ctx.channel().remoteAddress().toString();
        clients.put(ctx.channel().id().asShortText(), address);
        channels.put(ctx.channel().id().asShortText(), ctx.channel().closeFuture());
        System.out.println("Client connected : " + address);
    }



    /*
    Dans cet exemple, j'ai vérifié si le client a déjà entré un pseudo, si ce n'est pas le cas,
    on vérifie si lepseudo entré est déjà utilisé ou s'il est vide. Si tout est correct, on stocke
    le pseudo et on envoie un message de bienvenue au client. Sinon, on envoie un message pour lui demander
    d'entrer un pseudo valide. Si le client a déjà entré un pseudo, on utilise la méthode broadcast() pour diffuser
    le message reçu à tous les clients connectés en préfixant le message avec le pseudo du client qui l'a envoyé.

Il est important de noter que cet exemple est très basique et il y a beaucoup de choses à prendre en compte pour une
utilisation efficace de Netty. Il est préférable de consulter la documentation officielle pour une meilleure compréhension
des différentes options et fonctionnalités disponibles. Et il est important de sécuriser les communications pour éviter les
attaques en utilisant par exemple des protocoles de chiffrement.
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
                ctx.writeAndFlush("Bienvenue " + message + " !\n");
                broadcast(message + " s'est connecté !\n");
            }
        } else {
            broadcast(pseudo + " : " + message + "\n");
        }
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        String disconnectedClient = pseudos.get(ctx.channel().id().asShortText());
        pseudos.remove(ctx.channel().id().asShortText());
        clients.remove(ctx.channel().id().asShortText());
        channels.remove(ctx.channel().id().asShortText());
        System.out.println("Client disconnected : " + disconnectedClient);
        broadcast(disconnectedClient + " s'est déconnecté !\n");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    private void broadcast(String message) {
        channels.forEach((id, channel) -> channel.channel().writeAndFlush(message));
    }
}

