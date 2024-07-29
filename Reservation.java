package com.example.hotelcalifornia;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Reservation {
    private String dateOfReservation;
    private int lengthOfStay;
    private int numGuests;

    // Default constructor
    public Reservation() {}

    public Reservation(String dateOfReservation, int lengthOfStay, int numGuests) {
        setDateOfReservation(dateOfReservation);
        setLengthOfStay(lengthOfStay);
        setNumGuests(numGuests);
    }

    // Set methods
    public void setDateOfReservation(String dateOfReservation) {
        this.dateOfReservation = dateOfReservation;
    }
    public void setLengthOfStay(int lengthOfStay) {
        this.lengthOfStay = lengthOfStay;
    }
    public void setNumGuests(int numGuests) {
        this.numGuests = numGuests;
    }

    // Get methods
    public String getDateOfReservation() {
        return this.dateOfReservation;
    }
    public int getLengthOfStay() {
        return this.lengthOfStay;
    }
    public int getNumGuests() {
        return this.numGuests;
    }

    // Other methods
    public void getReservationInfo() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your booking date (yyyy-mm-dd): ");
        this.dateOfReservation = scanner.nextLine();
        System.out.println();
        System.out.print("How many days are you willing to stay: ");
        this.lengthOfStay = scanner.nextInt();
        System.out.println();
        System.out.print("Enter number of guests: ");
        this.numGuests = scanner.nextInt();
        System.out.println();
    }

    // Method for searching the availability of room for the given date range
    public boolean searchAvailability(List<Reservation> reservations) {
        LocalDate checkInDate = LocalDate.parse(this.dateOfReservation);
        LocalDate checkOutDate = checkInDate.plusDays(this.lengthOfStay);

        for (Reservation reservation : reservations) {
            LocalDate existingCheckIn = LocalDate.parse(reservation.getDateOfReservation());
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
                    reservations.add(new Reservation(date, length, guests));
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
            writer.write(this.dateOfReservation + "," + this.lengthOfStay + "," + this.numGuests);
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
