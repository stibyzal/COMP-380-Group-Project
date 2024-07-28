package javaOOP.HotelCalifornia;

import java.util.Scanner;

public class Customer {
    private String name;
    private String lastName;
    private String email;
    private String phoneNumber;

    public Customer(){} //Defaul constructor

    public Customer(String name, String lastName, String email, String phoneNumber){  //Non-default constructor
        this.setName(name);
        this.setLastName(lastName);
        this.setEmail(email);
        this.setPhoneNumber(phoneNumber);
    }

    // Setter Methods
    public void setName(String name){
        this.name = name;
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
    public String getName(){
        return this.name;
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

    //Other methods
     void getInfo() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        this.name = scanner.nextLine();
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
