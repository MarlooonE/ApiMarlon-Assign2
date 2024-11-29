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

public class MainController {

    @FXML
    private TextField cityInput; // TextField for user input
    @FXML
    private ListView<String> timeListView; // ListView to display the fetched data

    private final String API_URL = "http://worldtimeapi.org/api/timezone"; // Base URL of the WorldTimeAPI

    @FXML
    public void fetchTime() {
        // Retrieve user input and trim any extra spaces
        String input = cityInput.getText().trim();
        if (input.isEmpty()) {
            showAlert("Input Error", "Please enter a valid timezone (e.g., America/Toronto).");
            return;
        }

        // Construct the endpoint URL using the input
        String endpoint = API_URL + "/" + input;

        System.out.println("Request URL: " + endpoint); // Debugging output

        try {
            // Set up the HTTP connection
            URL url = new URL(endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Process the API response
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                // Read the response from the API
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Parse the JSON response
                JsonObject responseJson = JsonParser.parseString(response.toString()).getAsJsonObject();
                displayTimeDetails(responseJson);

            } else if (responseCode == 404) {
                // Handle not found errors (e.g., invalid timezone)
                showAlert("API Error", "The specified timezone was not found. Please check your input.");
            } else {
                // Handle other response codes
                showAlert("API Error", "Failed to fetch time. Response Code: " + responseCode);
            }
        } catch (Exception e) {
            // Catch and handle any unexpected exceptions
            showAlert("Error", "An unexpected error occurred: " + e.getMessage());
        }
    }

    /**
     * Parses and displays time details from the JSON response.
     *
     * @param responseJson The JSON object containing the API response.
     */
    private void displayTimeDetails(JsonObject responseJson) {
        // Extract relevant details from the JSON response
        String timezone = responseJson.get("timezone").getAsString();
        String utcOffset = responseJson.get("utc_offset").getAsString();
        String datetime = responseJson.get("datetime").getAsString();
        int dayOfWeek = responseJson.get("day_of_week").getAsInt();
        int dayOfYear = responseJson.get("day_of_year").getAsInt();
        int weekNumber = responseJson.get("week_number").getAsInt();
        boolean isDST = responseJson.get("dst").getAsBoolean();
        String abbreviation = responseJson.get("abbreviation").getAsString();

        // Clear the ListView and add new data
        timeListView.getItems().clear();
        timeListView.getItems().add("Timezone: " + timezone);
        timeListView.getItems().add("UTC Offset: " + utcOffset);
        timeListView.getItems().add("Datetime: " + datetime);
        timeListView.getItems().add("Day of Week: " + dayOfWeek);
        timeListView.getItems().add("Day of Year: " + dayOfYear);
        timeListView.getItems().add("Week Number: " + weekNumber);
        timeListView.getItems().add("DST: " + (isDST ? "Yes" : "No"));
        timeListView.getItems().add("Abbreviation: " + abbreviation);
    }

    /**
     * Shows an alert dialog with the specified title and content.
     *
     * @param title   The title of the alert.
     * @param content The content/message of the alert.
     */
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
