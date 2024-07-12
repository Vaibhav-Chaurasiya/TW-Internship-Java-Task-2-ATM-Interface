import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Account {
    private int accountNumber;
    private int pin;
    private double balance;

    public Account(int accountNumber, int pin, double initialBalance) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = initialBalance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public boolean validatePin(int inputPin) {
        return pin == inputPin;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            return true;
        } else {
            return false;
        }
    }

    public void transfer(Account targetAccount, double amount) {
        if (this.withdraw(amount)) {
            targetAccount.deposit(amount);
        }
    }
}

class ATM {
    private Map<Integer, Account> accounts;
    private Scanner scanner;

    public ATM() {
        accounts = new HashMap<>();
        scanner = new Scanner(System.in);
        initializeAccounts();
    }

    private void initializeAccounts() {
        // Adding some dummy accounts
        accounts.put(123456, new Account(123456, 1234, 5000.0));
        accounts.put(654321, new Account(654321, 4321, 3000.0));
    }

    public void start() {
        System.out.println("Welcome to the ATM");
        while (true) {
            System.out.print("Enter your account number: ");
            int accountNumber = scanner.nextInt();
            System.out.print("Enter your PIN: ");
            int pin = scanner.nextInt();

            Account account = accounts.get(accountNumber);
            if (account != null && account.validatePin(pin)) {
                showMenu(account);
            } else {
                System.out.println("Invalid account number or PIN.");
            }
        }
    }

    private void showMenu(Account account) {
        while (true) {
            System.out.println("\nATM Menu:");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Transfer Funds");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    checkBalance(account);
                    break;
                case 2:
                    depositMoney(account);
                    break;
                case 3:
                    withdrawMoney(account);
                    break;
                case 4:
                    transferFunds(account);
                    break;
                case 5:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void checkBalance(Account account) {
        System.out.println("Your balance is: $" + account.getBalance());
    }

    private void depositMoney(Account account) {
        System.out.print("Enter amount to deposit: ");
        double amount = scanner.nextDouble();
        account.deposit(amount);
        System.out.println("Deposit successful. Your new balance is: $" + account.getBalance());
    }

    private void withdrawMoney(Account account) {
        System.out.print("Enter amount to withdraw: ");
        double amount = scanner.nextDouble();
        if (account.withdraw(amount)) {
            System.out.println("Withdrawal successful. Your new balance is: $" + account.getBalance());
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    private void transferFunds(Account account) {
        System.out.print("Enter target account number: ");
        int targetAccountNumber = scanner.nextInt();
        Account targetAccount = accounts.get(targetAccountNumber);

        if (targetAccount != null) {
            System.out.print("Enter amount to transfer: ");
            double amount = scanner.nextDouble();
            if (account.getBalance() >= amount) {
                account.transfer(targetAccount, amount);
                System.out.println("Transfer successful. Your new balance is: $" + account.getBalance());
            } else {
                System.out.println("Insufficient balance.");
            }
        } else {
            System.out.println("Target account not found.");
        }
    }
}

public class AtmInterface {
    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.start();
    }
}
