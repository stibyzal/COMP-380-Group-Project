package com.example.projcomp380;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SecondWindowController {

    @FXML
    private ScrollPane scrollPane; // Scrollable window

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label groupTitle; // Hotel Name

    @FXML
    private Label dateLabel; // Display date input

    @FXML
    private VBox displayRoomBox; // Vertical columns for rooms

    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int numGuests;

    // Method to get datepicker input from window 1
    public void displayDates(LocalDate checkInDate, LocalDate checkOutDate, int numGuests) {
        // store input values
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numGuests = numGuests;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy"); // change date format
        String formattedCheckinDate = checkInDate.format(formatter);
        String formattedCheckoutDate = checkOutDate.format(formatter);
        // updates gui label  with user input from window 1
        dateLabel.setText("selected dates: " + formattedCheckinDate + " to " + formattedCheckoutDate +
                "  number of guests: " + numGuests);

        // create instance to get room details from room choice class
        RoomChoice roomChoice = new RoomChoice();
        for (String roomType : roomChoice.getRoomTypesWithImages()) {
            double price = roomChoice.getRoomPrice(roomType);
            String photoPath = roomChoice.getRoomImagePath(roomType);
            // method that updates GUI with room details from roomchoice class
            addRoomInfo(roomType, "description of " + roomType, photoPath, price);
        }
    }

    // method to add each room type into a vbox
    private void addRoomInfo(String roomType, String roomDescription, String photoPath, double price) {
        HBox roomInfo = new HBox(10);
        roomInfo.setAlignment(Pos.CENTER_LEFT);
        roomInfo.setPadding(new Insets(10));
        roomInfo.setStyle("-fx-border-color: #82BAC4; -fx-border-width: 1px; -fx-border-radius: 8px;");

        displayRoomBox.setFillWidth(true);
        displayRoomBox.setSpacing(20);

        // get image from path using url
        URL imageUrl = getClass().getResource(photoPath);
        if (imageUrl == null) {
            System.err.println("Image not found: " + photoPath); // used for debbuging
            return;
        }

        // creats an object for the image from url
        Image image = new Image(imageUrl.toExternalForm());
        ImageView roomPhotos = new ImageView(image);
        roomPhotos.setFitHeight(120);
        roomPhotos.setFitWidth(120);

        // vertical columns for room description
        VBox roomDetails = new VBox(10);
        Label nameLabel = new Label(roomType);
        nameLabel.setFont(new Font("Avenir", 18));
        Text descriptionText = new Text(roomDescription);
        descriptionText.setWrappingWidth(300);
        Label priceLabel = new Label("Price per night: $" + price);
        priceLabel.setFont(new Font("Avenir", 14));

        // add room details to the vertical columns
        roomDetails.getChildren().addAll(nameLabel, descriptionText, priceLabel);

        // action button to show 3rd window and includes all input from user
        Button bookNowButton = new Button("Book Now");
        bookNowButton.setAlignment(Pos.CENTER_RIGHT);
        bookNowButton.setOnAction(event -> {
            // user input being retrieved
            showThirdWindow(roomType, roomDescription, price, image);
        });

        // horizontal box for book now button
        HBox buttonBox = new HBox(bookNowButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.setPadding(new Insets(10, 0, 10, 0));


        roomInfo.getChildren().addAll(roomPhotos, roomDetails, buttonBox);
        displayRoomBox.getChildren().add(roomInfo);
    }

    // opens up to the next scene
    private void showThirdWindow(String roomType, String roomDescription, double price, Image image) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projcomp380/third-window.fxml"));
            Parent root = loader.load();

            // gets reservation details and pass them to the 3rd window
            ThirdWindowController controller = loader.getController();
            controller.getReservationDetails(checkInDate, checkOutDate, numGuests, roomType, price, roomDescription, image);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Book Room");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // scroll should adjust but still iffy
    private void scrollSecondWindow() {
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(false);
    }
}