package com.example.projcomp380;

import com.sun.tools.javac.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import java.time.LocalDate;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class MainWindowController {

    @FXML
    private Label groupTitle; // Hotel Name

    @FXML
    private Label checkInLabel; // Check-In Label

    @FXML
    private Label checkOutLabel; // Check-Out Label

    @FXML
    private Label numOfGuestsLabel; // Number of Guests Label

    @FXML
    private Button buttonSearch; // Search Button

    @FXML
    private DatePicker checkInDatePicker; // Check-In Picker

    @FXML
    private DatePicker checkOutDatePicker; // Check-Out Picker

    @FXML
    private TextField numOfGuestsField; // Number of Guests TextField

    @FXML
    protected void getCheckInDate() {
        LocalDate selectedDate = checkInDatePicker.getValue();
    }

    @FXML
    protected void getCheckOutDate() {
        LocalDate selectedDate = checkOutDatePicker.getValue();

    }


    // method to check for error, displays alert box
    private void displayErrorBox(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();

    }

    @FXML  //  method search parameters
    protected void searchRoomAvailability(ActionEvent actionEvent) throws Exception {

        // get input values from customer
        LocalDate checkInDate = checkInDatePicker.getValue();
        LocalDate checkOutDate = checkOutDatePicker.getValue();
        String numOfGuestsText = numOfGuestsField.getText();

        // check if all dates are entered
        if (checkInDate == null || checkOutDate == null) {
            displayErrorBox(AlertType.ERROR, "Date Error", "Please select check-in and check-out dates.");
            return;
        }

        // check if correct input for guests
        if (numOfGuestsText == null || numOfGuestsText.trim().isEmpty()) {
            displayErrorBox(AlertType.ERROR, "Guest Error", "Please enter the number of guests.");
            return;
        }

        // check if dates entered are correct
        if (checkInDate.isAfter(checkOutDate)) {
            displayErrorBox(AlertType.ERROR, "Date Error", "Check-in date cannot be after check-out date.");
            return;
        }

        // placeholder, working on second window
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("second-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

        // to do
        // read file - alert window for no rooms
        // read file - new scene for available rooms

        // if room is available -> next window
        // if room is NOT available -> another alert box

    }
}