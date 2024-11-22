package com.mycompany.cafemocha;

import java.io.*;
import java.util.Scanner;

public class CafeMocha {

    // Define constants for file paths - Encapsulation 
    static final String USERS_FILE = "user.txt";
    static final String ORDERS_FILE = "orders.txt";
    static final String MENU_FILE = "menu.txt";
    static final String BILLS_FILE = "bills.txt";

    public static void main(String[] args) {
        displayWelcomeMessage(); // Display the welcome message with a stylish banner - Input/Output
        boolean isLoggedIn = false; // Track login status - Boolean Logic
        Scanner sc = new Scanner(System.in); // Scanner for user input - Input/Output

        // Main program loop - Control Structures
        while (true) {
            if (!isLoggedIn) {
                isLoggedIn = login(); // Attempt to log in the user - Boolean Logic
                if (isLoggedIn) {
                    System.out.println("Login successful!"); // Notify successful login - Input/Output
                } else {
                    System.out.println("Login failed! Please try again."); // Notify failed login - Input/Output
                }
            } else {
                displayMenu(sc); // Display the main menu if logged in 
            }
        }
    }

    // Display a decorative welcome message
    public static void displayWelcomeMessage() {
        System.out.println(" /$$      /$$           /$$                                                     /$$                       /$$     /$$                                            /$$$$$$          /$$      /$$                     /$$                ");
        System.out.println("| $$  /$ | $$          | $$                                                    | $$                      | $$    | $$                                           /$$__  $$        | $$$    /$$$                    | $$                ");
        System.out.println("| $$ /$$$| $$  /$$$$$$ | $$  /$$$$$$$  /$$$$$$  /$$$$$$/$$$$   /$$$$$$        /$$$$$$    /$$$$$$        /$$$$$$  | $$$$$$$   /$$$$$$         /$$$$$$$  /$$$$$$ | $$  \\__//$$$$$$ | $$$$  /$$$$  /$$$$$$   /$$$$$$$| $$$$$$$   /$$$$$$ ");
        System.out.println("| $$/$$ $$ $$ /$$__  $$| $$ /$$_____/ /$$__  $$| $$_  $$_  $$ /$$__  $$      |_  $$_/   /$$__  $$      |_  $$_/  | $$__  $$ /$$__  $$       /$$_____/ |____  $$| $$$$   /$$__  $$| $$ $$/$$ $$ /$$__  $$ /$$_____/| $$__  $$ |____  $$");
        System.out.println("| $$$$_  $$$$| $$$$$$$$| $$| $$      | $$  \\ $$| $$ \\ $$ \\ $$| $$$$$$$$        | $$    | $$  \\ $$        | $$    | $$  \\ $$| $$$$$$$$      | $$        /$$$$$$$| $$_/  | $$$$$$$$| $$  $$$| $$| $$  \\ $$| $$      | $$  \\ $$  /$$$$$$$");
        System.out.println("| $$$/ \\  $$$| $$_____/| $$| $$      | $$  | $$| $$ | $$ | $$| $$_____/        | $$ /$$| $$  | $$        | $$ /$$| $$  | $$| $$_____/      | $$       /$$__  $$| $$    | $$_____/| $$\\  $ | $$| $$  | $$| $$      | $$  | $$ /$$__  $$");
        System.out.println("| $$/   \\  $$|  $$$$$$$| $$|  $$$$$$$|  $$$$$$/| $$ | $$ | $$|  $$$$$$$        |  $$$$/|  $$$$$$/        |  $$$$/| $$  | $$|  $$$$$$$      |  $$$$$$$|  $$$$$$$| $$    |  $$$$$$$| $$ \\/  | $$|  $$$$$$/|  $$$$$$$| $$  | $$|  $$$$$$$");
        System.out.println("|__/     \\__/ \\_______/|__/ \\_______/ \\______/ |__/ |__/ |__/ \\_______/         \\___/   \\______/          \\___/  |__/  |__/ \\_______/       \\_______/ \\_______/|__/     \\_______/|__/     |__/ \\______/  \\_______/|__/  |__/ \\_______/");
        System.out.println();
        System.out.println();
        System.out.println("Welcome to Café Mocha!");
        System.out.println();
    }

    // Handles user login by validating credentials from a file - File Handling

    public static boolean login() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Username: ");
        String username = sc.nextLine(); // Read username - Input/Output
        System.out.print("Enter Password: ");
        String password = sc.nextLine(); // Read password - Input/Output

