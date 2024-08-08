package com.example.projcomp380;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ModifyReservationController {

    @FXML
    private Label welcomeLabel;
    @FXML
    private Label reservationNumLabel;
    @FXML
    private DatePicker checkInDatePicker;
    @FXML
    private DatePicker checkOutDatePicker;
    @FXML
    private TextField numGuestsField;
    @FXML
    private ComboBox<String> roomTypeComboBox; // room type choice drop down box
    @FXML
    private Label emailLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label totalPriceLabel;
    @FXML
    private Label amountOwedLabel;

    // calls for room types object from room choice class
    private RoomChoice roomChoice = new RoomChoice();
    private String reservationNum;
    private double originalTotalPrice;

    @FXML
    private TextField cardNumberField;
    @FXML
    private TextField expMonthField;
    @FXML
    private TextField expYearField;
    @FXML
    private TextField cvvField;

    @FXML
    private CheckBox newAddressCheckBox;
    @FXML
    private Label streetAddressLabel;
    @FXML
    private TextField streetAddressField;
    @FXML
    private Label cityLabel;
    @FXML
    private TextField cityField;
    @FXML
    private Label zipLabel;
    @FXML
    private TextField zipField;
    @FXML
    private Label stateLabel;
    @FXML
    private TextField stateField;
    @FXML
    private Label countryLabel;
    @FXML
    private TextField countryField;

    @FXML // methods calls javafx to load roomtype/toggles visibility when scene is loaded
    public void initialize() {
        roomTypeComboBox.getItems().addAll(roomChoice.getRoomTypesWithImages());
        toggleAddressFields(false); // Initialize address fields to be hidden
        togglePaymentFields(false); // Initialize payment fields to be hidden
    }

    // get reservation details and populate fields with current details
    public void getReservationDetails(String reservationDetails, String reservationNum, double totalPrice) {
        this.reservationNum = reservationNum;
        this.originalTotalPrice = totalPrice;
        reservationNumLabel.setText(reservationNum);
        totalPriceLabel.setText("Total Paid: $" + originalTotalPrice);

        String[] details = reservationDetails.split("\n");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // populate fields with details passed
        for (String detail : details) {
            String[] parts = detail.split(":");
            if (parts.length < 2) continue;

            String key = parts[0].trim();
            String value = parts[1].trim();

            switch (key) {
                case "Name":
                    welcomeLabel.setText("Welcome Back, " + value);
                    break;
                case "Check-In":
                    try {
                        checkInDatePicker.setValue(LocalDate.parse(value, formatter));
                    } catch (DateTimeParseException e) {
                        checkInDatePicker.setValue(null);
                    }
                    break;
                case "Check-Out":
                    try {
                        checkOutDatePicker.setValue(LocalDate.parse(value, formatter));
                    } catch (DateTimeParseException e) {
                        checkOutDatePicker.setValue(null);
                    }
                    break;
                case "Length of Stay":
                    break;
                case "Guests":
                    numGuestsField.setText(value);
                    break;
                case "Room Choice":
                    roomTypeComboBox.setValue(value);
                    break;
                case "Email":
                    emailLabel.setText("Email: " + value);
                    break;
                case "Phone":
                    phoneLabel.setText("Phone: " + value);
                    break;
                default:
                    break;
            }
        }
    }

    // check room availability after action
    @FXML
    protected void checkAvailability() {
        LocalDate checkInDate = checkInDatePicker.getValue();
        LocalDate checkOutDate = checkOutDatePicker.getValue();
        String roomType = roomTypeComboBox.getValue();
        int numGuests;

        try {
            numGuests = Integer.parseInt(numGuestsField.getText());
        } catch (NumberFormatException e) {
            displayAlert("Input Error", "Please enter a valid number of guests.");
            return;
        }

        if (checkInDate == null || checkOutDate == null || roomType == null) {
            displayAlert("Input Error", "Please enter all required fields.");
            return;
        }

        Reservation newReservation = new Reservation(
                checkInDate.toString(),
                checkOutDate.toString(),
                (int) java.time.temporal.ChronoUnit.DAYS.between(checkInDate, checkOutDate),
                numGuests
        );

        List<Reservation> reservations = Reservation.readReservationsFromFile("reservations.txt");

        if (newReservation.searchAvailability(reservations)) {
            displayAlert("Availability", "The room is available for the selected dates.");

            // calls method from payment class, calculates during availability check
            double newTotalPrice = Payment.calculateNewTotalPrice(checkInDate, checkOutDate, roomType, roomChoice);
            double amountOwed = newTotalPrice - originalTotalPrice;

            amountOwedLabel.setText("You Owe: $" + amountOwed);

            togglePaymentFields(true);
        } else {
            displayAlert("Availability", "The room is not available for the selected dates.");
            amountOwedLabel.setText("You Owe: $0.00");
            togglePaymentFields(false);
        }
    }

    @FXML // action toggle, if address is new
    protected void toggleNewAddressField() {
        toggleAddressFields(newAddressCheckBox.isSelected());
    }

    @FXML // action save button, saves new details
    protected void saveNewDetails() {
        String checkInDate = checkInDatePicker.getValue().toString();
        String checkOutDate = checkOutDatePicker.getValue().toString();
        String numGuests = numGuestsField.getText();
        String roomType = roomTypeComboBox.getValue();

        // new payment details
        String cardNumber = cardNumberField.getText().trim();
        String expMonth = expMonthField.getText().trim();
        String expYear = expYearField.getText().trim();
        String cvv = cvvField.getText().trim();

        if (cardNumber.isEmpty() || expMonth.isEmpty() || expYear.isEmpty() || cvv.isEmpty()) {
            displayAlert("Payment Error", "Please enter all payment details.");
            return;
        }

        // calculates what will be added into text file
        double newTotalPrice = Payment.calculateNewTotalPrice(LocalDate.parse(checkInDate), LocalDate.parse(checkOutDate), roomType, roomChoice);
        double amountOwed = newTotalPrice - originalTotalPrice;
        double totalPricePaid = originalTotalPrice + amountOwed;

        // if box is checked, gets new address from field
        if (newAddressCheckBox.isSelected()) {
            String streetAddress = streetAddressField.getText().trim();
            String city = cityField.getText().trim();
            String zip = zipField.getText().trim();
            String state = stateField.getText().trim();
            String country = countryField.getText().trim();

            if (streetAddress.isEmpty() || city.isEmpty() || zip.isEmpty() || state.isEmpty() || country.isEmpty()) {
                displayAlert("Address Error", "Please enter all address details.");
                return;
            }

            UpdateReservation.updateAddressFile(reservationNum, streetAddress, city, zip, state, country);
        }

        // update reservation details
        UpdateReservation.updateReservationFile(reservationNum, checkInDate, checkOutDate, numGuests);

        // update room choice
        UpdateReservation.updateRoomChoiceFile(reservationNum, roomType);

        // update payment details
        UpdateReservation.updatePaymentFile(reservationNum, cardNumber, expMonth, expYear, cvv, totalPricePaid);

        displayAlert("HOTEL CALIFORNIA", "Reservation details updated successfully!");
    }

    // alert dialog box display
    private void displayAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // to display address fields if box is checked/true
    private void toggleAddressFields(boolean show) {
        streetAddressLabel.setVisible(show);
        streetAddressField.setVisible(show);
        cityLabel.setVisible(show);
        cityField.setVisible(show);
        zipLabel.setVisible(show);
        zipField.setVisible(show);
        stateLabel.setVisible(show);
        stateField.setVisible(show);
        countryLabel.setVisible(show);
        countryField.setVisible(show);
    }

    // to display payment fields when action button clicked
    private void togglePaymentFields(boolean show) {
        cardNumberField.setVisible(show);
        expMonthField.setVisible(show);
        expYearField.setVisible(show);
        cvvField.setVisible(show);
    }
}