package com.example.projcomp380;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ThirdWindowController {

    // input from window 2
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

    // scenebuilder stuff
    @FXML
    private TextField addressTextField;

    @FXML
    private TextField cityTextField;

    @FXML
    private TextField zipTextField;

    @FXML
    private TextField countryTextField;

    @FXML
    private TextField countyTextField;

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


    public void getReservationDetails(LocalDate checkInDate, LocalDate checkOutDate, int numGuests, String roomType, double price, String roomDescription, Image image) {

        // Convert dates to strings
        String checkInDateStr = checkInDate.toString();
        String checkOutDateStr = checkOutDate.toString();

        // Calculate total price
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

        // Set the price labels with just the numeric values
        priceLabel.setText(String.valueOf(price)); // Display the price per night
        totalPriceLabel.setText(String.valueOf(totalPrice)); // Display the total price

    }

}