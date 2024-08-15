package com.example.demo6;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;

import java.time.LocalDate;

/**
 * Controller class for the Manager Window in the hotel management application.
 * This class handles the generation of weekly reports and populates various
 * bar charts with data related to revenue, guests, and rooms booked.
 */
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

    /**
     * Handles the generation of the report for the date selected in the DatePicker.
     * This method retrieves the data for the current and previous weeks and populates
     * the TextAreas and BarCharts accordingly.
     */
    @FXML
    private void handleGenerateReport() {
        LocalDate selectedDate = datePicker.getValue();

        if (selectedDate != null) {
            ManagerReport report = new ManagerReport("reservation.txt", "payment.txt");

            String currentWeekData = report.getWeeklyData(selectedDate);
            String previousWeekData = report.getWeeklyData(selectedDate.minusWeeks(1));

            // Populate the TextAreas with the current and previous week data
            currentWeekTextArea.setText(currentWeekData);
            previousWeekTextArea.setText(previousWeekData);

            // Populate the BarCharts with the relevant data
            populateRevenueChart(report, selectedDate);
            populateGuestsChart(report, selectedDate);
            populateRoomsBookedChart(report, selectedDate);
        } else {
            currentWeekTextArea.setText("Please select a date to generate the report.");
            previousWeekTextArea.setText("Please select a date to generate the report.");
        }
    }

    /**
     * Populates the revenue BarChart with data for the current and previous weeks.
     *
     * @param report       The ManagerReport instance used to retrieve data.
     * @param selectedDate The date selected by the user, which determines the week range.
     */
    private void populateRevenueChart(ManagerReport report, LocalDate selectedDate) {
        // Clear existing data
        revenueChart.getData().clear();

        // Create series for current and previous week revenue
        XYChart.Series<String, Number> currentWeekSeries = new XYChart.Series<>();
        currentWeekSeries.setName("Current Week");
        currentWeekSeries.getData().add(new XYChart.Data<>("Revenue", report.getRevenue(selectedDate)));

        XYChart.Series<String, Number> previousWeekSeries = new XYChart.Series<>();
        previousWeekSeries.setName("Previous Week");
        previousWeekSeries.getData().add(new XYChart.Data<>("Revenue", report.getRevenue(selectedDate.minusWeeks(1))));

        // Add the series to the chart
        revenueChart.getData().addAll(currentWeekSeries, previousWeekSeries);

        // Set bar colors
        for (XYChart.Data<String, Number> data : currentWeekSeries.getData()) {
            data.getNode().setStyle("-fx-bar-fill: orange;");
        }
        for (XYChart.Data<String, Number> data : previousWeekSeries.getData()) {
            data.getNode().setStyle("-fx-bar-fill: yellow;");
        }
    }

    /**
     * Populates the guests BarChart with data for the current and previous weeks.
     *
     * @param report       The ManagerReport instance used to retrieve data.
     * @param selectedDate The date selected by the user, which determines the week range.
     */
    private void populateGuestsChart(ManagerReport report, LocalDate selectedDate) {
        // Clear existing data
        guestsChart.getData().clear();

        // Create series for current and previous week guests
        XYChart.Series<String, Number> currentWeekSeries = new XYChart.Series<>();
        currentWeekSeries.setName("Current Week");
        currentWeekSeries.getData().add(new XYChart.Data<>("Guests", report.getGuests(selectedDate)));

        XYChart.Series<String, Number> previousWeekSeries = new XYChart.Series<>();
        previousWeekSeries.setName("Previous Week");
        previousWeekSeries.getData().add(new XYChart.Data<>("Guests", report.getGuests(selectedDate.minusWeeks(1))));

        // Add the series to the chart
        guestsChart.getData().addAll(currentWeekSeries, previousWeekSeries);

        // Set bar colors
        for (XYChart.Data<String, Number> data : currentWeekSeries.getData()) {
            data.getNode().setStyle("-fx-bar-fill: orange;");
        }
        for (XYChart.Data<String, Number> data : previousWeekSeries.getData()) {
            data.getNode().setStyle("-fx-bar-fill: yellow;");
        }
    }

    /**
     * Populates the rooms booked BarChart with data for the current and previous weeks.
     *
     * @param report       The ManagerReport instance used to retrieve data.
     * @param selectedDate The date selected by the user, which determines the week range.
     */
    private void populateRoomsBookedChart(ManagerReport report, LocalDate selectedDate) {
        // Clear existing data
        roomsBookedChart.getData().clear();

        // Create series for current and previous week rooms booked
        XYChart.Series<String, Number> currentWeekSeries = new XYChart.Series<>();
        currentWeekSeries.setName("Current Week");
        currentWeekSeries.getData().add(new XYChart.Data<>("Rooms Booked", report.getRoomsBooked(selectedDate)));

        XYChart.Series<String, Number> previousWeekSeries = new XYChart.Series<>();
        previousWeekSeries.setName("Previous Week");
        previousWeekSeries.getData().add(new XYChart.Data<>("Rooms Booked", report.getRoomsBooked(selectedDate.minusWeeks(1))));

        // Add the series to the chart
        roomsBookedChart.getData().addAll(currentWeekSeries, previousWeekSeries);

        // Set bar colors
        for (XYChart.Data<String, Number> data : currentWeekSeries.getData()) {
            data.getNode().setStyle("-fx-bar-fill: orange;");
        }
        for (XYChart.Data<String, Number> data : previousWeekSeries.getData()) {
            data.getNode().setStyle("-fx-bar-fill: yellow;");
        }
    }
}
