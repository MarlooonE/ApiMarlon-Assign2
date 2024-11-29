package com.example.demoapi;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {
    private static Stage stage;

    public static void setStage(Stage primaryStage) {
        stage = primaryStage;
    }

    public static void switchScene(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxml));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
