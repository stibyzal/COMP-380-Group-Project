package com.example.hotelcalifornia;

import java.io.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

/**
 * Class for saving customer's payment information
 * @author Benjamin Hosseini
 */
public class Payment {
    /**
     * Represent card number
     */
    private String cardNumber;
    /**
     * Represent card expiration Month
     */
    private String expirationMonth;
    /**
     * Represent card expiration year
     */
    private String expirationYear;
    /**
     * Represent card cvv number
     */
    private String cvvNumber;
    /**
     * Represent total amount of charge
     */
    private double totalPrice;

    /**
     * Default constructor for Payment class
     */
    public Payment(){}

    /**
     * Construct payment class with given values
     * @param cardNumber the card number
     * @param expirationMonth the expiration month
     * @param expirationYear the expiration year
     * @param cvvNumber the card's cvv number
     * @param totalPrice the total reservation charge
     */
    public Payment(String cardNumber, String expirationMonth, String expirationYear, String cvvNumber, double totalPrice){
        this.cardNumber = cardNumber;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
        this.cvvNumber = cvvNumber;
        this.totalPrice = totalPrice;
    }

    /**
     * Set value to card number
     * @param cardNumber the number on the card
     */
    public void setCardNumber(String cardNumber){
        this.cardNumber = cardNumber;
    }

    /**
     * Set value to expiration year attribute
     * @param expirationYear the year in which card expires
     */
    public void setExpirationYear(String expirationYear){
        this.expirationYear = expirationYear;
    }

    /**
     * Set value to expiration month attribute
     * @param expirationMonth the month in which card expires
     */
    public void setExpirationMonth(String expirationMonth){
        this.expirationMonth = expirationMonth;
    }

    /**
     * Set value to cvv number attribute
     * @param cvvNumber the cvv number of the card
     */
    public void setCvvNumber(String cvvNumber){
        this.cvvNumber = cvvNumber;
    }

    /**
     * Set value to total price attribute
     * @param totalPrice the total reservation charge
     */
    public void setTotalPrice(double totalPrice){
        this.totalPrice = totalPrice;
    }


    // Get methods
    public String getCardNumber(){
        return this.cardNumber;
    }
    public String getExpirationMonth(){
        return this.expirationMonth;
    }
    public String getExpirationYear(){
        return this.expirationYear;
    }
    public String getCvvNumber(){
        return this.cvvNumber;
    }
    public double getTotalPrice(){
        return this.totalPrice;
    }

    /**
     * After confirmation, customer payment info saves into file with
     * @param fileName the name of file in database
     * @param confirmationNumber the reservation number
     */
    public void savePaymentToFile(String fileName, String confirmationNumber) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
            writer.println(confirmationNumber + "," +
                    this.cardNumber + "," +
                    this.expirationMonth + "," +
                    this.expirationYear + "," +
                    this.cvvNumber + "," +
                    this.totalPrice);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Calculate new total charges for reservation
     * @param checkInDate the check in date
     * @param checkOutDate the check out date
     * @param roomType the type of the room
     * @param roomChoice Instance of room choice class ->?
     * @return total reservation charges
     */
    public static double calculateNewTotalPrice(LocalDate checkInDate, LocalDate checkOutDate, String roomType, RoomChoice roomChoice) {
        int days = (int) ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        double roomPrice = roomChoice.getRoomPrice(roomType);
        return days * roomPrice;
    }



    Scanner scanner = new Scanner(System.in);

    /**
     * Getting customer's card information from console
     */
    protected void getPaymentInfo(){
        System.out.print("Enter card number: ");
        this.cardNumber = scanner.nextLine();
        System.out.println();
        System.out.print("Enter card expiration month: ");
        this.expirationMonth = scanner.nextLine();
        System.out.print("Enter card expiration year: ");
        this.expirationYear = scanner.nextLine();
        System.out.println();
        System.out.print("Enter the ccv number: ");
        this.cvvNumber = scanner.nextLine();
        System.out.println();
        System.out.println("Billing address: ");
        //address.getAddress();  // Calling getAddress method in address

    }

}
