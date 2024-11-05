package loyaltyprojectsystem;

import java.util.InputMismatchException;
import java.util.Scanner;

public class RewardManagement {
    Scanner scan = new Scanner(System.in);
    Config conf = new Config();

    public void rewardConfig() {
        int option;
        do {
            System.out.println("\n--- Reward Management ---");
            System.out.println("1. Add Reward");
            System.out.println("2. View Rewards");
            System.out.println("3. Edit Reward");
            System.out.println("4. Delete Reward");
            System.out.println("5. Exit");

            try {
                System.out.print("Choose an option: ");
                option = scan.nextInt();
                scan.nextLine();

                switch (option) {
                    case 1: addReward(); break;
                    case 2: viewRewards(); break;
                    case 3: editReward(); break;
                    case 4: deleteReward(); break;
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

    private void addReward() {
        System.out.println("\nEnter Reward Details:");
        System.out.print("Description: ");
        String description = scan.nextLine();
        System.out.print("Points Needed: ");
        int pointsNeeded = scan.nextInt();

        String sql = "INSERT INTO reward (description, points_needed) VALUES (?, ?)";
        conf.addRecord(sql, description, pointsNeeded);
    }

    public void viewRewards() {
        String sql = "SELECT * FROM reward";
        String[] headers = {"ID", "Description", "Points Needed"};
        String[] columns = {"id", "description", "points_needed"};
        conf.viewRecords(sql, headers, columns);
    }

    private void editReward() {
        System.out.print("Enter Reward ID to edit: ");
        int rewardId = scan.nextInt();
        scan.nextLine();
        if (conf.doesIDExist("reward", rewardId)) {
            System.out.print("New Description: ");
            String newDescription = scan.nextLine();
            System.out.print("New Points Needed: ");
            int newPointsNeeded = scan.nextInt();

            String sql = "UPDATE reward SET description = ?, points_needed = ? WHERE id = ?";
            conf.updateRecord(sql, newDescription, newPointsNeeded, rewardId);
        } else {
            System.out.println("Reward ID not found.");
        }
    }

    private void deleteReward() {
        System.out.print("Enter Reward ID to delete: ");
        int rewardId = scan.nextInt();
        scan.nextLine();
        if (conf.doesIDExist("reward", rewardId)) {
            String sql = "DELETE FROM reward WHERE id = ?";
            conf.deleteRecord(sql, rewardId);
        } else {
            System.out.println("Reward ID not found.");
        }
    }
}

