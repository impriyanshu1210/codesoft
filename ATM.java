import java.util.Scanner;

public class ATM {
    static double balance = 10000; // Initial balance
    static int pin = 1234; // Default PIN

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter ATM PIN: ");
        int enteredPin = sc.nextInt();

        if (enteredPin == pin) {
            int choice;
            do {
                System.out.println("\n--- ATM MENU ---");
                System.out.println("1. Balance Inquiry");
                System.out.println("2. Cash Withdrawal");
                System.out.println("3. Deposit Money");
                System.out.println("4. Change PIN");
                System.out.println("5. Exit");
                System.out.print("Enter choice: ");
                choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        System.out.println("Your Balance: ₹" + balance);
                        break;

                    case 2:
                        System.out.print("Enter withdrawal amount: ");
                        double wAmount = sc.nextDouble();
                        if (wAmount <= balance) {
                            balance -= wAmount;
                            System.out.println("Withdrawal successful! Remaining Balance: ₹" + balance);
                        } else {
                            System.out.println("Insufficient Balance!");
                        }
                        break;

                    case 3:
                        System.out.print("Enter deposit amount: ");
                        double dAmount = sc.nextDouble();
                        balance += dAmount;
                        System.out.println("Deposit successful! New Balance: ₹" + balance);
                        break;

                    case 4:
                        System.out.print("Enter old PIN: ");
                        int oldPin = sc.nextInt();
                        if (oldPin == pin) {
                            System.out.print("Enter new PIN: ");
                            pin = sc.nextInt();
                            System.out.println("PIN changed successfully!");
                        } else {
                            System.out.println("Wrong old PIN!");
                        }
                        break;

                    case 5:
                        System.out.println("Thank you for using ATM!");
                        break;

                    default:
                        System.out.println("Invalid choice!");
                }
            } while (choice != 5);
        } else {
            System.out.println("Incorrect PIN! Access Denied.");
        }
        sc.close();
    }
}

