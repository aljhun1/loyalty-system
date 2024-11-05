package loyaltyprojectsystem;

import java.util.Scanner;

public class LoyaltyProjectSystem {
    static Scanner scan = new Scanner(System.in);
    static Config conf = new Config();
    static CustomerManagement customer = new CustomerManagement();
    static RewardManagement reward = new RewardManagement();
    static CustomerRewardLogManagement log = new CustomerRewardLogManagement();

    public static void main(String[] args) {
        int option;
        do {
            System.out.println("\n--- Loyalty Project System ---");
            System.out.println("1. Manage Customers");
            System.out.println("2. Manage Rewards");
            System.out.println("3. Manage Reward Logs");
            System.out.println("4. Generate Reports");
            System.out.println("5. Exit");

            System.out.print("Choose an option: ");
            option = scan.nextInt();
            scan.nextLine();

            switch (option) {
                case 1: customer.customerConfig(); break;
                case 2: reward.rewardConfig(); break;
                case 3: log.logConfig(); break;
                case 4: generateReports(); break;
                case 5: System.out.println("Exiting system. Goodbye!"); break;
                default: System.out.println("Invalid option. Please try again.");
            }
        } while (option != 5);
    }

    static void generateReports() {
        System.out.println("\n\t\t--- CUSTOMER REWARD REPORT ---");
        customer.viewCustomers();

        int custId;
        do {
            System.out.print("\nEnter Customer ID for the report: ");
            custId = scan.nextInt();
            if (!conf.doesIDExist("customer", custId)) {
                System.out.println("Customer ID not found. Please try again.");
            }
        } while (!conf.doesIDExist("customer", custId));

        System.out.println("\n===============================");
        System.out.println("        CUSTOMER DETAILS        ");
        System.out.println("===============================");
        System.out.printf("Customer ID  : %d%n", custId);
        System.out.printf("Name         : %s%n", conf.getDataFromID("customer", custId, "name"));
        System.out.printf("Contact Info : %s%n", conf.getDataFromID("customer", custId, "contact_info"));
        System.out.println("-------------------------------");

        String sql = "SELECT log.id, reward.description, reward.points_needed, log.date " +
                     "FROM customer_reward_log log " +
                     "JOIN reward ON log.reward_id = reward.id " +
                     "WHERE log.customer_id = " + custId;

        if (conf.isTableEmpty("customer_reward_log WHERE customer_id = " + custId)) {
            System.out.println("No reward logs found for this customer.");
        } else {
            System.out.println("\nReward Log Details:");
            String[] headers = {"Log ID", "Reward Description", "Points Needed", "Date"};
            String[] columns = {"id", "description", "points_needed", "date"};

            conf.viewRecords(sql, headers, columns);
            System.out.println("\n===============================");
        }
    }
}
