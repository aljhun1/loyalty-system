package loyaltyprojectsystem;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CustomerRewardLogManagement {
    Scanner scan = new Scanner(System.in);
    Config conf = new Config();

    public void logConfig() {
        int option;
        do {
            System.out.println("\n--- Customer Reward Log Management ---");
            System.out.println("1. Add Reward Log");
            System.out.println("2. View Reward Logs");
            System.out.println("3. Edit Reward Log");
            System.out.println("4. Delete Reward Log");
            System.out.println("5. Exit");

            try {
                System.out.print("Choose an option: ");
                option = scan.nextInt();
                scan.nextLine();

                switch (option) {
                    case 1: addLog(); break;
                    case 2: viewLogs(); break;
                    case 3: editLog(); break;
                    case 4: deleteLog(); break;
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

    private void addLog() {
        System.out.println("\nEnter Customer Reward Log Details:");

        int customerId;
        do {
            System.out.print("Customer ID: ");
            customerId = scan.nextInt();
            if (!conf.doesIDExist("customer", customerId)) {
                System.out.println("Customer ID doesn't exist.");
            }
        } while (!conf.doesIDExist("customer", customerId));

        int rewardId;
        do {
            System.out.print("Reward ID: ");
            rewardId = scan.nextInt();
            if (!conf.doesIDExist("reward", rewardId)) {
                System.out.println("Reward ID doesn't exist.");
            }
        } while (!conf.doesIDExist("reward", rewardId));

        scan.nextLine();
        System.out.print("Date (YYYY-MM-DD): ");
        String date = scan.nextLine();

        String sql = "INSERT INTO customer_reward_log (customer_id, reward_id, date) VALUES (?, ?, ?)";
        conf.addRecord(sql, customerId, rewardId, date);
    }

    public void viewLogs() {
        String sql = "SELECT * FROM customer_reward_log";
        String[] headers = {"Log ID", "Customer ID", "Reward ID", "Date"};
        String[] columns = {"id", "customer_id", "reward_id", "date"};
        conf.viewRecords(sql, headers, columns);
    }

    private void editLog() {
        System.out.print("Enter Log ID to edit: ");
        int logId = scan.nextInt();
        scan.nextLine();
        if (conf.doesIDExist("customer_reward_log", logId)) {
            int customerId;
            do {
                System.out.print("New Customer ID: ");
                customerId = scan.nextInt();
                if (!conf.doesIDExist("customer", customerId)) {
                    System.out.println("Customer ID doesn't exist.");
                }
            } while (!conf.doesIDExist("customer", customerId));

            int rewardId;
            do {
                System.out.print("New Reward ID: ");
                rewardId = scan.nextInt();
                if (!conf.doesIDExist("reward", rewardId)) {
                    System.out.println("Reward ID doesn't exist.");
                }
            } while (!conf.doesIDExist("reward", rewardId));

            scan.nextLine();
            System.out.print("New Date (YYYY-MM-DD): ");
            String date = scan.nextLine();

            String sql = "UPDATE customer_reward_log SET customer_id = ?, reward_id = ?, date = ? WHERE id = ?";
            conf.updateRecord(sql, customerId, rewardId, date, logId);
        } else {
            System.out.println("Log ID not found.");
        }
    }

    private void deleteLog() {
        System.out.print("Enter Log ID to delete: ");
        int logId = scan.nextInt();
        scan.nextLine();
        if (conf.doesIDExist("customer_reward_log", logId)) {
            String sql = "DELETE FROM customer_reward_log WHERE id = ?";
            conf.deleteRecord(sql, logId);
        } else {
            System.out.println("Log ID not found.");
        }
    }
}
