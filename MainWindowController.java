package com.example.demo6;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class MainWindowController {

    @FXML
    private DatePicker checkInDatePicker;

    @FXML
    private DatePicker checkOutDatePicker;

    @FXML
    private TextField numOfGuestsField;

    @FXML
    private Button buttonSearch;

    private static final String FILE_NAME = "reservations.txt";

    // Method to check for error, displays alert box
    private void displayErrorBox(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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

        // Create a new Reservation object with the data from the form
        Reservation newReservation = new Reservation();
        newReservation.setDateOfReservation(checkInDate.toString());
        newReservation.setLengthOfStay((int) checkInDate.until(checkOutDate).getDays());
        newReservation.setNumGuests(Integer.parseInt(numOfGuestsText));

        // Read existing reservations from file
        List<Reservation> reservations = Reservation.readReservationsFromFile(FILE_NAME);

        // Check availability
        boolean isAvailable = checkAvailability(newReservation, reservations);

        if (isAvailable) {
            displayErrorBox(AlertType.INFORMATION, "Availability", "The room is available for the selected dates.");
            newReservation.writeReservationToFile(FILE_NAME);
            // Switch to the second window only if available
            switchToSecondWindow();
        } else {
            displayErrorBox(AlertType.INFORMATION, "Availability", "The room is not available for the selected dates.");
            // Stay on the first window for re-input
        }
    }

    // Method to check room availability with a maximum of 5 rooms per check-in date
    private boolean checkAvailability(Reservation newReservation, List<Reservation> reservations) {
        LocalDate checkInDate = LocalDate.parse(newReservation.getDateOfReservation());

        // Count number of reservations for the check-in date
        long count = reservations.stream()
                .filter(r -> LocalDate.parse(r.getDateOfReservation()).equals(checkInDate))
                .count();

        // Check if the number of reservations exceeds the room limit
        return count < 5;
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
