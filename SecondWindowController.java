package com.example.projcomp380;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import java.util.Date;
import javafx.stage.Stage;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.ScrollPane;
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
    public Button nextSceneButton;


    @FXML
    private ScrollPane scrollPane; // Scrollable window

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label groupTitle; // Hotel Name

    @FXML
    private Label dateLabel; // Display date input

    @FXML
    private VBox displayRoomBox; // vertical columns for rooms

    private String tempCid;
    private String tempCod;


//----------------------------------------------------------------------
    //Method for calculating days spent at hotel
    public String tempGuests;
    private long nightsAtHotel;
    public void totalDays(LocalDate first, LocalDate second){
        Date in = java.sql.Date.valueOf(first);
        Date out = java.sql.Date.valueOf(second);

        long diff = (in.getTime()-out.getTime())/86400000;
        this.nightsAtHotel = Math.abs(diff);
    }
//----------------------------------------------------------------------

    // method to get datepicker input from window 1
    //****Added String numOfGuests because it is the only way atm for us to get data from first scene to the next
    public void displayDates(LocalDate checkInDate, LocalDate checkOutDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String formattedCheckinDate = checkInDate.format(formatter);
        String formattedCheckoutDate = checkOutDate.format(formatter);
        dateLabel.setText("selected dates: " + formattedCheckinDate + " to " + formattedCheckoutDate);

        RoomChoice roomChoice = new RoomChoice();
        for (String roomType : roomChoice.getRoomTypes()) {
            double price = roomChoice.getRoomPrice(roomType);
            addRoomInfo(roomType, "description of " + roomType, "/com/example/projcomp380/placeholder.jpg", price);
        }

//-------------------------------------------------------------------------
        totalDays(checkInDate, checkOutDate);

        tempCid = formattedCheckinDate;
        tempCod = formattedCheckoutDate;
//-------------------------------------------------------------------------
    }

    // method to add each room type into a vbox
    private void addRoomInfo(String roomType, String roomDescription, String photoPath, double price) {
        HBox roomInfo = new HBox(10);
        roomInfo.setAlignment(Pos.CENTER_LEFT);
        roomInfo.setPadding(new Insets(10));
        roomInfo.setStyle("-fx-border-color: #82BAC4; -fx-border-width: 1px; -fx-border-radius: 8px;");

        displayRoomBox.setFillWidth(true);
        displayRoomBox.setSpacing(20);

        // Get image from path
        String imagePath = getClass().getResource(photoPath).toExternalForm();
        Image image = new Image(imagePath);
        // Display room photos
        ImageView roomPhotos = new ImageView(image);
        roomPhotos.setFitHeight(120);
        roomPhotos.setFitWidth(120);

        // Vertical columns for room description
        VBox roomDetails = new VBox(10);
        Label nameLabel = new Label(roomType);
        nameLabel.setFont(new Font("Avenir", 18));
        Text descriptionText = new Text(roomDescription);
        descriptionText.setWrappingWidth(300);
        Label priceLabel = new Label("Price per night: $" + price);
        priceLabel.setFont(new Font("Avenir", 14));

        roomDetails.getChildren().addAll(nameLabel, descriptionText, priceLabel);
        roomInfo.getChildren().addAll(roomPhotos, roomDetails);
        displayRoomBox.getChildren().add(roomInfo);
    }

    // scroll pane should adjust accordingly, still not sure if it works
    private void scrollSecondWindow() {
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(false);
    }

//------Button to switch to the next window and puts all reservation data into next window----------------------------
    @FXML
    protected void nextSceneButtonClick(ActionEvent actionEvent) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("third-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        //Doing temp cuz I haven't looked at the RoomChoice class yet
        String tempRoomChosen = "Twin Room";
        ThirdWindowController thirdWindowController = fxmlLoader.getController();
        thirdWindowController.finalRes.setCheckInDate(tempCid);
        thirdWindowController.finalRes.setCheckOutDate(tempCod);
        thirdWindowController.finalRes.setLengthOfStay((int)nightsAtHotel);
        thirdWindowController.finalRes.setNumGuests(tempGuests);
        thirdWindowController.reservationLabels();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}
