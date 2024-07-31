package com.example.projcomp380;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Reservation {
    private String checkInDate;
    private String checkOutDate;
    private int lengthOfStay;
    private int numGuests;

    // Default constructor
    public Reservation() {}

    public Reservation(String checkInDate, String checkOutDate, int lengthOfStay, int numGuests) {
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.lengthOfStay = lengthOfStay;
        this.numGuests = numGuests;
    }

    // Set methods
    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }
    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
    public void setLengthOfStay(int lengthOfStay) {
        this.lengthOfStay = lengthOfStay;
    }
    public void setNumGuests(int numGuests) {
        this.numGuests = numGuests;
    }

    // Get methods
    public String getCheckInDate() {
        return this.checkInDate;
    }
    public String getCheckOutDate() {
        return this.checkOutDate;
    }
    public int getLengthOfStay() {
        return this.lengthOfStay;
    }
    public int getNumGuests() {
        return this.numGuests;
    }

    // Method for searching the availability of room for the given date range
    public boolean searchAvailability(List<Reservation> reservations) {
        LocalDate checkInDate = LocalDate.parse(this.checkInDate);
        LocalDate checkOutDate = LocalDate.parse(this.checkOutDate);

        for (Reservation reservation : reservations) {
            LocalDate existingCheckIn = LocalDate.parse(reservation.getCheckInDate());
            LocalDate existingCheckOut = LocalDate.parse(reservation.getCheckOutDate());

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
                if (parts.length == 4) {
                    String checkInDate = parts[0].trim();
                    String checkOutDate = parts[1].trim();
                    int length = Integer.parseInt(parts[2].trim());
                    int guests = Integer.parseInt(parts[3].trim());
                    reservations.add(new Reservation(checkInDate, checkOutDate, length, guests));
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
            writer.write(this.checkInDate + "," + this.checkOutDate + "," + this.lengthOfStay + "," + this.numGuests);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}