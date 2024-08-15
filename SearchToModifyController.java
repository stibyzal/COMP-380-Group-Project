package com.example.projcomp380;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * SearchToModifyController
 * It allows the user to search for an existing reservation using a reservation number.
 *
 * Algorithms:
 * - File Searching: Loops through multiple files to find matching reservation numbers and retrieves associated details.
 *
 *  * @author Kristina Dela Merced
 *  * @version 1.0
 */

public class SearchToModifyController {

    @FXML
    private Label groupTitle; // hotel Name

    @FXML
    private Label reservationNumLabel; // reservation Number Label

    @FXML
    private TextField reservationNumInput; // reservation num text field

    @FXML
    private Button reservationSearchBtn; // search button

    /**
     * Searches for the reservation details based on the reservation number entered by the user.
     * Searches through multiple files (e.g., customers, reservations, room choices, payments, addresses)
     * to find matching reservation numbers. Accumulates and stores the details associated with the reservation in StringBuilders.
     * If a matching reservation is found, it loads the modification window with the retrieved details.
     *
     * Algorithm:
     * this class contains a file search algorithm
     * a loop to search through multiple text files for a specific reservation number.
     * This involves: Iterating through files, reading lines and checking if the reservation number matches.
     * Accumulating details in the corresponding StringBuilder when a match is found.
     *
     * @throws IOException If an error occurs while reading the files.
     */


    // method to search reservations based on reservation num
    @FXML
    protected void searchReservation() {
        String reservationNum = reservationNumInput.getText().trim();

        // check if field is empty
        if (reservationNum.isEmpty()) {
            displayAlert("Input Error", "Please enter a reservation number.");
            return;
        }

        // store data into stringbuilders
        StringBuilder customerDetails = new StringBuilder();
        StringBuilder reservationDetails = new StringBuilder();
        StringBuilder roomChoiceDetails = new StringBuilder();
        StringBuilder paymentDetails = new StringBuilder();
        StringBuilder addressDetails = new StringBuilder();

        boolean reservationFound = false;
        double totalPrice = 0.0;

        try {
            // relevant iles to search
            String[] files = {"customers.txt", "reservations.txt", "roomChoices.txt", "payments.txt", "addresses.txt"};

            // loop through each file to search for res num
            for (String fileName : files) {
                BufferedReader reader = new BufferedReader(new FileReader(fileName));
                String line;
                while ((line = reader.readLine()) != null) {
                    // look for frist entry reservation num
                    if (line.startsWith(reservationNum + ",")) {
                        // when reservation num is found, adds to corresponding stringbuilder
                        switch (fileName) {
                            case "customers.txt":
                                customerDetails.append(formatLine(fileName, line)).append("\n");
                                break;
                            case "reservations.txt":
                                reservationDetails.append(formatLine(fileName, line)).append("\n");
                                break;
                            case "roomChoices.txt":
                                roomChoiceDetails.append(formatLine(fileName, line)).append("\n");
                                break;
                            case "payments.txt":
                                paymentDetails.append(formatLine(fileName, line)).append("\n");
                                totalPrice = Double.parseDouble(line.split(",")[5].trim());
                                break;
                            case "addresses.txt":
                                addressDetails.append(formatLine(fileName, line)).append("\n");
                                break;
                        }
                        reservationFound = true;
                    }
                }
                reader.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        // error handling
        if (!reservationFound) {
            displayAlert("Reservation Not Found", "No reservation found with the given reservation number.");

        } else {

            // combine all details in the specified order
            StringBuilder finalDetails = new StringBuilder();
            finalDetails.append(customerDetails);
            finalDetails.append(reservationDetails);
            finalDetails.append(roomChoiceDetails);
            finalDetails.append(paymentDetails);
            finalDetails.append(addressDetails);

            // pass the details to the new scene with the reservation number and total price
            openModifyReservationWindow(finalDetails.toString(), reservationNum, totalPrice);
        }
    }

    /**
     * Formats a line from the file based on the file type.
     * processes a line from one of the reservation related files and formats it for display.
     *
     * @param fileName The name of the file being processed.
     * @param line The line read from the file.
     * @return A formatted string containing the relevant details for the reservation.
     */

    // method to format line based on file type
    private String formatLine(String fileName, String line) {
        // split line using comma
        String[] parts = line.split(",");
        switch (fileName) {
            case "customers.txt":
                return String.format("Name: %s %s\nEmail: %s\nPhone: %s", parts[1], parts[2], parts[3], parts[4]);
            case "reservations.txt":
                return String.format("Check-In: %s\nCheck-Out: %s\nLength of Stay: %s\nGuests: %s", parts[1], parts[2], parts[3], parts[4]);
            case "roomChoices.txt":
                return String.format("Room Choice: %s", parts[1]);
            case "payments.txt":
                return String.format("Total Paid: $%s", parts[5]); // Display only the total amount paid
            case "addresses.txt":
                return String.format("Address: %s %s %s %s %s", parts[1], parts[2], parts[3], parts[4], parts[5]);
            default:
                return line;
        }
    }

    /**
     * Displays an alert message with the given title and content.
     * This method is used to inform the user about errors, such as missing input or failed searches
     *
     * @param title The title of the alert dialog.
     * @param message The content message to be displayed in the alert dialog.
     */

    // method display alert
    private void displayAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    /**
     * Loads the modification window with the retrieved reservation details.
     * is called when a reservation is found and successfully loaded.
     *
     * @param reservationDetails The formatted reservation details to be displayed in the modification window.
     * @param reservationNum The reservation number of the reservation being modified.
     * @param totalPrice The total price associated with the reservation.
     * @throws IOException If an error occurs while loading the modification window.
     */

    // method to open new window for modifying reservation
    private void openModifyReservationWindow(String reservationDetails, String reservationNum, double totalPrice) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projcomp380/modify-window.fxml"));
            Pane pane = loader.load();

            ModifyReservationController controller = loader.getController();
            // pass reservation details
            controller.getReservationDetails(reservationDetails, reservationNum, totalPrice);

            // Get current stage and close it
            Stage currentStage = (Stage) reservationSearchBtn.getScene().getWindow();
            currentStage.close();

            Stage stage = new Stage();
            stage.setTitle("HOTEL CALIFORNIA");
            Scene scene = new Scene(pane, 680, 600);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); // Block interaction with other windows
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
