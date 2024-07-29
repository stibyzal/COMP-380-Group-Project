package com.example.hotelcalifornia;
import java.util.Scanner;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainWindow extends Application {

    // Main Window
    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main-window.fxml")));
            Scene scene = new Scene(root);
            stage.setTitle("Hotel California");
            stage.setX(100);
            stage.setY(100);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        launch();
    }
    public class Reservation {
        private String dateOfReservation;
        private int lengthOfStay;
        private int numGuests;

        //Default constructor
        public Reservation(){}

        public Reservation(String dateOfReservation, int lengthOfStay, int numGuests){
            setDateOfReservation(dateOfReservation);
            setLengthOfStay(lengthOfStay);
            setNumGuests(numGuests);
        }

        //Set methods
        public void setDateOfReservation(String dateOfReservation){
            this.dateOfReservation = dateOfReservation;
        }
        public void setLengthOfStay(int lengthOfStay){
            this.lengthOfStay = lengthOfStay;
        }
        public void setNumGuests(int numGuests){
            this.numGuests = numGuests;
        }

        //Get methods
        public String getDateOfReservation(){
            return this.dateOfReservation;
        }
        public int getLengthOfStay(){
            return this.lengthOfStay;
        }
        public int getNumGuests(){
            return this.numGuests;
        }

        Scanner scanner = new Scanner(System.in);
        //Other methods
        void getReservationInfo(){
            System.out.print("Enter your booking date: ");
            this.dateOfReservation = scanner.nextLine();
            System.out.println();
            System.out.print("How many day are you willing to stay: ");
            this.lengthOfStay = scanner.nextInt();
            System.out.println();
            System.out.print("Enter number of the guests: ");
            this.numGuests = scanner.nextInt();
            System.out.println();
        }

        // Method for searching the availability of room for the given date
        //Not complete need to add file read for actual database search
        boolean searchAvailability(int date){
            boolean isAvailable = true;
            int[] reservedArr = new int[50];
            //Search in data base if there is an availability during that day
            for (int day : reservedArr){
                if(reservedArr[day] == date){
                    isAvailable = false;
                    break;
                }
            }
            return isAvailable;
        }
    }
}
