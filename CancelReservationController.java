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

public class CancelReservationController {

    @FXML
    private Button continueCancelButton;

    @FXML
    private TextField enterResNum;

    public String refundAmt;


    @FXML
    protected void continueToCancellation(ActionEvent actionEvent) throws Exception {
        //gets the reservation number that the user types into the text box, stores it into the string
        String userReservationNum = enterResNum.getText();

        CancelReservation cancel = new CancelReservation(userReservationNum);
        refundAmt = cancel.getRefundAmount(userReservationNum);

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
