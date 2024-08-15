package com.example.projcomp380;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;

/**
 * Cancel Reservation
 * Aug 8, 2024
 * @author B. Hosseini, B. Ascorra
 * A way to cancel a reservation by deleting all the reservation details and user information from our database (text files).
 */
public class CancelReservation {

    /**
     * The reservation number.
     */
    private String reservationNum;

    /**
     * The refund amount for canceling the reservation.
     */
    private String refundAmount;

    /**
     * Method to starting the process of canceling a reservation.
     * @param reservationNum the reservation number
     */
    public CancelReservation(String reservationNum){
        setReservationNum(reservationNum);
        this.refundAmount = getRefundAmount(reservationNum);
        processCancellation(reservationNum);
    }

    /**
     * Method to set the reservation number based on the user's input.
     * @param reservationNum the reservation number
     */
    //setter method
    public void setReservationNum(String reservationNum){
        this.reservationNum = reservationNum;
    }

    //returning refund amount

    /**
     * Method to return the refund amount for canceling a reservation.
     * @return the refund amount.
     */
    public String refundAmountGetter(){
        return refundAmount;
    }

    //Getting refund amount from database
    /**
     * Method to getting the refund amount from the database.
     * @param key the reservation number
     * @return the refund amount.
     */
    protected String getRefundAmount(String key) {
        String inputFile = "payments.txt"; // Path to payment.txt file
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(key)) {
                    String[] parts = line.split(",");
                    return parts[parts.length - 1].trim();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

        // Processing cancellation

    /**
     * Method for deleting all the reservation and user data after canceling the reservation.
     * @param key the reservation number
     */
        protected void processCancellation(String key){
            String reservationFile = "reservations.txt"; // Path to reservation.txt file
            String paymentFile = "payments.txt"; // Path to payment.txt file
            String addressFile = "addresses.txt"; // Path to address.txt file
            String roomFile = "roomChoices.txt"; //Path to roomChoice.txt file
            String customerFile = "customers.txt"; //Path to customers.txt file

            // Deleting reservation information
            removeReservationInfo(reservationFile, key);
            removeReservationInfo(paymentFile, key);
            removeReservationInfo(addressFile, key);
            removeReservationInfo(roomFile, key);
            removeReservationInfo(customerFile, key);
        }

        // This method is used for deleting information from databases

    /**
     * Method that goes through a file and deletes data based on a key.
     * @param filePath the database (text file)
     * @param key the reservation number
     */
        protected void removeReservationInfo(String filePath, String key) {
            try {
                // Read all lines from the file
                List<String> lines = Files.readAllLines(Paths.get(filePath));

                // Filter out lines that contain the key
                List<String> filteredLines = new ArrayList<>();
                for (String line : lines) {
                    if (!line.contains(key)) {
                        filteredLines.add(line);
                    }
                }

                // Write the filtered lines back to the file
                Files.write(Paths.get(filePath), filteredLines);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
