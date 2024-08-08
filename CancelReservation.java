
package com.example.projcomp380;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;

public class CancelReservation {
    private String reservationNum;
    private String refundAmount;


    public CancelReservation(String reservationNum){
        setReservationNum(reservationNum);
        this.refundAmount = getRefundAmount(reservationNum);
        System.out.println("refund amount: " + this.refundAmount);


    public CancelReservation(String reservationNum){
        setReservationNum(reservationNum);
        this.refundAmount = getRefundAmount(reservationNum);

        processCancellation(reservationNum);
    }

    //setter method
    public void setReservationNum(String reservationNum){
        this.reservationNum = reservationNum;
    }
      
    //returning refund amount
    public String refundAmountGetter(){
        return refundAmount;
    }

    //Getting refund amount from database
    protected String getRefundAmount(String key) {
        String inputFile = ""; // Path to payment.txt file


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
    protected void processCancellation(String key){
        String reservationFile = ""; // Path to reservation.txt file
        String paymentFile = ""; // Path to payment.txt file
        String addressFile = ""; // Path to address.txt file
        String roomFile = ""; //Path to roomChoice.txt file

        // Deleting reservation information
        removeLinesContainingKey(reservationFile, key);
        removeLinesContainingKey(paymentFile, key);
        removeLinesContainingKey(addressFile, key);
        removeLinesContainingKey(roomFile, key);


    }
    // This method is used for deleting information from databases
    private void removeLinesContainingKey(String filePath, String key) {
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
