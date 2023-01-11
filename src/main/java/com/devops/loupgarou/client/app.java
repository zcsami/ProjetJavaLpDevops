package com.devops.loupgarou.client;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class app extends Application {
    //public static void main(String[] args) {
       // System.out.println("hello");
   // }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setWidth(800);
        stage.setHeight(600);
        stage.setTitle("TEST package client");
        Group root = new Group();
        Scene scene = new Scene(root);
        scene.setFill(Color.BEIGE);
        Text text = new Text(10, 30, "Test package client");
        root.getChildren().add(text);

        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}
