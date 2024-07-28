package com.example.projcomp380;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.geometry.Pos;
import javafx.geometry.Insets;



public class SecondWindowController {

    @FXML
    private ScrollPane scrollPane; // Scrollable window

    @FXML
    private void scrollSecondWindow() {
        // scrolling should resize according to content, still iffy
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(false);

        }

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label groupTitle; // Hotel Name

    @FXML
    private Label dateLabel; // Display date input


    // method to get datepicker input from window 1
    public void displayDates(LocalDate checkInDate, LocalDate checkOutDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String formattedCheckinDate = checkInDate.format(formatter);
        String formattedCheckoutDate = checkOutDate.format(formatter);
        dateLabel.setText("selected dates: " + formattedCheckinDate + " to " + formattedCheckoutDate);

        /* placeholder while waiting for backend
         when displaydates method is called, it will also shows all the rooms available for now for the gui
         eventually this will be its own method (i think?)  */
        addRoomInfo("Single Room", "Description of Room 1", "/com/example/projcomp380/placeholder.jpg");
        addRoomInfo("Twin Room", "Description of Room 2", "/com/example/projcomp380/placeholder.jpg");
        addRoomInfo("Deluxe Room", "Description of Room 3", "/com/example/projcomp380/placeholder.jpg");
        addRoomInfo("Suite", "Description of Room 4", "/com/example/projcomp380/placeholder.jpg");

     }

    @FXML
    private VBox displayRoomBox; // vertical columns for rooms


    // method to add each room type into a vbox
    private void addRoomInfo(String roomType, String roomDescription, String photoPath) {
        HBox roomInfo = new HBox(10);
        roomInfo.setAlignment(Pos.CENTER_LEFT);
        roomInfo.setPadding(new Insets(10));
        roomInfo.setStyle("-fx-border-color: #82BAC4; -fx-border-width: 1px; -fx-border-radius: 8px;");

        displayRoomBox.setFillWidth(true);
        displayRoomBox.setSpacing(20);

        // get image from path
        String imagePath = getClass().getResource(photoPath).toExternalForm();
        Image image = new Image(imagePath);
        // displau room photos
        ImageView roomPhotos = new ImageView(image);
        roomPhotos.setFitHeight(120);
        roomPhotos.setFitWidth(120);

        // vertical columns for room description
        VBox roomDetails = new VBox(10);
        Label nameLabel = new Label(roomType);
        nameLabel.setFont(new Font("Avenir", 18));
        Text descriptionText = new Text(roomDescription);
        descriptionText.setWrappingWidth(300);


        roomDetails.getChildren().addAll(nameLabel, descriptionText);
        roomInfo.getChildren().addAll(roomPhotos, roomDetails);
        displayRoomBox.getChildren().add(roomInfo);


      }

}