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

/**
 * Third Window Controller
 * Aug 8, 2024
 * @author K. Dela Merced, B. Ascorra
 * The third window that shows the user the details of their booking.
 * The user inputs their payment method details on this window.
 */
public class ThirdWindowController {
    /**
     * Image of the room that was selected by user.
     */
    @FXML
    private ImageView roomImageView;

    /**
     * Label on the window to show the user's check in date.
     */
    @FXML
    private Label checkInDateLabel;

    /**
     * Label on the window to show the user's check out date.
     */
    @FXML
    private Label checkOutDateLabel;

    /**
     * Label on the window to show the number of guests for the reservation.
     */
    @FXML
    private Label guestsLabel;

    /**
     * Label on the window to show the user's type of room selected.
     */
    @FXML
    private Label roomTypeLabel;

    /**
     * Label on the window to show the description of the room selected.
     */
    @FXML
    private Label roomDescriptionLabel;

    /**
     * Label on the window to show the price per night for reserving the selected.
     */
    @FXML
    private Label priceLabel;

    /**
     * Label on the window to show the total price of the reservation.
     */
    @FXML
    private Label totalPriceLabel;

    /**
     * Text field in the window where the user inputs their address.
     */
    @FXML
    private TextField addressTextField;

    /**
     * Text field in the window where the user inputs their city.
     */
    @FXML
    private TextField cityTextField;

    /**
     * Text field in the window where the user inputs their zipcode.
     */
    @FXML
    private TextField zipTextField;

    /**
     * Text field in the window where the user inputs their country.
     */
    @FXML
    private TextField countryTextField;

    /**
     * Text field in the window where the user inputs their state.
     */
    @FXML
    private TextField stateTextField;

    /**
     * Text field in the window where the user inputs their email.
     */
    @FXML
    private TextField emailText;

    /**
     * Text field in the window where the user inputs their cvv.
     */
    @FXML
    private TextField cvvText;

    /**
     * Text field in the window where the user inputs their card expiration year.
     */
    @FXML
    private TextField expYearText;

    /**
     * Text field in the window where the user inputs their card expiration month.
     */
    @FXML
    private TextField expMonthText;

    /**
     * Text field in the window where the user inputs their card number.
     */
    @FXML
    private TextField cardNumText;

    /**
     * Text field in the window where the user inputs their first name.
     */
    @FXML
    private TextField firstNameText;

    /**
     * Text field in the window where the user inputs their last name.
     */
    @FXML
    private TextField lastNameText;

    /**
     * Text field in the window where the user inputs their phone number.
     */
    @FXML
    private TextField phoneNumText;

    /**
     * Button on the window that cancels the reservation process.
     */
    @FXML
    private Button cancelButton;

    /**
     * Button on the window that confirms the reservation.
     */
    @FXML
    private Button confirmButton;


    // attributes to store reservation details
    /**
     * Represents the user's check in date.
     */
    private LocalDate checkInDate;

    /**
     * Represents the user's check out date.
     */
    private LocalDate checkOutDate;

    /**
     * Represents the user's number of guests for the reservation.
     */
    private int numGuests;

    /**
     * Represents the user's room type that was selected.
     */
    private String roomType;

    /**
     * Represents the price per night based on the room type selected.
     */
    private double price;

    /**
     * Represents the total price of the entire reservation.
     */
    private double totalPrice;

    /**
     * Represents the description of the room based on the type selected.
     */
    private String roomDescription;

    /**
     * Represents the image of the room based on the type selected.
     */
    private Image roomImage;

    // method to get reservation customer input from second window

    /**
     * Gets the user's reservation details from the previous window.
     * It then displays the details on the current window.
     * @param checkInDate the check-in date
     * @param checkOutDate the check-out date
     * @param numGuests the number of guests
     * @param roomType the type of room
     * @param price the price per night
     * @param roomDescription the description of the room
     * @param image the image of the room
     */
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

    /**
     * Gets in all the user input from the text fields and stores them into their respective objects.
     * Those objects and all their data are then put into text files based on object type.
     */
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

    /**
     * Sends all the information stored from this window into the next window.
     * @param customer the customer information
     * @param payment the payment information
     * @param address the address information
     * @param reservation the reservation information
     * @param reservationNumber the reservation number
     * @param totalPaid the total cost of the reservation
     */
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
