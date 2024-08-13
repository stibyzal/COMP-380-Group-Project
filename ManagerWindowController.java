package com.example.demo6;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;

import java.time.LocalDate;

public class ManagerWindowController {

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextArea currentWeekTextArea;

    @FXML
    private TextArea previousWeekTextArea;

    @FXML
    private BarChart<String, Number> revenueChart;

    @FXML
    private BarChart<String, Number> guestsChart;

    @FXML
    private BarChart<String, Number> roomsBookedChart;

    // Method to generate the report for given date
    @FXML
    private void handleGenerateReport() {
        LocalDate selectedDate = datePicker.getValue();

        if (selectedDate != null) {
            ManagerReport report = new ManagerReport("reservation.txt", "payment.txt");

            String currentWeekData = report.getWeeklyData(selectedDate);
            String previousWeekData = report.getWeeklyData(selectedDate.minusWeeks(1));

            // current on left, previous on right
            currentWeekTextArea.setText(currentWeekData);
            previousWeekTextArea.setText(previousWeekData);

            // puts info into the bar chart
            populateRevenueChart(report, selectedDate);
            populateGuestsChart(report, selectedDate);
            populateRoomsBookedChart(report, selectedDate);
        } else {
            currentWeekTextArea.setText("Please select a date to generate the report.");
            previousWeekTextArea.setText("Please select a date to generate the report.");
        }
    }

    private void populateRevenueChart(ManagerReport report, LocalDate selectedDate) {
        // Clear existing data
        revenueChart.getData().clear();

        // current week bar
        XYChart.Series<String, Number> currentWeekSeries = new XYChart.Series<>();
        currentWeekSeries.setName("Current Week");
        currentWeekSeries.getData().add(new XYChart.Data<>("Revenue", report.getRevenue(selectedDate)));

        // Creates bar for prevous week
        XYChart.Series<String, Number> previousWeekSeries = new XYChart.Series<>();
        previousWeekSeries.setName("Previous Week");
        previousWeekSeries.getData().add(new XYChart.Data<>("Revenue", report.getRevenue(selectedDate.minusWeeks(1))));

        // Add the series to the chart
        revenueChart.getData().addAll(currentWeekSeries, previousWeekSeries);

        // Colors of Bars
        for (XYChart.Data<String, Number> data : currentWeekSeries.getData()) {
            data.getNode().setStyle("-fx-bar-fill: orange;");
        }
        for (XYChart.Data<String, Number> data : previousWeekSeries.getData()) {
            data.getNode().setStyle("-fx-bar-fill: yellow;");
        }
    }

    private void populateGuestsChart(ManagerReport report, LocalDate selectedDate) {
        guestsChart.getData().clear();

        XYChart.Series<String, Number> currentWeekSeries = new XYChart.Series<>();
        currentWeekSeries.setName("Current Week");
        currentWeekSeries.getData().add(new XYChart.Data<>("Guests", report.getGuests(selectedDate)));

        XYChart.Series<String, Number> previousWeekSeries = new XYChart.Series<>();
        previousWeekSeries.setName("Previous Week");
        previousWeekSeries.getData().add(new XYChart.Data<>("Guests", report.getGuests(selectedDate.minusWeeks(1))));

        guestsChart.getData().addAll(currentWeekSeries, previousWeekSeries);

        for (XYChart.Data<String, Number> data : currentWeekSeries.getData()) {
            data.getNode().setStyle("-fx-bar-fill: orange;");
        }
        for (XYChart.Data<String, Number> data : previousWeekSeries.getData()) {
            data.getNode().setStyle("-fx-bar-fill: yellow;");
        }
    }

    private void populateRoomsBookedChart(ManagerReport report, LocalDate selectedDate) {
        roomsBookedChart.getData().clear();

        XYChart.Series<String, Number> currentWeekSeries = new XYChart.Series<>();
        currentWeekSeries.setName("Current Week");
        currentWeekSeries.getData().add(new XYChart.Data<>("Rooms Booked", report.getRoomsBooked(selectedDate)));

        XYChart.Series<String, Number> previousWeekSeries = new XYChart.Series<>();
        previousWeekSeries.setName("Previous Week");
        previousWeekSeries.getData().add(new XYChart.Data<>("Rooms Booked", report.getRoomsBooked(selectedDate.minusWeeks(1))));

        roomsBookedChart.getData().addAll(currentWeekSeries, previousWeekSeries);

        for (XYChart.Data<String, Number> data : currentWeekSeries.getData()) {
            data.getNode().setStyle("-fx-bar-fill: orange;");
        }
        for (XYChart.Data<String, Number> data : previousWeekSeries.getData()) {
            data.getNode().setStyle("-fx-bar-fill: yellow;");
        }
    }
}
