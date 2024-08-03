package com.example.projcomp380;

import java.io.*;
import java.util.Scanner;

public class Customer {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    public Customer(){} //Defaul constructor

    public Customer(String firstName, String lastName, String email, String phoneNumber){  //Non-default constructor
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
        this.setPhoneNumber(phoneNumber);
    }

    // Setter Methods
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    // Getter Methods
    public String getFirstName(){
        return this.firstName;
    }
    public String getLastName(){
        return this.lastName;
    }
    public String getEmail(){
        return this.email;
    }
    public String getPhoneNumber(){
        return this.phoneNumber;
    }

    // after confirmation, saveS customer info to file with reservation number
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

    //Other methods
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