package com.devops.loupgarou.server;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class app2 extends Application {
    //public static void main(String[] args) {
    // System.out.println("hello");
    // }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setWidth(800);
        stage.setHeight(600);
        stage.setTitle("fenetre package Sever");
        Group root = new Group();
        Scene scene = new Scene(root);
        scene.setFill(Color.BEIGE);
        Text text = new Text(10, 30, "test dans le package server");
        root.getChildren().add(text);

        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}
