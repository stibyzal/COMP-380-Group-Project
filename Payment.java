package com.example.projcomp380;

import java.io.*;
import java.util.Scanner;

public class Payment {
    private String cardNumber;
    private String expirationMonth;
    private String expirationYear;
    private String cvvNumber;
    private double totalPrice;


    public Payment(){}  //Default constructor
    public Payment(String cardNumber, String expirationMonth, String expirationYear, String cvvNumber,double totalPrice){
        this.cardNumber = cardNumber;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
        this.cvvNumber = cvvNumber;
        this.totalPrice = totalPrice;
        
    }

    // Setter methods
    public void setCardNumber(String cardNumber){
        this.cardNumber = cardNumber;
    }
    public void setExpirationYear(String expirationYear){
        this.expirationYear = expirationYear;
    }
    public void setExpirationMonth(String expirationMonth){
        this.expirationMonth = expirationMonth;
    }
    public void setCvvNumber(String cvvNumber){
        this.cvvNumber = cvvNumber;
    }
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

    // after confirmation, customer payment info saves into file with res. #
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

    // Other classes
    Scanner scanner = new Scanner(System.in);
    void getPaymentInfo(){
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
