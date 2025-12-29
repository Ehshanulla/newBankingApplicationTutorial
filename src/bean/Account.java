package bean;

import service.AccountService;

public abstract class Account implements AccountService {

    protected String accNo;
    protected String accName;
    protected AccType accType;
    protected double accBalance;
    protected boolean accIsActive;

    // Getters and Setters
    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public AccType getAccType() {
        return accType;
    }

    public void setAccType(AccType accType) {
        this.accType = accType;
    }

    public double getAccBalance() {
        return accBalance;
    }

    public void setAccBalance(double accBalance) {
        this.accBalance = accBalance;
    }

    public boolean isAccIsActive() {
        return accIsActive;
    }

    public void setAccIsActive(boolean accIsActive) {
        this.accIsActive = accIsActive;
    }

    // Common method
    @Override
    public double checkBalance() {
        return accBalance;
    }

    // Force child classes to implement rules
    @Override
    public abstract void deposit(double amount);

    @Override
    public abstract void withdraw(double amount);
}
