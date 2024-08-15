package com.example.hotelcalifornia;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * Main window starts the project
 * @author Kristina Patrisha Dela Merced
 */
public class MainWindow extends Application {

    /**
     * Starts the project
     * @param stage java stage library
     * @throws IOException prompt user with an error message
     * in case of I/O error
     */
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


    /**
     *  launching the main window
     * @param args name of the array variable
     */
    public static void main(String[] args) {
        launch();
    }
}







    public static void main(String[] args) {
        launch();
    }
}
