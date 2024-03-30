import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static Map<String, String> usersData = new HashMap<>();
    private static int maxAttempts = 3;
    private static long lockoutTime = 24 * 60 * 60; // 24 hours in seconds

    static {
        usersData.put("sameer", "abid123");
        usersData.put("saleem", "saleem123");
        usersData.put("ismail", "ismasameeil123");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String cUsername = scanner.next().toLowerCase();

        if (usersData.containsKey(cUsername)) {
            System.out.print("Enter password: ");
            String cPassword = scanner.next();
            if (authenticate(cUsername, cPassword)) {
                performTransaction(cUsername);
            } else {
                handleInvalidLogin(cUsername);
            }
        } else {
            System.out.println("Invalid username");
        }
    }

    private static boolean authenticate(String username, String password) {
        return usersData.getOrDefault(username, "").equals(password);
    }

    private static void performTransaction(String username) {
        int amount = 50000;
        Scanner scanner = new Scanner(System.in);
        int option = 0;

        while (option != 4) {
            System.out.println("\n1. Deposit\n2. Withdraw\n3. Mini Statement\n4. Exit");
            System.out.print("Select your option: ");
            option = scanner.nextInt();

            switch (option) {
                case 1:
                    System.out.print("Enter the amount: ");
                    int dep = scanner.nextInt();
                    amount += dep;
                    System.out.println("Total amount: " + amount);
                    break;
                case 2:
                    System.out.print("Enter the amount: ");
                    int withdraw = scanner.nextInt();
                    amount -= withdraw;
                    if (amount<0) {
                        System.out.print("insufficient balance");
                    } else{
                    System.out.println("Total balance amount: " + amount);
                    }
                    break;
                case 3:
                    System.out.println("======== ATM ========");
                    System.out.println("Username: " + username);
                    System.out.println("Total amount: " + amount);
                    System.out.println("Thanks for visiting");
                    System.out.println("=====================");
                    break;
                case 4:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option");
            }
        }
    }

    private static void handleInvalidLogin(String username) {
        Scanner scanner = new Scanner(System.in);
        int n = 1;
        long lastAttemptTime = System.currentTimeMillis() / 1000; // Convert milliseconds to seconds

        while (n <= maxAttempts) {
            System.out.print("Invalid password. Enter password: ");
            String cPassword = scanner.next();
            if (authenticate(username, cPassword)) {
                System.out.println("Login successful.");
                return;
            } else {
                System.out.println("Invalid password. Please try again.");
                n++;
            }
        }

        long currentTime = System.currentTimeMillis() / 1000; // Convert milliseconds to seconds
        long remainingTime = Math.max(0, lastAttemptTime + lockoutTime - currentTime);

        if (remainingTime > 0) {
            System.out.println("Too many failed attempts. Try again after " + formatTime(remainingTime));
        } else {
            System.out.println("Please try after 24 hours");
        }
    }

    private static String formatTime(long seconds) {
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long secs = seconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, secs);
    }
}

