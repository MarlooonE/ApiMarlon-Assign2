package com.example.demoapi;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {
    private static Stage stage;
    private static TimeInfo timeInfo;

    public static void setTimeInfo(TimeInfo info) {
        timeInfo = info;
    }

    public static TimeInfo getTimeInfo() {
        return timeInfo;
    }


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
