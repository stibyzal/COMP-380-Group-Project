package com.example.demo6;

import java.util.Scanner;

public class ManagerLogin {
    private String userID;
    private String password;

    // Specific UserID and password
    private static final String user_id = "admin";
    private static final String real_password = "password123";

    public ManagerLogin() {
        // Initialize userID and password
        userID = "";
        password = "";
    }

    // Method to prompt user for UserID & password
    public void promptCredentials() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter User ID: ");
        userID = scanner.nextLine();

        System.out.print("Enter Password: ");
        password = scanner.nextLine();

        // Close the scanner
        scanner.close();
    }

    // Getter for userID
    public String getUserID() {
        return userID;
    }

    // Getter for password
    public String getPassword() {
        return password;
    }

    // Validates UserID and Password
    public boolean validateCredentials() {
        return userID.equals(user_id) && password.equals(real_password);
    }

    // Testing Method
    public static void main(String[] args) {
        ManagerLogin login = new ManagerLogin();
        login.promptCredentials();

        if (login.validateCredentials()) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Invalid userID or password.");
        }
    }
}
