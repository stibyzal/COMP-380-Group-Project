package com.example.projcomp380;

import java.util.Scanner;

public class RoomChoice {

    private String[] roomTypes = {"Single room", "Twin room", "Executive room", "Suite"};
    private int[] availableRooms = {1, 0, 0, 0}; // just a test
    private double[] roomPrices = {100.0, 150.0, 200.0, 250.0}; // prices for each rooom
    private String room;
    private String choice;

    // Set method
    public void setRoom(String room) {
        this.room = room;
    }

    // Get method
    public String getRoom() {
        return this.room;
    }

    // Get method for room types
    public String[] getRoomTypes() {
        return this.roomTypes;
    }

    // Get method for choice
    public String getChoice() {
        return this.choice;
    }

    // Get method for price
    public double getRoomPrice(String roomType) {
        for (int i = 0; i < roomTypes.length; i++) {
            if (roomTypes[i].equals(roomType)) {
                return roomPrices[i];
            }
        }
        return 0.0;
    }

    // Get method for available rooms
    public int getAvailableRooms(String roomType) {
        for (int i = 0; i < roomTypes.length; i++) {
            if (roomTypes[i].equals(roomType)) {
                return availableRooms[i];
            }
        }
        return 0;
    }

    // Method for getting the room from the guest
    public void roomChoice() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please choose the room type: ");
        for (int i = 0; i < this.roomTypes.length; i++) {
            System.out.println(i + ") " + roomTypes[i]);
        }
        System.out.print("Enter the corresponding room type number: ");
        int select = scanner.nextInt();

        // if selection is valid
        if (select >= 0 && select < this.roomTypes.length) {
            this.choice = this.roomTypes[select];
        } else {
            System.out.println("Invalid selection.");
        }
    }
}