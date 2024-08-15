
package com.example.hotelcalifornia;

import java.io.*;
import java.util.Scanner;
/**
 * Address class that gets corresponding address to the payment card
 *@author Benjamin Hosseini
 */
public class Address extends Payment{
    /**
     * Represent country
     */
    private String country;
    /**
     * Represent state
     */
    private String state;
    /**
     * Represent city
     */
    private String city;
    /**
     * Represent streetName
     */
    private String streetName;
    /**
     * Represent zip code
     */
    private int zipCode;

    /**
     * Default constructor
     */
    public Address(){} //Default constructor

    /**
     * Constructs Address with given values
     * @param country the name of the country
     * @param state the name of the state
     * @param city the name of city
     * @param streetName the name of the street
     * @param zipCode the zip code
     */
    public Address(String country, String  state, String city, String streetName, int zipCode){
        setCountry(country);
        setState(state);
        setCity(city);
        setStreetName(streetName);
        setZipCode(zipCode);
    }

    /**
     * Set value to country attribute
     * @param country the value of country
     */
    public void setCountry(String country){
        this.country = country;
    }

    /**
     * Set value to state attribute
     * @param state the value of state
     */
    public void setState(String state){
        this.state = state;
    }

    /**
     * Set value to city attribute
     * @param city the value of city
     */
    public void setCity(String city){
        this.city = city;
    }

    /**
     * Set value to street name attribute
     * @param streetName the value of street name
     */
    public void setStreetName(String streetName){
        this.streetName = streetName;
    }

    /**
     * Set value to zip code attribute
     * @param zipCode the value of zip code
     */
    public void setZipCode(int zipCode){
        this.zipCode = zipCode;
    }

    /**
     * Returns value of country
     * @return country
     */
    public String getCountry(){
        return this.country;
    }

    /**
     * Returns value of state
     * @return state
     */
    public String getState(){
        return this.state;
    }

    /**
     * Returns value of city
     * @return city
     */
    public String getCity(){
        return this.city;
    }

    /**
     * Returns value of street name
     * @return street name
     */
    public String getStreetName(){
        return this.streetName;
    }

    /**
     * Returns value of zip code
     * @return zip code
     */
    public int getZipCode(){
        return this.zipCode;
    }

    /**
     * after confirmation, save customer address to file with reservation number
     * @param fileName the name of file in database
     * @param confirmationNumber the confirmation number
     */
    public void saveAddressToFile(String fileName, String confirmationNumber) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
            writer.println(confirmationNumber + "," +
                    this.country + "," +
                    this.state + "," +
                    this.city + "," +
                    this.streetName + "," +
                    this.zipCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Get user address from console and pass it to setter methods
     */
    Scanner scanner = new Scanner(System.in);
    void getAddress(){
        System.out.print("Country: ");
        setCountry(scanner.nextLine());
        System.out.println();
        System.out.print("State: ");
        setState(scanner.nextLine());
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
