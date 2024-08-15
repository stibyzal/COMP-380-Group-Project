package com.example.projcomp380;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import java.time.LocalDate;

/**
 * Fourth Reservation Controller
 * Aug 3, 2024
 * @author K. Dela Merced, B. Ascorra
 * The fourth window that shows the user's reservation has been confirmed.
 * It shows their details as a customer, their booking details, and their reservation number.
 */
public class FourthWindowController {
    /**
     * Text on the window to show the user's name.
     */
    @FXML
    private Text nameText;

    /**
     * Text on the window to show the user's email.
     */
    @FXML
    private Text emailText;

    /**
     * Text on the window to show the date the user made the reservation.
     */
    @FXML
    private Text dateText;

    /**
     * Text on the window to show the user's reservation number.
     */
    @FXML
    private Text reservationNumText;

    /**
     * Text on the window to show the user's check in date.
     */
    @FXML
    private Text cidText;

    /**
     * Text on the window to show the user's check out date.
     */
    @FXML
    private Text codText;

    /**
     * Text on the window to show the total paid for the reservation.
     */
    @FXML
    private Text paidText;

    /**
     * Text on the window to show the reservation number but bigger.
     */
    @FXML
    private Text reservationNumberTextBig;



    // method display confirmation details
    /**
     * Method that shows all the confirmation details for the reservation confirmation.
     * @param customer the customer information
     * @param payment the payment information
     * @param address the address information
     * @param reservation the reservation information
     * @param reservationNumber the reservation number
     * @param totalPaid the total paid for the reservation
     */
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
