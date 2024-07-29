package com.example.hotelcalifornia;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SecondWindowController {

    @FXML
    private void switchToMainWindow(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main-window.fxml"));
            Parent mainRoot = loader.load();

            // Get the current stage from the event
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the main scene to the current stage
            currentStage.setScene(new Scene(mainRoot));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
