package service.impl;


import bean.AccType;
import bean.Account;
import exceptions.InsufficientBalanceException;

public class SavingsAccount extends Account {

    public SavingsAccount() {
        this.accType = AccType.SAVINGS;
    }

    @Override
    public void deposit(double amount) {
        validateAmount(amount);
        accBalance += amount;
        System.out.println("Savings Deposit Successful. Balance: " + accBalance);
    }

    @Override
    public void withdraw(double amount) {
        validateAmount(amount);

        if (amount > 5000) {
            throw new IllegalArgumentException("Savings account withdrawal limit is 5000");
        }

        if (amount > accBalance) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        accBalance -= amount;
        System.out.println("Savings Withdrawal Successful. Balance: " + accBalance);
    }

    private void validateAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
    }
}
