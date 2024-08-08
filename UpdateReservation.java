package com.example.projcomp380;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class UpdateReservation {

    // method to update data on relevant text files
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
        }
        catch (IOException e) {
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

    // method formats new details and calls for the method to update files
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

    // for room choice text file
    public static void updateRoomChoiceFile(String reservationNum, String roomType) {
        String newData = String.format("%s,%s", reservationNum, roomType);
        updateFile("roomChoices.txt", reservationNum, newData);
    }

    // for payment details
    public static void updatePaymentFile(String reservationNum, String cardNumber, String expMonth, String expYear, String cvv, double totalPrice) {
        String newData = String.format("%s,%s,%s,%s,%s,%.2f", reservationNum, cardNumber, expMonth, expYear, cvv, totalPrice);
        updateFile("payments.txt", reservationNum, newData);
    }

    // for address details
    public static void updateAddressFile(String reservationNum, String streetAddress, String city, String zip, String state, String country) {
        String newData = String.format("%s,%s,%s,%s,%s,%s", reservationNum, streetAddress, city, zip, state, country);
        updateFile("addresses.txt", reservationNum, newData);
    }
}