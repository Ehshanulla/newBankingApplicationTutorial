package service;

public interface AccountService {

    double checkBalance();

    void deposit(double amount) throws IllegalArgumentException;

    void withdraw(double amount) throws IllegalArgumentException;
}

