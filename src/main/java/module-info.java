module ProjetJavaLpDevops {


    /*
    Depuis Java 9 on et l'organisation d'un module
    on défine une fichier module-info.java
     */
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;



    exports com.devops.loupgarou.client to javafx.graphics;
    exports com.devops.loupgarou.server to javafx.graphics;

}