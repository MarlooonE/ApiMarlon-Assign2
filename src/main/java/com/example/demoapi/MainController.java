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
    private TextField cityInput; // User input field

    @FXML
    private ListView<String> timeListView; // ListView to display data

    private final String API_URL = "http://worldtimeapi.org/api/timezone"; // Base URL of the API

    @FXML
    public void fetchTime() {
        String input = cityInput.getText().trim(); // Get user input
        if (input.isEmpty()) {
            showAlert("Input Error", "Please enter a valid timezone or city name (e.g., America/Toronto).");
            return;
        }

        try {
            // Encode the input to handle spaces and special characters
            String encodedInput = URLEncoder.encode(input, "UTF-8");
            String endpoint = API_URL + "/" + encodedInput;

            System.out.println("Request URL: " + endpoint); // Print the URL for debugging

            // Configure HTTP connection
            URL url = new URL(endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                // Read the API response
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Process the JSON response
                JsonObject responseJson = JsonParser.parseString(response.toString()).getAsJsonObject();
                displayTimeDetails(responseJson);
            } else if (responseCode == 404) {
                showAlert("API Error", "The specified timezone or city was not found. Please check your input.");
            } else {
                showAlert("API Error", "Failed to fetch time. Response Code: " + responseCode);
            }
        } catch (Exception e) {
            showAlert("Error", "An unexpected error occurred: " + e.getMessage());
        }
    }

    /**
     * Displays relevant time details in the ListView.
     */
    private void displayTimeDetails(JsonObject responseJson) {
        // Extract relevant fields from JSON
        String datetime = responseJson.get("datetime").getAsString();
        String time = datetime.substring(11, 19); // Extract HH:mm:ss
        String utcOffset = responseJson.get("utc_offset").getAsString();
        String timezone = responseJson.get("timezone").getAsString();
        int dayOfWeek = responseJson.get("day_of_week").getAsInt();
        int dayOfYear = responseJson.get("day_of_year").getAsInt();
        String abbreviation = responseJson.get("abbreviation").getAsString();
        boolean isDST = responseJson.get("dst").getAsBoolean();

        // Clear the view and add new data
        timeListView.getItems().clear();
        timeListView.getItems().add("Current Time: " + time);
        timeListView.getItems().add("UTC Offset: " + utcOffset);
        timeListView.getItems().add("Timezone: " + timezone);
        timeListView.getItems().add("Day of the Week: " + dayOfWeek);
        timeListView.getItems().add("Day of the Year: " + dayOfYear);
        timeListView.getItems().add("Abbreviation: " + abbreviation);
        timeListView.getItems().add("DST: " + (isDST ? "Yes" : "No"));
    }

    /**
     * Displays an error alert with the error message.
     */
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
