package com.example.demo6;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The Reservation class represents a hotel room reservation.
 * It includes details such as check-in date, check-out date, length of stay,
 * and number of guests. This class also provides methods for searching
 * availability, reading reservations from a file, and saving reservations to a file.
 */
public class Reservation {
    private String checkInDate;
    private String checkOutDate;
    private int lengthOfStay;
    private int numGuests;

    /**
     * Default constructor for Reservation. (Not implemented, used to avoid compilation errors)
     *
     * @param checkInDate   The check-in date for the reservation.
     * @param checkOutDate  The check-out date for the reservation.
     * @param length        The length of stay in days.
     * @param guests        The number of guests.
     * @param trim          Placeholder parameter (not used).
     */
    public Reservation(String checkInDate, String checkOutDate, int length, int guests, String trim) {}

    /**
     * Constructs a Reservation object with the specified details.
     *
     * @param checkInDate   The check-in date for the reservation.
     * @param checkOutDate  The check-out date for the reservation.
     * @param lengthOfStay  The length of stay in days.
     * @param numGuests     The number of guests.
     */
    public Reservation(String checkInDate, String checkOutDate, int lengthOfStay, int numGuests) {
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.lengthOfStay = lengthOfStay;
        this.numGuests = numGuests;
    }

    // Set methods

    /**
     * Sets the check-in date for the reservation.
     *
     * @param checkInDate The new check-in date.
     */
    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    /**
     * Sets the check-out date for the reservation.
     *
     * @param checkOutDate The new check-out date.
     */
    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    /**
     * Sets the length of stay for the reservation.
     *
     * @param lengthOfStay The new length of stay in days.
     */
    public void setLengthOfStay(int lengthOfStay) {
        this.lengthOfStay = lengthOfStay;
    }

    /**
     * Sets the number of guests for the reservation.
     *
     * @param numGuests The new number of guests.
     */
    public void setNumGuests(int numGuests) {
        this.numGuests = numGuests;
    }

    // Get methods

    /**
     * Returns the check-in date for the reservation.
     *
     * @return The check-in date as a String.
     */
    public String getCheckInDate() {
        return this.checkInDate;
    }

    /**
     * Returns the check-out date for the reservation.
     *
     * @return The check-out date as a String.
     */
    public String getCheckOutDate() {
        return this.checkOutDate;
    }

    /**
     * Returns the length of stay for the reservation.
     *
     * @return The length of stay in days.
     */
    public int getLengthOfStay() {
        return this.lengthOfStay;
    }

    /**
     * Returns the number of guests for the reservation.
     *
     * @return The number of guests.
     */
    public int getNumGuests() {
        return this.numGuests;
    }

    /**
     * Checks the availability of a room based on the given list of existing reservations.
     * It ensures that the current reservation does not overlap with any existing ones.
     *
     * @param reservations A list of existing reservations.
     * @return true if the room is available; false if there is a conflict with an existing reservation.
     */
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

    /**
     * Reads reservations from a specified file and returns a list of Reservation objects.
     * Each line in the file should contain reservation details separated by commas.
     *
     * @param fileName The name of the file to read reservations from.
     * @return A list of Reservation objects.
     */
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

    /**
     * Saves the current reservation details to a specified file with a confirmation number.
     * The details are appended to the file as a new line.
     *
     * @param fileName            The name of the file to save the reservation to.
     * @param confirmationNumber  The confirmation number associated with the reservation.
     */
    public void saveReservationToFile(String fileName, String confirmationNumber) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
            writer.println(confirmationNumber + "," +
                    this.checkInDate + "," +
                    this.checkOutDate + "," +
                    this.lengthOfStay + "," +
                    this.numGuests);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the room type for the reservation. (Currently not implemented)
     *
     * @return The room type as a String (currently returns null).
     */
    public String getRoomType() {
        return null;
    }
}
