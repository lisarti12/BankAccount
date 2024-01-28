/**

 * File: CheckingsAccount.java

 * Author: Lisart Mella

 * Date: 11/27/2023

 */

import javax.swing.*;

public class CheckingsAccount extends BankAccount {

    //private method that makes the difference between a savings account and a checkings account
    private double monthlySalary;

    //constructors
    CheckingsAccount() {


    }

    CheckingsAccount(String name, int accountNumber, int pin, double amount, double monthlySalary) {
        super(name, accountNumber, pin, amount);
        setMonthlySalary(monthlySalary);
        setAccountType(AccountType.CHECKINGS);
    }

    //getter and setter
    public double getMonthlySalary() {
        return monthlySalary;
    }

    public void setMonthlySalary(double monthlySalary) {
        if(monthlySalary < 0){
            this.monthlySalary = 0;
        }
        this.monthlySalary = monthlySalary;
    }

    //overriden methods
    @Override
    public void CreateAccount() {
        String name = JOptionPane.showInputDialog("Please enter your name:");
        //this for loop checks whether there are non-alphabetic characters in the given string and displays and returns if it does have at least one
        for (int i = 0; i < name.length(); i++) {
            // Get the current character in the loop
            char currentChar = name.charAt(i);
            //used prebuilt methods isLetter(char c) and isWhiteSpace(char c)
            if (!Character.isLetter(currentChar) && !Character.isWhitespace(currentChar)) {
                // Display an error message
                JOptionPane.showMessageDialog(null, "ERROR. Name should only contain alphabetic characters and spaces.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        String enteredAccountNumber = JOptionPane.showInputDialog("Please enter an 8-digit account number:");
        int accountNumber = 0;
        //using try catch to check if the input is in fact a number
        try{
            accountNumber = Math.max(0, Integer.parseInt(enteredAccountNumber));
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
        int pin = 0;

        try{
            pin = Math.max(0, Integer.parseInt(pinStr));
        }
        catch(Exception exception){
            JOptionPane.showMessageDialog(null, "ERROR. The provided input is not a number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String enteredAmount = JOptionPane.showInputDialog("Please enter the initial balance amount (If negative, balance will be set to 0):");
        double amount = 0;
        try{
            amount = Math.max(0, Double.parseDouble(enteredAmount));
        }
        catch(Exception exception){
            JOptionPane.showMessageDialog(null, "ERROR. The provided input is not a number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String enteredSalary = JOptionPane.showInputDialog("Please enter your monthly salary:");
        double monthlySalary = 0;
        try{
            monthlySalary = Double.parseDouble(enteredSalary);
        }
        catch(Exception exception){
            JOptionPane.showMessageDialog(null, "ERROR. The provided input is not a number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        CheckingsAccount checkingsAccount = new CheckingsAccount(name, accountNumber, pin, amount, monthlySalary);

        bankAccounts.add(checkingsAccount);//adding the given account information to bankAccounts vector to use it later
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
        //user is asked for acc number and pin
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



        if (foundAccount instanceof CheckingsAccount) {
            String message = String.format("""
            Account Type: CHECKINGS
            Name: %s
            Account Number: %d
            Monthly Salary: $%.2f
            Balance: $%.2f""",
                    foundAccount.getName(),
                    foundAccount.getAccountNumber(),
                    ((CheckingsAccount) foundAccount).getMonthlySalary(),
                    foundAccount.getAmount()
            );

            JOptionPane.showMessageDialog(null, message, "Account Information", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Handle the case where foundAccount is not a CheckingsAccount
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
