package com.example.demoapi;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        SceneManager.setStage(primaryStage);
        SceneManager.switchScene("/fxml/main_scene.fxml");


        primaryStage.setTitle("WORLD TIME");


        Image icon = new Image(Main.class.getResourceAsStream("/icons/earth.png"));
        primaryStage.getIcons().add(icon);


        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
