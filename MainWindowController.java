package com.example.hotelcalifornia;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class MainWindowController {

    @FXML
    private DatePicker checkInDatePicker;

    @FXML
    private DatePicker checkOutDatePicker;

    @FXML
    private TextField numOfGuestsField;

    @FXML
    private Button buttonSearch;

    // Method to check for error, displays alert box
    private void displayErrorBox(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    protected void getCheckInDate() {
        // Method implementation (if needed)
        System.out.println("Check-In Date: " + checkInDatePicker.getValue());
    }

    @FXML
    protected void getCheckOutDate() {
        // Method implementation (if needed)
        System.out.println("Check-Out Date: " + checkOutDatePicker.getValue());
    }

    @FXML
    protected void searchRoomAvailability() {
        LocalDate checkInDate = checkInDatePicker.getValue();
        LocalDate checkOutDate = checkOutDatePicker.getValue();
        String numOfGuestsText = numOfGuestsField.getText();

        if (checkInDate == null || checkOutDate == null) {
            displayErrorBox(AlertType.ERROR, "Date Error", "Please select check-in and check-out dates.");
            return;
        }

        if (numOfGuestsText == null || numOfGuestsText.trim().isEmpty()) {
            displayErrorBox(AlertType.ERROR, "Guest Error", "Please enter the number of guests.");
            return;
        }

        if (checkInDate.isAfter(checkOutDate)) {
            displayErrorBox(AlertType.ERROR, "Date Error", "Check-in date cannot be after check-out date.");
            return;
        }

        // Assuming reservation logic is implemented here

        // Switch to second window after handling reservation
        switchToSecondWindow();
    }

    @FXML
    private void switchToSecondWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("second-window.fxml"));
            Parent newRoot = loader.load();

            // Get the current stage
            Stage currentStage = (Stage) buttonSearch.getScene().getWindow();
            currentStage.setScene(new Scene(newRoot));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
