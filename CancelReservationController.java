package com.example.projcomp380;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Cancel Reservation Controller
 * Aug 7, 2024
 * @author B. Ascorra
 * The controller class that sets up the GUI for the cancel reservation window.
 */
public class CancelReservationController {
    /**
     * Button to go to the final window where the reservation is cancelled and a refund is issued.
     */
    @FXML
    private Button continueCancelButton;

    /**
     * Text field in the window where a user inputs their reservation number.
     */
    @FXML
    private TextField enterResNum;

    /**
     * The refund amount given after a reservation is cancelled.
     */
    public String refundAmt;

    /**
     * The window that takes the user's reservation number, so they can cancel their reservation.
     * Then creates a final window to show the user a confirmation that their reservation was cancelled.
     * @param actionEvent the button click
     */
    @FXML
    protected void continueToCancellation(ActionEvent actionEvent) {
        //gets the reservation number that the user types into the text box, stores it into the string
        String userReservationNum = enterResNum.getText();

        CancelReservation cancel = new CancelReservation(userReservationNum);
        refundAmt = cancel.refundAmountGetter();

        //setting up the final popup
        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setMinWidth(300);
        stage.setMinHeight(200);
        stage.setTitle("Reservation Cancelled");

        Label label1 = new Label("Reservation " + userReservationNum + " cancelled");
        Label label2 = new Label("Refund issued: " + refundAmt);
        Button button = new Button("Close");
        button.setOnAction(e -> stage.close());

        VBox layout = new VBox(20);
        layout.getChildren().addAll(label1, label2, button);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.show();
    }
}
