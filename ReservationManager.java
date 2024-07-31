package com.example.projcomp380;
import java.util.Scanner;

public class ReservationManager {
    public static void main(String[] args){

        //Testing getters and setters in reservation class
        Reservation reservation = new Reservation();
        reservation.getReservationInfo();

        //----------------------------------------------------

        //Testing Room_choice class
        RoomChoice choice = new RoomChoice();
        choice.roomChoice();
        System.out.println("\nRoom Type: " + choice.getChoice());
        System.out.println();


        //-----------------------------------------------------

        //Testing customer class
        Customer customer = new Customer();
        customer.getInfo();


        //-----------------------------------------------------

        // Testing Payment file
        Payment pay = new Payment();
        pay.getPaymentInfo();

        //----------------------------------------------------
        printReservationInfo(reservation);
        printCustomerInfo(customer);
        printPaymentInfo(pay);

    }
    public static void printReservationInfo(Reservation reservation){
        System.out.println("Data of reservaton: " + reservation.getCheckInDate() + "\nLength of stay: " + reservation.getLengthOfStay() +
                "\nnumber of guests: " + reservation.getNumGuests());
        System.out.println();
    }
    public static void printPaymentInfo(Payment pay){
        System.out.println("your card number is: " + pay.getCardNumber());
        System.out.println("Your card's expiration date is: " + pay.getExpirationMonth() + "/" + pay.getExpirationYear());
        System.out.println("Yor card's cvv number is: " + pay.getCvvNumber());
    }


    public static void printCustomerInfo(Customer customer){
        System.out.println("your name is: " + customer.getName() + " " + customer.getLastName());
        System.out.println("Your email address is: " + customer.getEmail());
        System.out.println("Your phone number is: " + customer.getPhoneNumber());
    }
}
