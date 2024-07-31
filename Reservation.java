package com.example.projcomp380;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Reservation {
    private String checkInDate;
    private String checkOutDate;
    private int lengthOfStay;
    private String numGuests;

    // Default constructor
    public Reservation() {}

    public Reservation(String dateIn, String dateOut, String guests) {
        setCheckInDate(dateIn);
        setCheckOutDate(dateOut);
        setNumGuests(guests);
    }

    // Set methods
    public void setCheckInDate(String cid) {
        this.checkInDate = cid;
    }
    public void setCheckOutDate(String cod) {
        this.checkOutDate = cod;
    }
    public void setNumGuests(String numGuests) {
        this.numGuests = numGuests;
    }
    public void setLengthOfStay(int lengthOfStay) {
        this.lengthOfStay = lengthOfStay;
    }



    // Get methods
    public String getCheckInDate() {
        return this.checkInDate;
    }
    public String getCheckOutDate() {
        return this.checkOutDate;
    }
    public String getNumGuests() {
        return this.numGuests;
    }
    public int getLengthOfStay() {
        return this.lengthOfStay;
    }

    Scanner scanner = new Scanner(System.in);
    // Other methods
    public void getReservationInfo() {
        System.out.print("Enter your booking date ");
        this.checkInDate = scanner.nextLine();
        System.out.println();
        System.out.print("How many days are you willing to stay: ");
        this.lengthOfStay = scanner.nextInt();
        System.out.println();
        System.out.print("Enter number of guests: ");
        this.numGuests = scanner.nextLine();
        System.out.println();
    }

    // Method for searching the availability of room for the given date range
    public boolean searchAvailability(List<Reservation> reservations) {
        LocalDate checkInDate = LocalDate.parse(this.checkInDate);
        LocalDate checkOutDate = checkInDate.plusDays(this.lengthOfStay);

        for (Reservation reservation : reservations) {
            LocalDate existingCheckIn = LocalDate.parse(reservation.getCheckInDate());
            LocalDate existingCheckOut = existingCheckIn.plusDays(reservation.getLengthOfStay());

            if (checkInDate.isBefore(existingCheckOut) && checkOutDate.isAfter(existingCheckIn)) {
                return false; // Overlapping reservation found
            }
        }

        return true; // No overlapping reservation found
    }

    // Method to read reservations from a file
    public static List<Reservation> readReservationsFromFile(String fileName) {
        List<Reservation> reservations = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String date = parts[0].trim();
                    int length = Integer.parseInt(parts[1].trim());
                    int guests = Integer.parseInt(parts[2].trim());
                    //reservations.add(new Reservation(date, length, guests));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return reservations;
    }

    // Method to write a reservation to a file
    public void writeReservationToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(this.checkInDate + "," + this.lengthOfStay + "," + this.numGuests);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String fileName = "reservations.txt";

        // Read existing reservations from file
        List<Reservation> reservations = readReservationsFromFile(fileName);

        Reservation newReservation = new Reservation();
        newReservation.getReservationInfo();

        boolean isAvailable = newReservation.searchAvailability(reservations);

        if (isAvailable) {
            System.out.println("The room is available for the selected dates.");
            newReservation.writeReservationToFile(fileName);
        } else {
            System.out.println("The room is not available for the selected dates.");
        }
    }


    ////still need to implement text file for the Reservations
}
