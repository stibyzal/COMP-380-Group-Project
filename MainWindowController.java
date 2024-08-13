package com.example.demo6;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.List;

import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import static javafx.scene.control.Alert.AlertType.*;
import static javafx.scene.control.Alert.AlertType.INFORMATION;


public class MainWindowController {


    private static final String FILE_NAME = "reservation.txt";

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

    //------------------------------------------------------------------------
    @FXML
    private Button cancelResButton;
    //------------------------------------------------------------------------

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

    @FXML //  method search parameters
    protected void searchRoomAvailability(ActionEvent actionEvent) throws Exception {

        // get input values from customer
        LocalDate checkInDate = checkInDatePicker.getValue();
        LocalDate checkOutDate = checkOutDatePicker.getValue();
        String numOfGuestsText = numOfGuestsField.getText();

        // check if dates are entered
        if (checkInDate == null || checkOutDate == null) {
            displayErrorBox(ERROR, "Date Error", "Please select check-in and check-out dates.");
            return;
        }

        // check if number of guests is empty
        if (numOfGuestsText == null || numOfGuestsText.trim().isEmpty()) {
            displayErrorBox(ERROR, "Guest Error", "Please enter the number of guests.");
            return;
        }

        // check if number of guests input is a number
        if (!numOfGuestsText.matches("\\d+")) {
            displayErrorBox(ERROR, "Guest Error", "Please enter a valid number of guests.");
            return;
        }

        // string to integer
        int numGuests = Integer.parseInt(numOfGuestsText);

        if (checkInDate.isAfter(checkOutDate)) {
            displayErrorBox(ERROR, "Date Error", "Check-in date cannot be after check-out date.");
            return;
        }

        // create new reservation
        Reservation newReservation = new Reservation(
                checkInDate.toString(),
                checkOutDate.toString(),
                (int) java.time.temporal.ChronoUnit.DAYS.between(checkInDate, checkOutDate),
                numGuests
        );

        // read existing reservations from file
        List<Reservation> reservations = Reservation.readReservationsFromFile(FILE_NAME);

        // check availability
        boolean isAvailable = newReservation.searchAvailability(reservations);

        if (isAvailable) {
            displayErrorBox(INFORMATION, "Reservation Successful", "The room is available for the selected dates.");
        } else {
            displayErrorBox(ERROR, "No Rooms Available", "No rooms are available for the selected dates.");
            return;
        }

        // loads new scene/second window with available rooms
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("second-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        SecondWindowController secondWindowController = fxmlLoader.getController();

        // pass and display date input to second window
        secondWindowController.displayDates(checkInDate, checkOutDate, numGuests);

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    // loads new scene/window, to search existing reservation
    @FXML
    protected void goSearchReservation(ActionEvent actionEvent) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("search-reservation-window.fxml"));
        Parent root = fxmlLoader.load();
        Stage newStage = new Stage();
        newStage.setTitle("Hotel California");
        newStage.setScene(new Scene(root));
        newStage.show();
        // closes main window when button is clicked
        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        currentStage.close();


        // to do
        // read file - alert window for no rooms
        // read file - new scene for available rooms

        // if room is available -> next window
        // if room is NOT available -> another alert box

    }


    // action button loads new scene to search and modify for an existing reservation
    @FXML
    protected void modifyReservation(ActionEvent actionEvent) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("search-to-modify-window.fxml"));
        Parent root = fxmlLoader.load();
        Stage newStage = new Stage();
        newStage.setTitle("Hotel California");
        newStage.setScene(new Scene(root));
        newStage.show();

        // closes main window when button is clicked
        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        currentStage.close();

    }
    //--------------------------------------------------------------------------------------------------
    @FXML
    protected void cancelReservation(ActionEvent actionEvent) throws Exception {
        Stage stage = new Stage();
        stage.setTitle("Hotel California");
        Parent root;
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        if (actionEvent.getSource() == cancelResButton){
            root = FXMLLoader.load(getClass().getResource("cancel-window.fxml"));
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(cancelResButton.getScene().getWindow());
            stage.showAndWait();
        }
    }


    //---------------------------------------------------------------------------------------------
    //Manager Login Section
    @FXML
    protected void managerLogin(ActionEvent actionEvent) throws Exception {
        // Prompt for User ID
        TextInputDialog userIDDialog = new TextInputDialog();
        userIDDialog.setTitle("Manager Login");
        userIDDialog.setHeaderText("Please enter your User ID:");
        userIDDialog.setContentText("User ID:");

        String userID = userIDDialog.showAndWait().orElse("");

        // Check if User ID is empty
        if (userID.isEmpty()) {
            displayErrorBox(Alert.AlertType.ERROR, "Login Failed", "User ID is required.");
            return;
        }

        // Check if User ID is correct
        if (!"admin".equals(userID)) {
            displayErrorBox(Alert.AlertType.ERROR, "Login Failed", "Invalid User ID.");
            return;
        }

        // Prompts user to input password
        TextInputDialog passwordDialog = new TextInputDialog();
        passwordDialog.setTitle("Manager Login");
        passwordDialog.setHeaderText("Please enter your Password:");
        passwordDialog.setContentText("Password:");

        String password = passwordDialog.showAndWait().orElse("");

        // Check if Password is empty
        if (password.isEmpty()) {
            displayErrorBox(Alert.AlertType.ERROR, "Login Failed", "Password is required.");
            return;
        }

        // Check if Password is correct
        if ("password1234".equals(password)) {
            displayErrorBox(Alert.AlertType.INFORMATION, "Login Successful", "Welcome, admin!");

            // Load the Manager Window after successful login
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("manager-window.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } else {
            displayErrorBox(Alert.AlertType.ERROR, "Login Failed", "Invalid Password.");
        }
    }
}