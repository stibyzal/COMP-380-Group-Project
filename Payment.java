package javaOOP.HotelCalifornia;
import java.util.Scanner;
import java.util.ArrayList;

public class Payment {
    private String cardNumber;
    private String expirationDate;
    private String cvvNumber;
   // private ArrayList<String> billingInfo = new ArrayList<String>();

    public Payment(){}  //Default constructor
    public Payment(String cardNumber, String expirationDate, String cvvNumber){

    }

    // Setter methods
    public void setCardNumber(String cardNumber){
        this.cardNumber = cardNumber;
    }
    public void setExpirationDate(String expirationDate){
        this.expirationDate = expirationDate;
    }
    public void setCvvNumber(String cvvNumber){
        this.cvvNumber = cvvNumber;
    }

    // Get methods
    public String getCardNumber(){
        return this.cardNumber;
    }
    public String getCardExpirationDate(){
        return this.expirationDate;
    }
    public String getCvvNumber(){
        return this.cvvNumber;
    }

    // Other classes
    Scanner scanner = new Scanner(System.in);
    void getPaymentInfo(){
        System.out.print("Enter card number: ");
        this.cardNumber = scanner.nextLine();
        System.out.println();
        System.out.print("Enter card expiration date: ");
        this.expirationDate = scanner.nextLine();
        System.out.println();
        System.out.print("Enter the ccv number: ");
        this.cvvNumber = scanner.nextLine();
        System.out.println();

    }



}
