package com.example.projcomp380;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindow extends Application {

    // Main Window
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("main-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 816, 360);
        stage.setTitle("Hotel California");
        stage.setX(100);
        stage.setY(100);
        stage.setScene(scene);
        stage.show();


    }






    public static void main(String[] args) {
        launch();
    }
}
