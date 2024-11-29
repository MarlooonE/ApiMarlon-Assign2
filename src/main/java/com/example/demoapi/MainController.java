package com.example.demoapi;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainController {

    @FXML
    private TextField cityInput;
    @FXML
    private ListView<String> timeListView;

    private static final String API_URL = "http://worldtimeapi.org/api/timezone";

    @FXML
    public void fetchTime() {
        String input = cityInput.getText().trim();
        if (input.isEmpty()) {
            showAlert("Input Error", "Please enter a valid timezone or city name (e.g., America/Toronto).");
            return;
        }

        try {
            String encodedInput = URLEncoder.encode(input, "UTF-8");
            String endpoint = API_URL + "/" + encodedInput;

            URL url = new URL(endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JsonObject responseJson = JsonParser.parseString(response.toString()).getAsJsonObject();
                String datetime = responseJson.get("datetime").getAsString();
                String time = datetime.substring(11, 19);

                timeListView.getItems().clear();
                timeListView.getItems().add("Current Time: " + time);

                TimeInfo timeInfo = parseJsonToTimeInfo(responseJson);
                SceneManager.setTimeInfo(timeInfo);

            } else if (responseCode == 404) {
                showAlert("API Error", "The specified timezone or city was not found. Please check your input.");
            } else {
                showAlert("API Error", "Failed to fetch time. Response Code: " + responseCode);
            }
        } catch (Exception e) {
            showAlert("Error", "An unexpected error occurred: " + e.getMessage());
        }
    }
    @FXML
    public void refreshData() {
        fetchTime();
    }

    private TimeInfo parseJsonToTimeInfo(JsonObject json) {
        return new TimeInfo(
                json.get("timezone").getAsString(),
                json.get("utc_offset").getAsString(),
                json.get("datetime").getAsString(),
                json.get("day_of_week").getAsInt(),
                json.get("day_of_year").getAsInt(),
                json.get("week_number").getAsInt(),
                json.get("dst").getAsBoolean(),
                json.get("abbreviation").getAsString()
        );
    }


    @FXML
    public void goToDetails() {
        try {
            SceneManager.switchScene("/fxml/details_scene.fxml");
        } catch (Exception e) {
            showAlert("Navigation Error", "Failed to load details scene: " + e.getMessage());
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
