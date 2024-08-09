package com.example.projcomp380;

import java.io.IOException;
import java.util.Random;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ThirdWindowController {

    @FXML
    private ImageView roomImageView;
    @FXML
    private Label checkInDateLabel;
    @FXML
    private Label checkOutDateLabel;
    @FXML
    private Label guestsLabel;
    @FXML
    private Label roomTypeLabel;
    @FXML
    private Label roomDescriptionLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label totalPriceLabel;

    @FXML
    private TextField addressTextField;
    @FXML
    private TextField cityTextField;
    @FXML
    private TextField zipTextField;
    @FXML
    private TextField countryTextField;
    @FXML
    private TextField stateTextField;
    @FXML
    private TextField emailText;
    @FXML
    private TextField billAddText;
    @FXML
    private TextField cvvText;
    @FXML
    private TextField expYearText;
    @FXML
    private TextField expMonthText;
    @FXML
    private TextField cardNumText;
    @FXML
    private TextField firstNameText;
    @FXML
    private TextField lastNameText;
    @FXML
    private TextField phoneNumText;
    @FXML
    private Text nightsLabel;
    @FXML
    private Button cancelButton;
    @FXML
    private Button confirmButton;

    // attributes to store reservation details
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int numGuests;
    private String roomType;
    private double price;
    private double totalPrice;
    private String roomDescription;
    private Image roomImage;

    // method to get reservation customer input from second window
    public void getReservationDetails(LocalDate checkInDate, LocalDate checkOutDate, int numGuests, String roomType, double price, String roomDescription, Image image) {

        // store details in instance variable
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numGuests = numGuests;
        this.roomType = roomType;
        this.price = price;
        this.totalPrice = price * ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        this.roomDescription = roomDescription;
        this.roomImage = image;

        // convert dates to strings for gui
        String checkInDateStr = checkInDate.toString();
        String checkOutDateStr = checkOutDate.toString();

        // calculate total price
        long numNights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        double totalPrice = numNights * price;

        // display input from window 2
        checkInDateLabel.setText(checkInDateStr);
        checkOutDateLabel.setText(checkOutDateStr);
        guestsLabel.setText(String.valueOf(numGuests));
        roomTypeLabel.setText(roomType);
        roomDescriptionLabel.setText(roomDescription);

        // displays the image of the chosen room
        if (image != null) {
            roomImageView.setImage(image);
        } else {
            System.err.println("Image not found for room type: " + roomType);
        }


        priceLabel.setText(String.valueOf(price)); // display the price per night
        totalPriceLabel.setText(String.valueOf(totalPrice)); // display the total price

    }


    // method to handle reservation after being confirmed by customer
    @FXML
    private void confirmReservation() {
        // generate reservation num
        Random random = new Random();
        String reservationNumber = String.format("%05d", random.nextInt(100000));

        // get user input from gui textfield
        String firstName = firstNameText.getText();
        String lastName = lastNameText.getText();
        String email = emailText.getText();
        String phoneNumber = phoneNumText.getText();
        String cardNumber = cardNumText.getText();
        String expirationMonth = expMonthText.getText();
        String expirationYear = expYearText.getText();
        String cvvNumber = cvvText.getText();
        String streetName = addressTextField.getText();
        String city = cityTextField.getText();
        String state = stateTextField.getText();
        String country = countryTextField.getText();
        int zipCode = Integer.parseInt(zipTextField.getText());

        // creates objects for classes accordinly
        Address address = new Address(country, state, city, streetName, zipCode);
        Customer customer = new Customer(firstName, lastName, email, phoneNumber);
        Payment payment = new Payment(cardNumber, expirationMonth, expirationYear, cvvNumber, totalPrice);
        Reservation reservation = new Reservation(checkInDate.toString(), checkOutDate.toString(), (int) ChronoUnit.DAYS.between(checkInDate, checkOutDate), numGuests);
        RoomChoice roomChoice = new RoomChoice();
        roomChoice.setRoom(roomType);

        // calls method from other classes to save details into respective text files, with res. num
        reservation.saveReservationToFile("reservations.txt", reservationNumber);
        address.saveAddressToFile("addresses.txt", reservationNumber);
        payment.savePaymentToFile("payments.txt", reservationNumber);
        customer.saveCustomerToFile("customers.txt", reservationNumber);
        roomChoice.saveRoomChoiceToFile("roomChoices.txt", reservationNumber);


        double totalPaid = totalPrice;
        // passes details to next window
        showConfirmationWindow(customer, payment, address, reservation, reservationNumber, totalPaid);
    }

    // method to display next window with final summary of reservation
    private void showConfirmationWindow(Customer customer, Payment payment, Address address, Reservation reservation, String reservationNumber, double totalPaid) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fourth-window.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            FourthWindowController controller = loader.getController();

            // get final reservation details to next window
            controller.getConfirmation(customer, payment, address, reservation, reservationNumber, totalPaid);
            stage.show();

            // Get current stage and close it
            Stage currentStage = (Stage) confirmButton.getScene().getWindow();
            currentStage.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
