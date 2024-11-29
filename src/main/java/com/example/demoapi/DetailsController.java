package com.example.demoapi;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DetailsController {
    @FXML
    private Label detailsContent;

    @FXML
    public void initialize() {
        TimeInfo timeInfo = SceneManager.getTimeInfo();
        if (timeInfo != null) {
            detailsContent.setText(timeInfo.toString());
        } else {
            detailsContent.setText("No details available.");
        }
    }
}
