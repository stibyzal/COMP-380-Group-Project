package com.example.demo6;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class ManagerReport {
    private List<Reservation> reservations;
    private List<Double> payments;

    public ManagerReport(String reservationFileName, String paymentFileName) {
        this.reservations = new ArrayList<>();
        this.payments = new ArrayList<>();
        readReservationsAndPaymentsFromFile(reservationFileName, paymentFileName);
    }

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

    public void calculateAndPrintWeeklyData(LocalDate date) {
        double weeklyTotalPrice = 0.0;
        int weeklyGuests = 0;
        int weeklyRoomsBooked = 0;
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

                // Add to weekly guests count
                weeklyGuests += reservation.getNumGuests();

                // Add to weekly rooms booked count
                weeklyRoomsBooked += 1;
            }
        }

        // Print the weekly data
        String weekRange = startOfWeek + " to " + endOfWeek;
        System.out.println("Weekly Sales, Guests, and Rooms Report:");
        System.out.println("Week Range               | Total Price | Guests | Rooms Booked");
        System.out.println(weekRange + "  |   " + weeklyTotalPrice + "    | " + weeklyGuests + "    | " + weeklyRoomsBooked);
    }

    public void askForDateAndGenerateReport() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the date (yyyy-MM-dd) for which you want to generate the report: ");
        String dateString = scanner.nextLine();

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault());
            LocalDate date = LocalDate.parse(dateString, formatter);

            calculateAndPrintWeeklyData(date);
        } catch (Exception e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            // Debug statement to show the exception message
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ManagerReport report = new ManagerReport("reservation.txt", "payment.txt");
        report.askForDateAndGenerateReport();
    }
}
