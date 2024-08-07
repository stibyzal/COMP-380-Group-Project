package com.example.demo6;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class ManagerReport {
    private List<Reservation> reservations;
    private List<Double> payments;

    public ManagerReport(String reservationFileName, String paymentFileName) {
        this.reservations = Reservation.readReservationsFromFile(reservationFileName);
        this.payments = Payment.readPaymentsFromFile(paymentFileName);
    }

    public Map<String, Map<String, Double>> calculateWeeklyData() {
        Map<String, Double> weeklyTotalPrice = new HashMap<>();
        Map<String, Integer> weeklyGuests = new HashMap<>();
        Map<String, Integer> weeklyRooms = new HashMap<>();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());

        // Assuming each reservation corresponds directly to each payment
        for (int i = 0; i < reservations.size(); i++) {
            Reservation reservation = reservations.get(i);
            double totalPrice = payments.get(i);

            LocalDate checkInDate = LocalDate.parse(reservation.getCheckInDate());
            LocalDate startOfWeek = checkInDate.with(weekFields.dayOfWeek(), 1); // Monday
            LocalDate endOfWeek = checkInDate.with(weekFields.dayOfWeek(), 7);   // Sunday
            String weekRange = startOfWeek + " to " + endOfWeek;

            // Calculate weekly total price
            weeklyTotalPrice.put(weekRange, weeklyTotalPrice.getOrDefault(weekRange, 0.0) + totalPrice);

            // Calculate number of guests
            weeklyGuests.put(weekRange, weeklyGuests.getOrDefault(weekRange, 0) + reservation.getNumGuests());

            // Calculate number of rooms booked
            weeklyRooms.put(weekRange, weeklyRooms.getOrDefault(weekRange, 0) + 1);
        }

        // Combine data into a single map for ease of use
        Map<String, Map<String, Double>> weeklyData = new HashMap<>();
        for (String weekRange : weeklyTotalPrice.keySet()) {
            Map<String, Double> data = new HashMap<>();
            data.put("TotalPrice", weeklyTotalPrice.get(weekRange));
            data.put("Guests", (double) weeklyGuests.get(weekRange));
            data.put("Rooms", (double) weeklyRooms.get(weekRange));
            weeklyData.put(weekRange, data);
        }

        return weeklyData;
    }

    public void generateWeeklySalesReport(LocalDate date) {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        LocalDate startOfWeek = date.with(weekFields.dayOfWeek(), 1); // Monday
        LocalDate endOfWeek = date.with(weekFields.dayOfWeek(), 7);   // Sunday
        String weekRange = startOfWeek + " to " + endOfWeek;

        Map<String, Map<String, Double>> weeklyData = calculateWeeklyData();
        Map<String, Double> data = weeklyData.get(weekRange);

        if (data != null) {
            System.out.println("Weekly Sales, Guests, and Rooms Report:");
            System.out.println("Week Range           | Total Price | Guests | Booked Rooms");
            System.out.println(weekRange + " | " + data.get("TotalPrice") + " | " + data.get("Guests") + " | " + data.get("Rooms"));
        } else {
            System.out.println("No data available for the week of " + weekRange);
        }
    }

    public void askForDateAndGenerateReport() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the date (yyyy-MM-dd) for which you want to generate the report: ");
        String dateString = scanner.nextLine();

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault());
            LocalDate date = LocalDate.parse(dateString, formatter);

            generateWeeklySalesReport(date);
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