        // Check credentials against saved user data - Data Parsing
        try (BufferedReader br = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] userDetails = line.split(",");
                // Compare input with stored username and password
                if (userDetails.length == 2 && userDetails[0].equals(username) && userDetails[1].equals(password)) {
                    return true; // Return true if credentials match - Boolean Logic
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading users file."); // Handle file read errors - Exception Handling
        }
        return false; // Return false if login fails - Boolean Logic
    }

    // Display the main menu with various functionalities - Control Structures
    public static void displayMenu(Scanner sc) {
        while (true) {
            // List of options available for the user
            System.out.println("\n1. Add Customer Order\n2. Display Order Details\n3. Calculate Bill\n4. Add Menu Item\n5. Update Order\n6. Delete Order\n7. Help\n8. Logout\n9. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume the newline character

            // Execute actions based on user's choice
            switch (choice) {
                case 1 -> addCustomerOrder(); // Add a new order - File Handling
                case 2 -> displayOrderDetails(); // Display all orders - File Handling
                case 3 -> calculateBill(); // Calculate the bill for an order - Data Parsing
                case 4 -> addMenuItem(); // Add a new item to the menu - File Handling
                case 5 -> updateOrder(); // Update an existing order - File Handling
                case 6 -> deleteOrder(); // Delete an order - File Handling
                case 7 -> help(); // Show the help/manual - Input/Output
                case 8 -> {
                    System.out.println("You have successfully logged out."); // Logout - Boolean Logic
                    return; // Exit the method to go back to login
                }
                case 9 -> {
                    System.out.println("Exiting the system. Goodbye!"); // Exit the application - Control Structures
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice. Please try again."); // Handle invalid choices
            }
        }
    }

    // Add a new customer order by taking input and saving it to a file
    public static void addCustomerOrder() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Order Number: ");
        String orderNumber = sc.nextLine(); // Input for order number
        System.out.print("Enter Customer Name: ");
        String customerName = sc.nextLine(); // Input for customer name
        System.out.print("Enter Customer Address: ");
        String customerAddress = sc.nextLine(); // Input for customer address
        System.out.print("Enter Customer Telephone: ");
        String customerTelephone = sc.nextLine(); // Input for customer telephone
        System.out.print("Enter Order Details (Format: ItemName:Quantity;ItemName:Quantity): ");
        String orderDetails = sc.nextLine(); // Input for order details

        // Save the order details to a file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ORDERS_FILE, true))) {
            bw.write(orderNumber + "," + customerName + "," + customerAddress + "," + customerTelephone + "," + orderDetails);
            bw.newLine(); // Add a new line after each order
            System.out.println("Order added successfully!");
        } catch (IOException e) {
            System.out.println("Error writing to orders file."); // Handle file write errors
        }
    }

    // Display details of all customer orders
    public static void displayOrderDetails() {
        try (BufferedReader br = new BufferedReader(new FileReader(ORDERS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] orderDetails = line.split(",");
                // Check if order details are valid
                if (orderDetails.length >= 5) {
                    System.out.println("Order Number: " + orderDetails[0]);
                    System.out.println("Customer Name: " + orderDetails[1]);
                    System.out.println("Customer Address: " + orderDetails[2]);
                    System.out.println("Customer Telephone: " + orderDetails[3]);
                    System.out.println("Order Details: " + orderDetails[4]);
                    System.out.println("------------------------");
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading orders file."); // Handle file read errors
        }
    }

    // Calculate and display the bill for a given order
    public static void calculateBill() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Order Number: ");
        String orderNumber = sc.nextLine(); // Input for order number

        // Attempt to read and calculate the bill
        try (BufferedReader br = new BufferedReader(new FileReader(ORDERS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] orderDetails = line.split(",");
                if (orderDetails.length >= 5 && orderDetails[0].equals(orderNumber)) {
                    double total = 0; // Initialize the total cost
                    String[] items = orderDetails[4].split(";");
                    // Iterate over each item to calculate cost
                    for (String item : items) {
                        String[] parts = item.split(":");
                        if (parts.length == 2) {
                            String itemName = parts[0];
                            int quantity = Integer.parseInt(parts[1]);
                            double price = getPrice(itemName); // Get the price of the item
                            total += price * quantity; // Add to total
                        }
                    }
                    // Save the bill details to a file
                    try (BufferedWriter bw = new BufferedWriter(new FileWriter(BILLS_FILE, true))) {
                        bw.write(orderNumber + "," + total);
                        bw.newLine(); // Add a new line for the next bill
                        System.out.println("Total Bill: $" + total);
                    }
                    return; // Exit after processing the bill
                }
            }
            // Notify if the order is not found
            System.out.println("Order not found.");
        } catch (IOException e) {
            System.out.println("Error processing the bill."); // Handle file errors
        }
    }

    // Get the price of an item from the menu
    public static double getPrice(String itemName) {
        try (BufferedReader br = new BufferedReader(new FileReader(MENU_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] menuDetails = line.split(",");
                if (menuDetails.length == 2 && menuDetails[0].equals(itemName)) {
                    return Double.parseDouble(menuDetails[1]); // Return the price
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading menu file."); // Handle file read errors
        }
        return 0; // Return 0 if item not found
    }

    // Add a new item to the menu with price
    public static void addMenuItem() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Item Name: ");
        String itemName = sc.nextLine(); // Input for item name
        System.out.print("Enter Item Price: ");
        double itemPrice = sc.nextDouble(); // Input for item price

        // Save the new item to the menu
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(MENU_FILE, true))) {
            bw.write(itemName + "," + itemPrice);
            bw.newLine(); // Add a new line for the next item
            System.out.println("Menu item added successfully!");
        } catch (IOException e) {
            System.out.println("Error writing to menu file."); // Handle file write errors
        }
    }

    // Update an existing customer order
    public static void updateOrder() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Order Number to Update: ");
        String orderNumber = sc.nextLine(); // Input for order number to update

        StringBuilder updatedOrders = new StringBuilder(); // StringBuilder to hold updated orders
        boolean orderFound = false; // Track if the order is found

        // Read existing orders and update the specified one
        try (BufferedReader br = new BufferedReader(new FileReader(ORDERS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] orderDetails = line.split(",");
                if (orderDetails.length >= 5 && orderDetails[0].equals(orderNumber)) {
                    orderFound = true;
                    System.out.print("Enter new Order Details (Format: ItemName:Quantity;ItemName:Quantity): ");
                    String newOrderDetails = sc.nextLine(); // Input for new order details
                    updatedOrders.append(orderNumber).append(",").append(orderDetails[1]).append(",").append(orderDetails[2]).append(",").append(orderDetails[3]).append(",").append(newOrderDetails).append("\n");
                    System.out.println("Order updated successfully!");
                } else {
                    updatedOrders.append(line).append("\n"); // Keep the order unchanged
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading orders file."); // Handle file read errors
        }

        // Save the updated list of orders back to the file
        if (orderFound) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(ORDERS_FILE))) {
                bw.write(updatedOrders.toString());
            } catch (IOException e) {
                System.out.println("Error writing to orders file."); // Handle file write errors
            }
        } else {
            System.out.println("Order not found."); // Notify if the order is not found
        }
    }

    // Delete a customer order
    public static void deleteOrder() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Order Number to Delete: ");
        String orderNumber = sc.nextLine(); // Input for order number to delete

        StringBuilder remainingOrders = new StringBuilder(); // StringBuilder to hold remaining orders
        boolean orderFound = false; // Track if the order is found

        // Read existing orders and exclude the specified one
        try (BufferedReader br = new BufferedReader(new FileReader(ORDERS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] orderDetails = line.split(",");
                if (orderDetails.length >= 5 && orderDetails[0].equals(orderNumber)) {
                    orderFound = true;
                    System.out.println("Order deleted successfully!");
                } else {
                    remainingOrders.append(line).append("\n"); // Keep the order unchanged
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading orders file."); // Handle file read errors
        }

        // Save the updated list of orders back to the file
        if (orderFound) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(ORDERS_FILE))) {
                bw.write(remainingOrders.toString());
            } catch (IOException e) {
                System.out.println("Error writing to orders file."); // Handle file write errors
            }
        } else {
            System.out.println("Order not found."); // Notify if the order is not found
        }
    }

    // Display help/manual information
    public static void help() {
        System.out.println("\nHelp - User Manual");
        System.out.println("1. Add Customer Order - Allows you to add a new customer order.");
        System.out.println("2. Display Order Details - Displays all the orders.");
        System.out.println("3. Calculate Bill - Calculates and displays the bill for a specific order.");
        System.out.println("4. Add Menu Item - Adds a new item to the menu.");
        System.out.println("5. Update Order - Updates an existing order's details.");
        System.out.println("6. Delete Order - Deletes an order from the records.");
        System.out.println("7. Help - Displays this help manual.");
        System.out.println("8. Logout - Logs you out of the system.");
        System.out.println("9. Exit - Exits the application.");
    }
}
