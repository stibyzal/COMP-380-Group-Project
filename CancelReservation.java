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
        processCancellation(reservationNum);
    }

    //setter method
    public void setReservationNum(String reservationNum){
        this.reservationNum = reservationNum;
    }

    //Getting refund amount
    protected String getRefundAmount(String key) {
        String inputFile = "payments.txt"; // Path to payment.txt file

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.contains(key)) {
                    String[] parts = line.split(",");
                    return parts[parts.length - 1].trim();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    // Removing reservation info
    protected void processCancellation(String key){
        String inputFile = "reservations.txt"; // Path to reservation.txt file

        try {
            //Read all lines from the file in a list
            List<String> lines = Files.readAllLines(Paths.get(inputFile));

            //Fiter out lines that contains the key
            List<String> filteredLines = new ArrayList<>();
            for(String line : lines){
                if(!line.contains(key)){
                    filteredLines.add(line);
                }
            }
            Files.write(Paths.get(inputFile), filteredLines);
            processRefund(key);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    //processing refund; deleting payment info
    protected void processRefund(String key){
        String inputFile = "payments.txt"; // Path to payment.txt file

        try {
            //Read all lines from the file in a list
            List<String> lines = Files.readAllLines(Paths.get(inputFile));

            //Fiter out lines that contains the key
            List<String> filteredLines = new ArrayList<>();
            for(String line : lines){
                if(!line.contains(key)){
                    filteredLines.add(line);
                }
            }
            Files.write(Paths.get(inputFile), filteredLines);
        }catch(IOException e){
            e.printStackTrace();
        }

        // Deleting address
        String address = "addresses.txt"; // Path to address.txt file
        try {
            //Read all lines from the file in a list
            List<String> lines = Files.readAllLines(Paths.get(address));

            //Fiter out lines that contains the key
            List<String> filteredLines = new ArrayList<>();
            for(String line : lines){
                if(!line.contains(key)){
                    filteredLines.add(line);
                }
            }
            Files.write(Paths.get(address), filteredLines);
        }catch(IOException e){
            e.printStackTrace();
        }


    }

}
