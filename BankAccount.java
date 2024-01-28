/**

 * File: BankAccount.java

 * Author: Lisart Mella

 * Date: 11/27/2023

 */

import javax.swing.*;
import java.util.Vector;

//enum used to differ account types
enum AccountType{
    CHECKINGS,
    SAVINGS
}

public abstract class BankAccount{
    private String name;
    private int accountNumber;
    private int pin;
    private AccountType accountType;
    private double amount;
    protected static Vector<BankAccount> bankAccounts = new Vector<>(); //static so multiple bank accounts can be saved


    //constructors
    public BankAccount(){

    }

    public BankAccount(String name, int accountNumber, int pin, double amount){
        setName(name);
        setAccountNumber(accountNumber);
        setPin(pin);
        setAmount(amount);
    }

    //getters and setters
    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        if (accountNumber >= 100000000 || accountNumber < 10000000){
            JOptionPane.showMessageDialog(null, "The provided account number does not contain 8 digits. Account number saved as 10000000.");
            accountNumber = 10000000;
        }
        this.accountNumber = accountNumber;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        if (pin < 1000 || pin >= 10000){
            JOptionPane.showMessageDialog(null, "The provided pin does not have 4 digits. Pin saved as 1000.");
            pin = 1000;
        }
        this.pin = pin;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        if (amount < 0){
            this.amount = 0;
        }
        this.amount = amount;
    }

    //used to check if the given bank account number already exists in the BankAccount vector
    protected BankAccount CheckAccountNumber(int accountNumber) {
        for (BankAccount account : bankAccounts) {
            if (account.getAccountNumber() == accountNumber) {
                return account;
            }
        }
        return null;
    }

    //the abstract methods that will be overriden by child classes
    public abstract void CreateAccount();
    public abstract void Transfer();
    public abstract void Withdraw(double amount);
    public abstract void Deposit(double amount);
    public abstract void DisplayRecord();
    public abstract void WithdrawMoney();
    public abstract void DepositMoney();

}
