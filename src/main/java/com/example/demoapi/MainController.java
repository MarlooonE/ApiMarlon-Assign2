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
    private TextField cityInput;
    @FXML
    private ListView<String> timeListView;

    private final String API_KEY = "o3DEjH43ju8GkPQOrvxojkrzrwSpXzDk2XKfliX0"; // Tu clave API
    private final String API_URL = "https://api.api-ninjas.com/v1/worldtime"; // URL base de la API

    @FXML
    public void fetchTime() {
        String city = cityInput.getText().trim(); // Obtiene el texto ingresado
        if (city.isEmpty()) {
            showAlert("Input Error", "Please enter a timezone or city name."); // Muestra un error si el campo está vacío
            return;
        }

        // Formatea la entrada para que coincida con el formato esperado por la API
        String formattedCity = "America/" + city.substring(0, 1).toUpperCase() + city.substring(1).toLowerCase();
        String endpoint = API_URL + "?timezone=" + formattedCity;

        System.out.println("Request URL: " + endpoint); // Depuración de la URL

        try {
            // Configura la conexión
            URL url = new URL(endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("X-Api-Key", API_KEY); // Agrega la API Key al encabezado
            connection.setRequestMethod("GET");

            // Obtiene el código de respuesta
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                // Lee la respuesta de la API
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Procesa el JSON de respuesta
                JsonObject responseJson = JsonParser.parseString(response.toString()).getAsJsonObject();
                String timezone = responseJson.get("timezone").getAsString();
                String datetime = responseJson.get("datetime").getAsString();
                String date = responseJson.get("date").getAsString();
                String year = responseJson.get("year").getAsString();
                String month = responseJson.get("month").getAsString();
                String day = responseJson.get("day").getAsString();
                String hour = responseJson.get("hour").getAsString();
                String minute = responseJson.get("minute").getAsString();
                String second = responseJson.get("second").getAsString();
                String dayOfWeek = responseJson.get("day_of_week").getAsString();

                // Limpia el ListView antes de agregar nuevos datos
                timeListView.getItems().clear();

                // Agrega la información al ListView
                timeListView.getItems().add("Timezone: " + timezone);
                timeListView.getItems().add("Datetime: " + datetime);
                timeListView.getItems().add("Date: " + date);
                timeListView.getItems().add("Year: " + year);
                timeListView.getItems().add("Month: " + month);
                timeListView.getItems().add("Day: " + day);
                timeListView.getItems().add("Hour: " + hour);
                timeListView.getItems().add("Minute: " + minute);
                timeListView.getItems().add("Second: " + second);
                timeListView.getItems().add("Day of Week: " + dayOfWeek);

            } else if (responseCode == 502) {
                showAlert("API Error", "The server encountered a problem (502 Bad Gateway). Please try again later.");
            } else {
                showAlert("API Error", "Failed to fetch time. Response Code: " + responseCode);
            }
        } catch (Exception e) {
            showAlert("Error", "An error occurred: " + e.getMessage());
        }
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
