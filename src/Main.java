import bean.AccType;
import bean.Account;
import exceptions.InsufficientBalanceException;
import service.impl.CurrentAccount;
import service.impl.PFAccount;
import service.impl.SavingsAccount;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    private static final Map<String, Account> accounts = new HashMap<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("Welcome to Banking Application");

        boolean exit = false;

        while (!exit) {
            showMenu();
            int choice = scanner.nextInt();

            try {
                switch (choice) {
                    case 1 -> createAccount();
                    case 2 -> deposit();
                    case 3 -> withdraw();
                    case 4 -> checkBalance();
                    case 5 -> displayAllAccounts();
                    case 6 -> exit = true;
                    default -> System.out.println("Invalid choice. Try again.");
                }
            } catch (InsufficientBalanceException | IllegalArgumentException ex) {
                System.out.println("Error: " + ex.getMessage());
            } catch (Exception ex) {
                System.out.println("Unexpected error: " + ex.getMessage());
            }
        }

        System.out.println("Thank you for using Banking App!");
        scanner.close();
    }

    //MENU
    private static void showMenu() {
        System.out.print("""
               
                1. Create Account
                2. Deposit Amount
                3. Withdraw Amount
                4. Check Balance
                5. Display All Accounts
                6. Exit
                Enter your choice:
                """);
    }

    //CREATE ACCOUNT
    private static void createAccount() {
        scanner.nextLine();
        System.out.print("Enter Account Holder Name: ");
        String name = scanner.nextLine();
        boolean validName = isValid(name);

        try {
            if(validName){
                System.out.println("Select Account Type:");
                System.out.println("1. Savings");
                System.out.println("2. Current");
                System.out.println("3. PF");

                int typeChoice = scanner.nextInt();

                Account account;


                switch (typeChoice) {
                    case 1 -> {
                        account = new SavingsAccount();
                    }
                    case 2 -> {
                        CurrentAccount ca = new CurrentAccount();
                        System.out.print("Enable Overdraft? (true/false): ");
                        ca.setOdEnabled(scanner.nextBoolean());
                        account = ca;
                    }
                    case 3 -> account = new PFAccount();
                    default -> throw new IllegalArgumentException("Invalid account type");
                }


                String accNo = generateAccountNumber(typeChoice);



                account.setAccNo(accNo);
                account.setAccName(name);
                account.setAccBalance(0);
                account.setAccIsActive(true);

                accounts.put(accNo, account);

                System.out.println("Account created successfully!");
            }
        }
        catch (IllegalStateException ex){
            System.out.println(ex.getMessage());
        }


    }

    //DEPOSIT
    private static void deposit() {

        Account account = getAccount();

        System.out.print("Enter amount to deposit: ");
        double amount = scanner.nextDouble();

        account.deposit(amount);
    }

    //WITHDRAW
    private static void withdraw() {

        Account account = getAccount();

        System.out.print("Enter amount to withdraw: ");
        double amount = scanner.nextDouble();

        account.withdraw(amount);
    }

    //CHECK BALANCE
    private static void checkBalance() {

        Account account = getAccount();
        System.out.println("Current Balance: " + account.checkBalance());
    }

    //DISPLAY ALL
    private static void displayAllAccounts() {

        if (accounts.isEmpty()) {
            System.out.println("No accounts available.");
            return;
        }

        accounts.values().forEach(acc -> {
            System.out.println("--------------------------------");
            System.out.println("Account No   : " + acc.getAccNo());
            System.out.println("Name         : " + acc.getAccName());
            System.out.println("Type         : " + acc.getAccType());
            System.out.println("Balance      : " + acc.getAccBalance());
            System.out.println("Active       : " + acc.isAccIsActive());
        });
    }

    //HELPER
    private static Account getAccount() {

        System.out.print("Enter Account Number: ");
        long accNo = scanner.nextLong();

        Account account = accounts.get(accNo);

        if (account == null || !account.isAccIsActive()) {
            throw new IllegalArgumentException("Invalid or inactive account!");
        }
        return account;
    }

    public static boolean isValid(String name){
        if(name.length() <=2){
            throw new IllegalStateException("Name must contain more than 2 characters");
        }
        return true;
    }

    private static int savingsCounter = 100;
    private static int currentCounter = 500;
    private static int pfCounter = 2000;


    private static String generateAccountNumber(int typeChoice) {

        Random random = new Random();
        String accNo;

        do {
            switch (typeChoice) {
                case 1 ->  // Savings
                        accNo = "SA" + (++savingsCounter);

                case 2 ->  // Current
                        accNo = "CA" + (++currentCounter);

                case 3 ->  // PF
                        accNo = "PF" + (++pfCounter);

                default ->
                        throw new IllegalArgumentException("Invalid account type");
            }
        } while (accounts.containsKey(accNo)); // uniqueness check

        return accNo;
    }


}