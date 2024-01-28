/**

 * File: SavingsAccount.java

 * Author: Lisart Mella

 * Date: 11/27/2023

 */


import javax.swing.*;
import java.util.List;
import java.util.Scanner;
public class SavingsAccount extends BankAccount {

    SavingsAccount() {

    }

    SavingsAccount(String name, int accountNumber, int pin, double amount) {
        super(name, accountNumber, pin, amount);
        setAccountType(AccountType.SAVINGS);
    }

    @Override
    public void CreateAccount() {
        String name = JOptionPane.showInputDialog("Please enter your name:");

        for (int i = 0; i < name.length(); i++) {
            char currentChar = name.charAt(i);
            if (!Character.isLetter(currentChar) && !Character.isWhitespace(currentChar)) {

                JOptionPane.showMessageDialog(null, "ERROR. You cannot enter a null number nor non-alphabetic characters.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        String accountNumberStr = JOptionPane.showInputDialog("Please enter an 8-digit account number:");
        int accountNumber = 0;
        try{
            accountNumber = Math.max(0, Integer.parseInt(accountNumberStr));
        }
        catch(Exception exception){
            JOptionPane.showMessageDialog(null, "ERROR. The provided input is not a number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //check whether the bank account exists already
        BankAccount newAccount = CheckAccountNumber(accountNumber);
        if (newAccount != null) {
            JOptionPane.showMessageDialog(null, "Account with the provided account number already exists. Create account aborted.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String pinStr = JOptionPane.showInputDialog("Please create a 4-digit PIN:");
        int pin = Integer.parseInt(pinStr);

        try{
            pin = Math.max(0, Integer.parseInt(pinStr));
        }
        catch(Exception exception){
            JOptionPane.showMessageDialog(null, "ERROR. The provided input is not a number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String amountStr = JOptionPane.showInputDialog("Please enter the initial balance amount (If negative, balance will be set to 0):");
        double amount = 0;
        try{
            amount = Math.max(0, Double.parseDouble(amountStr));
        }
        catch(Exception exception){
            JOptionPane.showMessageDialog(null, "ERROR. The provided input is not a number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }



        SavingsAccount savingsAccount = new SavingsAccount(name, accountNumber, pin, amount);

        bankAccounts.add(savingsAccount);//adding the given account information to bankAccounts vector to use it later
    }

    @Override
    public void Transfer() {

        String senderAccountNumberStr = JOptionPane.showInputDialog("Enter your account number:");
        int senderAccountNumber = Integer.parseInt(senderAccountNumberStr);


        String senderPinStr = JOptionPane.showInputDialog("Enter your PIN:");
        int senderPin = Integer.parseInt(senderPinStr);

        // Find the sender account in the bankAccounts vector
        BankAccount senderAccount = CheckAccountNumber(senderAccountNumber);

        if (senderAccount == null || senderAccount.getPin() != senderPin) {
            JOptionPane.showMessageDialog(null, "Invalid account number or PIN. Transfer aborted.");
            return;
        }



        String recipientAccountNumberStr = JOptionPane.showInputDialog("Enter the recipient's account number:");
        int recipientAccountNumber = Integer.parseInt(recipientAccountNumberStr);

        // Find the recipient account in the bankAccounts vector
        BankAccount recipientAccount = CheckAccountNumber(recipientAccountNumber);

        if (recipientAccount == null) {
            JOptionPane.showMessageDialog(null, "Recipient account does not exist. Transfer aborted.");
            return;
        }

        if (recipientAccountNumber == senderAccountNumber){
            JOptionPane.showMessageDialog(null, "You cannot transfer money to the same account. Transfer aborted.");
            return;
        }
        double amountToTransfer = Double.parseDouble(JOptionPane.showInputDialog("Enter amount to transfer:"));
        //withdraw the money from the sender and deposit it to the recipient
        senderAccount.Withdraw(amountToTransfer);
        recipientAccount.Deposit(amountToTransfer);

        JOptionPane.showMessageDialog(null, "Transfer successful.");
    }



    @Override
    public void Withdraw(double amount) {
        if (amount > getAmount()) {
            JOptionPane.showMessageDialog(null, "Error: Insufficient funds. Withdrawal aborted.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            double balance = getAmount() - amount;
            setAmount(balance);
        }
    }

    @Override
    public void Deposit(double amount){

        double balance = getAmount() + amount;

        setAmount(balance);

    }

    @Override
    public void DisplayRecord() {
        String accountNumberStr = JOptionPane.showInputDialog("Enter your account number:");
        int enteredAccountNumber = Integer.parseInt(accountNumberStr);

        String PinStr = JOptionPane.showInputDialog("Enter your PIN:");
        int enteredPin = Integer.parseInt(PinStr);

        //checks whether the account exists. if not returns with error
        BankAccount foundAccount = CheckAccountNumber(enteredAccountNumber);

        if (foundAccount == null || foundAccount.getPin() != enteredPin) {
            JOptionPane.showMessageDialog(null, "Invalid account number or PIN. Display aborted.");
            return;
        }

        if (foundAccount instanceof SavingsAccount) {
            String message = String.format("""
            Account Type: SAVINGS
            Name: %s
            Account Number: %d
            Balance: $%.2f""",
                    foundAccount.getName(),
                    foundAccount.getAccountNumber(),
                    foundAccount.getAmount()
            );

            JOptionPane.showMessageDialog(null, message, "Account Information", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Handle the case where foundAccount is not a Savings Account
            JOptionPane.showMessageDialog(null, "Invalid account type for displaying records.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    @Override
    public void WithdrawMoney(){
        String accountNumberStr = JOptionPane.showInputDialog("Enter your account number:");
        int enteredAccountNumber =  Integer.parseInt(accountNumberStr);

        String PinStr = JOptionPane.showInputDialog("Enter your PIN:");
        int enteredPin = Integer.parseInt(PinStr);


        BankAccount foundAccount = CheckAccountNumber(enteredAccountNumber);

        if (foundAccount == null || foundAccount.getPin() != enteredPin) {
            JOptionPane.showMessageDialog(null, "Invalid account number or PIN. Display aborted.");
            return;
        }

        double amount = 0;

        String enteredAmount = JOptionPane.showInputDialog("Enter the amount you want to withdraw:");
        amount = Double.parseDouble(enteredAmount);

        foundAccount.Withdraw(amount);
    }

    @Override
    public void DepositMoney(){
        String accountNumberStr = JOptionPane.showInputDialog("Enter your account number:");

        int enteredAccountNumber = Integer.parseInt(accountNumberStr);


        String PinStr = JOptionPane.showInputDialog("Enter your PIN:");
        int enteredPin = Integer.parseInt(PinStr);


        BankAccount foundAccount = CheckAccountNumber(enteredAccountNumber);

        if (foundAccount == null || foundAccount.getPin() != enteredPin) {
            JOptionPane.showMessageDialog(null, "Invalid account number or PIN. Deposit aborted.");
            return;
        }
        double amount = 0;


        String enteredAmount = JOptionPane.showInputDialog("Enter the amount you want to deposit:");
        amount = Double.parseDouble(enteredAmount);

        foundAccount.Deposit(amount);
    }

}
