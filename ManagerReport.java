package com.example.demo6;

import com.example.demo6.Reservation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * The ManagerReport class is responsible for generating weekly reports
 * for hotel reservations, including revenue, guests, and rooms booked.
 * It reads data from reservation and payment files and allows the user
 * to generate reports for a specified date and the previous week.
 */
public class ManagerReport {
    private List<Reservation> reservations;
    private List<Double> payments;

    /**
     * Constructs a ManagerReport instance, reading reservations and payments
     * from the specified files.
     *
     * @param reservationFileName The name of the file containing reservation data.
     * @param paymentFileName     The name of the file containing payment data.
     */
    public ManagerReport(String reservationFileName, String paymentFileName) {
        this.reservations = new ArrayList<>();
        this.payments = new ArrayList<>();
        readReservationsAndPaymentsFromFile(reservationFileName, paymentFileName);
    }

    /**
     * Reads reservations and payments data from the specified files and
     * stores them in the respective lists.
     *
     * @param reservationFileName The name of the file containing reservation data.
     * @param paymentFileName     The name of the file containing payment data.
     */
    private void readReservationsAndPaymentsFromFile(String reservationFileName, String paymentFileName) {
        try (BufferedReader reservationReader = new BufferedReader(new FileReader(reservationFileName));
             BufferedReader paymentReader = new BufferedReader(new FileReader(paymentFileName))) {
            String reservationLine, paymentLine;

            while ((reservationLine = reservationReader.readLine()) != null && (paymentLine = paymentReader.readLine()) != null) {
                String[] reservationParts = reservationLine.split(",");
                String[] paymentParts = paymentLine.split(",");

                if (reservationParts.length == 5 && paymentParts.length == 6) {
                    String checkInDate = reservationParts[1].trim();
                    String checkOutDate = reservationParts[2].trim();
                    int length = Integer.parseInt(reservationParts[3].trim());
                    int guests = Integer.parseInt(reservationParts[4].trim());
                    double totalPrice = Double.parseDouble(paymentParts[5].trim());

                    reservations.add(new Reservation(checkInDate, checkOutDate, length, guests));
                    payments.add(totalPrice);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Calculates the total revenue for a given week based on the specified date.
     *
     * @param date The date within the week for which to calculate the revenue.
     * @return The total revenue for the specified week.
     */
    public double getRevenue(LocalDate date) {
        double weeklyTotalPrice = 0.0;
        WeekFields weekFields = WeekFields.of(Locale.getDefault());

        LocalDate startOfWeek = date.with(weekFields.dayOfWeek(), 1); // Monday
        LocalDate endOfWeek = date.with(weekFields.dayOfWeek(), 7);   // Sunday

        for (int i = 0; i < reservations.size(); i++) {
            Reservation reservation = reservations.get(i);
            double totalPrice = payments.get(i);

            LocalDate checkInDate = LocalDate.parse(reservation.getCheckInDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            if (!checkInDate.isBefore(startOfWeek) && !checkInDate.isAfter(endOfWeek)) {
                // Add to weekly total price
                weeklyTotalPrice += totalPrice;
            }
        }
        return weeklyTotalPrice;
    }

    /**
     * Calculates the total number of guests for a given week based on the specified date.
     *
     * @param date The date within the week for which to calculate the number of guests.
     * @return The total number of guests for the specified week.
     */
    public int getGuests(LocalDate date) {
        int weeklyGuests = 0;
        WeekFields weekFields = WeekFields.of(Locale.getDefault());

        LocalDate startOfWeek = date.with(weekFields.dayOfWeek(), 1); // Monday
        LocalDate endOfWeek = date.with(weekFields.dayOfWeek(), 7);   // Sunday

        for (Reservation reservation : reservations) {
            LocalDate checkInDate = LocalDate.parse(reservation.getCheckInDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            if (!checkInDate.isBefore(startOfWeek) && !checkInDate.isAfter(endOfWeek)) {
                // Add to weekly guests count
                weeklyGuests += reservation.getNumGuests();
            }
        }
        return weeklyGuests;
    }

    /**
     * Calculates the total number of rooms booked for a given week based on the specified date.
     *
     * @param date The date within the week for which to calculate the number of rooms booked.
     * @return The total number of rooms booked for the specified week.
     */
    public int getRoomsBooked(LocalDate date) {
        int weeklyRoomsBooked = 0;
        WeekFields weekFields = WeekFields.of(Locale.getDefault());

        LocalDate startOfWeek = date.with(weekFields.dayOfWeek(), 1); // Monday
        LocalDate endOfWeek = date.with(weekFields.dayOfWeek(), 7);   // Sunday

        for (Reservation reservation : reservations) {
            LocalDate checkInDate = LocalDate.parse(reservation.getCheckInDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            if (!checkInDate.isBefore(startOfWeek) && !checkInDate.isAfter(endOfWeek)) {
                // Add to weekly rooms booked count
                weeklyRoomsBooked += 1;
            }
        }
        return weeklyRoomsBooked;
    }

    /**
     * Generates a summary of data for a given week, including revenue,
     * guests, and rooms booked.
     *
     * @param date The date within the week for which to generate the data.
     * @return A string containing the summary of the weekly data.
     */
    public String getWeeklyData(LocalDate date) {
        StringBuilder weeklyData = new StringBuilder();
        double weeklyTotalPrice = getRevenue(date);
        int weeklyGuests = getGuests(date);
        int weeklyRoomsBooked = getRoomsBooked(date);

        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        LocalDate startOfWeek = date.with(weekFields.dayOfWeek(), 1); // Monday
        LocalDate endOfWeek = date.with(weekFields.dayOfWeek(), 7);   // Sunday

        weeklyData.append("Week Range: ").append(startOfWeek).append(" to ").append(endOfWeek).append("\n")
                .append("Revenue: ").append(weeklyTotalPrice).append("\n")
                .append("Guests: ").append(weeklyGuests).append("\n")
                .append("Rooms Booked: ").append(weeklyRoomsBooked).append("\n");

        return weeklyData.toString();
    }

    /**
     * Prompts the user for a date and generates a report for the specified week
     * as well as the previous week. Outputs the results to the console.
     */
    public void askForDateAndGenerateReport() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the date (yyyy-MM-dd) for which you want to generate the report: ");
        String dateString = scanner.nextLine();

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault());
            LocalDate date = LocalDate.parse(dateString, formatter);

            // Generate the report for the specified date
            String reportData = getWeeklyData(date);
            System.out.println(reportData);

            // Also generate the report for the previous week
            String previousWeekData = getWeeklyData(date.minusWeeks(1));
            System.out.println("Previous Week's Data:\n" + previousWeekData);
        } catch (Exception e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            e.printStackTrace();
        }
    }

    /**
     * The main method to run the ManagerReport program, which reads data
     * from the reservation and payment files and generates reports based on user input.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        ManagerReport report = new ManagerReport("reservation.txt", "payment.txt");
        report.askForDateAndGenerateReport();
    }
}
