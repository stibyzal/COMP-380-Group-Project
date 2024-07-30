package javaOOP.HotelCalifornia;
import java.util.Scanner;
public class Address extends Payment{
    private String country;
    private String county;
    private String city;
    private String streetName;
    private int zipCode;

    public Address(){} //Default constructor

    public Address(String country, String  county, String city, String streetName, int zipCode){
        setCountry(country);
        setCounty(county);
        setCity(city);
        setStreetName(streetName);
        setZipCode(zipCode);
    }

    // Setter methods
    public void setCountry(String country){
        this.country = country;
    }
    public void setCounty(String county){
        this.county = county;
    }
    public void setCity(String city){
        this.city = city;
    }
    public void setStreetName(String streetName){
        this.streetName = streetName;
    }
    public void setZipCode(int zipCode){
        this.zipCode = zipCode;
    }

    // Getter methods
    public String getCountry(){
        return this.country;
    }
    public String getCounty(){
        return this.county;
    }
    public String getCity(){
        return this.city;
    }
    public String getStreetName(){
        return this.streetName;
    }
    public int getZipCode(){
        return this.zipCode;
    }

    //Other method
    // Getting user address
    Scanner scanner = new Scanner(System.in);
    void getAddress(){
        System.out.print("Country: ");
        setCountry(scanner.nextLine());
        System.out.println();
        System.out.print("County: ");
        setCounty(scanner.nextLine());
        System.out.println();
        System.out.print("City: ");
        setCity(scanner.nextLine());
        System.out.println();
        System.out.print("Street name: ");
        setStreetName(scanner.nextLine());
        System.out.println();
        System.out.print("Zip Code: ");
        setZipCode(scanner.nextInt());
        System.out.println();
    }
}
