package com.example.demo6;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * The UpdateReservation class provides methods to update reservation details
 * in various text files, including reservation information, room choices,
 * payment details, and address information.
 */
public class UpdateReservation {

    /**
     * Updates a specific entry in a file based on the reservation number.
     * The method reads the content of the file, updates the matching entry,
     * and then writes the updated content back to the file.
     *
     * @param filename        The name of the file to be updated.
     * @param reservationNum  The reservation number to search for.
     * @param newData         The new data to replace the existing entry.
     */
    private static void updateFile(String filename, String reservationNum, String newData) {
        // store lines in array list
        List<String> fileContent = new ArrayList<>();
        // to check if data got updated
        boolean entryUpdated = false;

        // read file and update reservation details with matching reservation num
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // checks if line starts with the correct reservation num
                if (line.startsWith(reservationNum + ",")) {
                    fileContent.add(newData);
                    entryUpdated = true;
                } else {
                    // keeps existing line in the file
                    fileContent.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // write the updated content back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String line : fileContent) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the reservation details in the reservations file.
     * This method formats the new reservation details and calls the updateFile method to apply the changes.
     *
     * @param reservationNum  The reservation number to be updated.
     * @param checkInDate     The new check-in date.
     * @param checkOutDate    The new check-out date.
     * @param numGuests       The number of guests.
     */
    public static void updateReservationFile(String reservationNum, String checkInDate, String checkOutDate, String numGuests) {
        try {
            // parse dates and calculate length of stay
            LocalDate checkIn = LocalDate.parse(checkInDate);
            LocalDate checkOut = LocalDate.parse(checkOutDate);
            int lengthOfStay = (int) ChronoUnit.DAYS.between(checkIn, checkOut);

            String newData = String.format("%s,%s,%s,%d,%s", reservationNum, checkInDate, checkOutDate, lengthOfStay, numGuests);

            // update the file with the new details
            updateFile("reservations.txt", reservationNum, newData);
        } catch (DateTimeParseException e) {
            System.out.println("Error parsing date: " + e.getMessage());
        }
    }

    /**
     * Updates the room choice details in the roomChoices file.
     * This method formats the new room choice details and calls the updateFile method to apply the changes.
     *
     * @param reservationNum  The reservation number to be updated.
     * @param roomType        The new room type.
     */
    public static void updateRoomChoiceFile(String reservationNum, String roomType) {
        String newData = String.format("%s,%s", reservationNum, roomType);
        updateFile("roomChoices.txt", reservationNum, newData);
    }

    /**
     * Updates the payment details in the payments file.
     * This method formats the new payment details and calls the updateFile method to apply the changes.
     *
     * @param reservationNum  The reservation number to be updated.
     * @param cardNumber      The new card number.
     * @param expMonth        The new expiration month.
     * @param expYear         The new expiration year.
     * @param cvv             The new CVV code.
     * @param totalPrice      The new total price.
     */
    public static void updatePaymentFile(String reservationNum, String cardNumber, String expMonth, String expYear, String cvv, double totalPrice) {
        String newData = String.format("%s,%s,%s,%s,%s,%.2f", reservationNum, cardNumber, expMonth, expYear, cvv, totalPrice);
        updateFile("payments.txt", reservationNum, newData);
    }

    /**
     * Updates the address details in the addresses file.
     * This method formats the new address details and calls the updateFile method to apply the changes.
     *
     * @param reservationNum  The reservation number to be updated.
     * @param streetAddress   The new street address.
     * @param city            The new city.
     * @param zip             The new ZIP code.
     * @param state           The new state.
     * @param country         The new country.
     */
    public static void updateAddressFile(String reservationNum, String streetAddress, String city, String zip, String state, String country) {
        String newData = String.format("%s,%s,%s,%s,%s,%s", reservationNum, streetAddress, city, zip, state, country);
        updateFile("addresses.txt", reservationNum, newData);
    }
}
