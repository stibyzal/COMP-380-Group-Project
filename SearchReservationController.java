package com.example.projcomp380;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SearchReservationController {



    @FXML
    private Label groupTitle; // Hotel Name

    @FXML
    private Label reservationNumLabel; // Reservation Number Label

    @FXML
    private TextField reservationNumInput; // Reservation Number Input

    @FXML
    private Button reservationSearchBtn; // Search Button

    // Method to search reservations based on the reservation number
    @FXML
    protected void searchReservation() {
        String reservationNum = reservationNumInput.getText().trim();

        if (reservationNum.isEmpty()) {
            showAlert(AlertType.ERROR, "Input Error", "Please enter a reservation number.");
            return;
        }

        // NOT DONE YET, NEED TO ADD PAYMENT SUMMARY, ROOM TYPE
        // Read reservations from file and check if the reservation exists
        List<Reservation> reservations = readReservationsFromFile("reservation.txt");
        boolean reservationFound = false;

        for (Reservation reservation : reservations) {
            if (reservation.getDateOfReservation().equals(reservationNum)) {
                reservationFound = true;
                showAlert(AlertType.INFORMATION, "Reservation Found",
                        "Reservation Details:\n" +
                                "Check-In Date: " + reservation.getCheckInDate() + "\n" +
                                "Check-Out Date: " + reservation.getCheckOutDate() + "\n" +
                                "Number of Guests: " + reservation.getNumGuests());
                break;
            }
        }

        if (!reservationFound) {
            showAlert(AlertType.ERROR, "No Reservation Found", "No reservation found with the provided number.");
        }
    }

    // Method to read reservations from a file
    private List<Reservation> readReservationsFromFile(String fileName) {
        List<Reservation> reservations = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String date = parts[0].trim();
                    LocalDate checkInDate = LocalDate.parse(date);
                    LocalDate checkOutDate = LocalDate.parse(parts[1].trim());
                    int guests = Integer.parseInt(parts[2].trim());
                    reservations.add(new Reservation(checkInDate, checkOutDate, guests));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return reservations;
    }

    // Method to show alert boxes
    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


