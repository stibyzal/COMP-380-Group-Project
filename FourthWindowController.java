package com.example.projcomp380;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.time.LocalDate;

public class FourthWindowController {
    @FXML
    private Text nameText;
    @FXML
    private Text emailText;
    @FXML
    private Text dateText;
    @FXML
    private Text reservationNumText;
    @FXML
    private Text cidText;
    @FXML
    private Text codText;
    @FXML
    private Text paidText;
    @FXML
    private Text reservationNumberTextBig;

    // method display confirmation details
    public void getConfirmation(Customer customer, Payment payment, Address address, Reservation reservation, String reservationNumber, double totalPaid) {
        nameText.setText(customer.getFirstName() + " " + customer.getLastName());
        emailText.setText(customer.getEmail());

        // display date of reservation
        LocalDate currentDate = LocalDate.now();
        dateText.setText(currentDate.toString());

        reservationNumText.setText(reservationNumber);
        cidText.setText(reservation.getCheckInDate());
        codText.setText(reservation.getCheckOutDate());
        paidText.setText(String.valueOf(totalPaid)); // Display the total payment amount
        reservationNumberTextBig.setText(reservationNumber);
    }
}