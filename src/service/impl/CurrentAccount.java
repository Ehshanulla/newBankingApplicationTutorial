package service.impl;

import bean.Account;
import bean.AccType;
import exceptions.InsufficientBalanceException;

public class CurrentAccount extends Account {

    private boolean odEnabled;

    public CurrentAccount() {
        this.accType = AccType.CURRENT;
    }

    public boolean isOdEnabled() {
        return odEnabled;
    }

    public void setOdEnabled(boolean odEnabled) {
        this.odEnabled = odEnabled;
    }

    @Override
    public void deposit(double amount) {
        validateAmount(amount);
        accBalance += amount;
        System.out.println("Current Deposit Successful. Balance: " + accBalance);
    }

    @Override
    public void withdraw(double amount) {
        validateAmount(amount);

        if (amount > 25000) {
            throw new IllegalArgumentException("Current account withdrawal limit is 25000");
        }

        if (!odEnabled && amount > accBalance) {
            throw new InsufficientBalanceException("OD not enabled. Insufficient balance");
        }

        accBalance -= amount;
        System.out.println("Current Withdrawal Successful. Balance: " + accBalance);
    }

    private void validateAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
    }
}
