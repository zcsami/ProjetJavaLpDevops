module ProjetJavaLpDevops {


    /*
    Depuis Java 9 on et l'organisation d'un module
    on défine une fichier module-info.java
     */
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    //dépendances tests io.netty
    requires io.netty.all;
    requires io.netty.transport;
    requires io.netty.common;
    requires io.netty.codec;
    requires io.netty.buffer;


    exports com.devops.loupgarou.client to javafx.graphics;
    exports com.devops.loupgarou.server to javafx.graphics;


}