package com.example.demo6;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * The RoomChoice class allows guests to choose a room type from a predefined list.
 * It provides methods to retrieve room types, prices, and image paths, as well as
 * to save the guest's room choice to a file.
 */
public class RoomChoice {

    private String[] roomTypes = {"single room", "twin room", "suite", "executive room"};
    private int[] availableRooms = {10, 10, 10, 10}; // Number of available rooms for each type
    private double[] roomPrices = {100.00, 200.00, 250.00, 450.00}; // Prices for each room type
    private String room;
    private String choice;

    private Map<String, String> roomTypesWithImages;

    /**
     * Constructs a RoomChoice object and initializes the room types with associated images.
     * Uses a LinkedHashMap to maintain the order of room types.
     */
    public RoomChoice() {
        roomTypesWithImages = new LinkedHashMap<>();
        roomTypesWithImages.put("single room", "/com/example/demo6/singleroom.jpg");
        roomTypesWithImages.put("twin room", "/com/example/demo6/twinroom.jpg");
        roomTypesWithImages.put("suite", "/com/example/demo6/suite.jpg");
        roomTypesWithImages.put("executive room", "/com/example/demo6/executiveroom.jpg");
    }

    /**
     * Returns an array of room types from the roomTypesWithImages map.
     *
     * @return An array of room types.
     */
    public String[] getRoomTypesWithImages() {
        return roomTypesWithImages.keySet().toArray(new String[0]);
    }

    /**
     * Returns the price of the specified room type.
     *
     * @param roomType The type of room for which to get the price.
     * @return The price of the room type, or 0.0 if the room type is not found.
     */
    public double getRoomPrice(String roomType) {
        for (int i = 0; i < roomTypes.length; i++) {
            if (roomTypes[i].equals(roomType)) {
                return roomPrices[i];
            }
        }
        return 0.0;
    }

    /**
     * Returns the image path for the specified room type.
     *
     * @param roomType The type of room for which to get the image path.
     * @return The image path for the room type, or a placeholder image path if the room type is not found.
     */
    public String getRoomImagePath(String roomType) {
        return roomTypesWithImages.getOrDefault(roomType, "/com/example/projcomp380/placeholder.jpg");
    }

    /**
     * Sets the selected room type.
     *
     * @param room The room type to set.
     */
    public void setRoom(String room) {
        this.room = room;
    }

    /**
     * Returns the selected room type.
     *
     * @return The selected room type.
     */
    public String getRoom() {
        return this.room;
    }

    /**
     * Returns an array of all available room types.
     *
     * @return An array of room types.
     */
    public String[] getRoomTypes() {
        return this.roomTypes;
    }

    /**
     * Returns the guest's choice of room type.
     *
     * @return The selected room type.
     */
    public String getChoice() {
        return this.choice;
    }

    /**
     * Returns the number of available rooms for the specified room type.
     *
     * @param roomType The type of room for which to get the availability.
     * @return The number of available rooms, or 0 if the room type is not found.
     */
    public int getAvailableRooms(String roomType) {
        for (int i = 0; i < roomTypes.length; i++) {
            if (roomTypes[i].equals(roomType)) {
                return availableRooms[i];
            }
        }
        return 0;
    }

    /**
     * Saves the guest's room choice and confirmation number to a specified file.
     * The room choice is appended to the file as a new line.
     *
     * @param fileName           The name of the file to save the room choice to.
     * @param confirmationNumber The confirmation number associated with the reservation.
     */
    public void saveRoomChoiceToFile(String fileName, String confirmationNumber) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
            writer.println(confirmationNumber + "," + this.room);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prompts the guest to choose a room type from the available options.
     * The guest's choice is stored in the choice field.
     */
    public void roomChoice() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please choose the room type: ");
        for (int i = 0; i < this.roomTypes.length; i++) {
            System.out.println(i + ") " + roomTypes[i]);
        }
        System.out.print("Enter the corresponding room type number: ");
        int select = scanner.nextInt();

        // Validate selection
        if (select >= 0 && select < this.roomTypes.length) {
            this.choice = this.roomTypes[select];
        } else {
            System.out.println("Invalid selection.");
        }
    }

    /**
     * Returns the type of room that has been set.
     *
     * @return The type of room set in this instance.
     */
    public String getRoomType() {
        return this.room;
    }
}
