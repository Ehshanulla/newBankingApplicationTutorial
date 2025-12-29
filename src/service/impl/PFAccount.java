package service.impl;


import bean.AccType;
import bean.Account;

public class PFAccount extends Account {

    public PFAccount() {
        this.accType = AccType.PF;
    }

    @Override
    public void deposit(double amount) {
        validateAmount(amount);
        accBalance += amount;
        System.out.println("PF Deposit Successful. Balance: " + accBalance);
    }

    @Override
    public void withdraw(double amount) {
        validateAmount(amount);

        double maxWithdraw = accBalance * 0.70;

        if (amount > maxWithdraw) {
            throw new IllegalArgumentException(
                    "PF withdrawal allowed only up to 70% of balance"
            );
        }

        accBalance -= amount;
        System.out.println("PF Withdrawal Successful. Balance: " + accBalance);
    }

    private void validateAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
    }
}
