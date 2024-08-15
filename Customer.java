package com.example.hotelcalifornia;

import java.io.*;
import java.util.Scanner;

/**
 Create a customer object
 @author Benjamin Hosseini
 */
public class Customer {
    /**
     * Present customer's first name
     */
    private String firstName;
    /**
     * Present customer's last name
     */
    private String lastName;
    /**
     * Present customer's email
     */
    private String email;
    /**
     * Present customer's phone number
     */
    private String phoneNumber;

    /**
     Constructs customer with null values
     */
    public Customer(){}

    /**
     * Constructs customer with provided values
     * @param firstName customer's first name
     * @param lastName customer's last name
     * @param email customer's email
     * @param phoneNumber customer's phone number
     */
    public Customer(String firstName, String lastName, String email, String phoneNumber){  //Non-default constructor
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
        this.setPhoneNumber(phoneNumber);
    }

    /**
     * Set first name with given value
     * @param firstName customer's first name
     */
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    /**
     * Set last name with given value
     * @param lastName customer's last name
     */
    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    /**
     * Set email with given value
     * @param email customer's email
     */
    public void setEmail(String email){
        this.email = email;
    }

    /**
     * Set phone number with given value
     * @param phoneNumber customer's phone number
     */
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    /**
     * Get fist name
     * @return customer's fist name
     */
    public String getFirstName(){
        return this.firstName;
    }

    /**
     * Get last name
     * @return customer's last name
     */
    public String getLastName(){
        return this.lastName;
    }

    /**
     * Get email
     * @return customer's email address
     */
    public String getEmail(){
        return this.email;
    }

    /**
     * Get phone number
     * @return customer's phone number
     */
    public String getPhoneNumber(){
        return this.phoneNumber;
    }

    /**
     * Saves customer's information to database with reservation number
     * @param fileName file's name in database
     * @param confirmationNumber reservation Number
     */
    public void saveCustomerToFile(String fileName, String confirmationNumber) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
            writer.println(confirmationNumber + "," +
                    this.firstName + "," +
                    this.lastName + "," +
                    this.email + "," +
                    this.phoneNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get customer's information and pass the to setter methods
     */
    void getInfo() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        this.firstName = scanner.nextLine();
        System.out.println();
        System.out.print("Enter your lastName: ");
        this.lastName = scanner.nextLine();
        System.out.println();
        System.out.print("Enter your email: ");
        this.email = scanner.nextLine();
        System.out.println();
        System.out.print("Enter your phoneNumber: ");
        this.phoneNumber = scanner.nextLine();
        System.out.println();

    }


}
