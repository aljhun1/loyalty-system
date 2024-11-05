package loyaltyprojectsystem;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CustomerManagement {
    Scanner scan = new Scanner(System.in);
    Config conf = new Config();

    public void customerConfig() {
        int option;
        do {
            System.out.println("\n--- Customer Management ---");
            System.out.println("1. Add Customer");
            System.out.println("2. View Customers");
            System.out.println("3. Edit Customer");
            System.out.println("4. Delete Customer");
            System.out.println("5. Exit");

            try {
                System.out.print("Choose an option: ");
                option = scan.nextInt();
                scan.nextLine();

                switch (option) {
                    case 1: addCustomer(); break;
                    case 2: viewCustomers(); break;
                    case 3: editCustomer(); break;
                    case 4: deleteCustomer(); break;
                    case 5: System.out.println("Returning to main menu."); break;
                    default: System.out.println("Invalid option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scan.nextLine();
                option = -1;
            }
        } while (option != 5);
    }

    private void addCustomer() {
        System.out.println("\nEnter Customer Details:");
        System.out.print("Name: ");
        String name = scan.nextLine();
        System.out.print("Contact Info: ");
        String contactInfo = scan.nextLine();

        String sql = "INSERT INTO customer (name, contact_info) VALUES (?, ?)";
        conf.addRecord(sql, name, contactInfo);
    }

    public void viewCustomers() {
        String sql = "SELECT * FROM customer";
        String[] headers = {"ID", "Name", "Contact Info"};
        String[] columns = {"id", "name", "contact_info"};
        conf.viewRecords(sql, headers, columns);
    }

    private void editCustomer() {
        System.out.print("Enter Customer ID to edit: ");
        int customerId = scan.nextInt();
        scan.nextLine();
        if (conf.doesIDExist("customer", customerId)) {
            System.out.print("New Name: ");
            String newName = scan.nextLine();
            System.out.print("New Contact Info: ");
            String newContactInfo = scan.nextLine();

            String sql = "UPDATE customer SET name = ?, contact_info = ? WHERE id = ?";
            conf.updateRecord(sql, newName, newContactInfo, customerId);
        } else {
            System.out.println("Customer ID not found.");
        }
    }

    private void deleteCustomer() {
        System.out.print("Enter Customer ID to delete: ");
        int customerId = scan.nextInt();
        scan.nextLine();
        if (conf.doesIDExist("customer", customerId)) {
            String sql = "DELETE FROM customer WHERE id = ?";
            conf.deleteRecord(sql, customerId);
        } else {
            System.out.println("Customer ID not found.");
        }
    }
}
