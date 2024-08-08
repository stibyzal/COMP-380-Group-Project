package com.example.projcomp380;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class RoomChoice {

    private String[] roomTypes = {"single room", "twin room", "suite", "executive room"};
    private int[] availableRooms = {10, 10, 10, 10}; // just a test
    private double[] roomPrices = {100.00, 200.00, 250.00, 450.00}; // prices for each room
    private String room;
    private String choice;

    private Map<String, String> roomTypesWithImages;

    public RoomChoice() {
        // used LinkedHashMap, i was having troubles with the order of rooms with regular hashmap
        roomTypesWithImages = new LinkedHashMap<>();
        roomTypesWithImages.put("single room", "/com/example/projcomp380/singleroom.jpg");
        roomTypesWithImages.put("twin room", "/com/example/projcomp380/twinroom.jpg");
        roomTypesWithImages.put("suite", "/com/example/projcomp380/suite.jpg");
        roomTypesWithImages.put("executive room", "/com/example/projcomp380/executiveroom.jpg");
    }

    // method returns an array of room types from the roomTypesWithImages map
    public String[] getRoomTypesWithImages() {
        return roomTypesWithImages.keySet().toArray(new String[0]);
    }

    // method to get the price of a room
    public double getRoomPrice(String roomType) {
        for (int i = 0; i < roomTypes.length; i++) {
            if (roomTypes[i].equals(roomType)) {
                return roomPrices[i];
            }
        }
        return 0.0;
    }

    // method to get images of room per room type
    public String getRoomImagePath(String roomType) {
        return roomTypesWithImages.getOrDefault(roomType, "/com/example/projcomp380/placeholder.jpg");
    }

    // Set Method
    public void setRoom(String room) {
        this.room = room;
    }

    // Get Method
    public String getRoom() {
        return this.room;
    }

    // Get Method
    public String[] getRoomTypes() {
        return this.roomTypes;
    }

    // Get method
    public String getChoice() {
        return this.choice;
    }

    // Get method available rooms - test
    public int getAvailableRooms(String roomType) {
        for (int i = 0; i < roomTypes.length; i++) {
            if (roomTypes[i].equals(roomType)) {
                return availableRooms[i];
            }
        }
        return 0;
    }

    // method to save customer room choice with res. #
    public void saveRoomChoiceToFile(String fileName, String confirmationNumber) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
            writer.println(confirmationNumber + "," + this.room);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Other method
    // Method for getting the room choice from the guest
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

    // Add getter for roomType
    public String getRoomType() {
        return this.room;
    }
}