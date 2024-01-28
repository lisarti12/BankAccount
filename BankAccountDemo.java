/**

 * File: BankAccountDemo.java

 * Author: Lisart Mella

 * Date: 11/27/2023

 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BankAccountDemo extends JFrame {
    BankAccount currentAccount;

    public static void main(String[] args) {
        // Invokes the creation and display of the GUI on the event dispatching thread
        SwingUtilities.invokeLater(() -> {
            // Creating an instance of BankAccountDemo
            BankAccountDemo mainApp = new BankAccountDemo();

            // Setting the default close operation of the JFrame
            mainApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Setting the size of the JFrame
            mainApp.setSize(400, 300);

            // Setting the title of the JFrame
            mainApp.setTitle("Banking Application");

            // Making the JFrame visible
            mainApp.setVisible(true);
        });
    }

    public BankAccountDemo() {

        // Creating buttons
        JButton createAccountButton = new JButton("Create Account");
        JButton transferButton = new JButton("Transfer");
        JButton withdrawButton = new JButton("Withdraw");
        JButton depositButton = new JButton("Deposit");
        JButton displayRecordButton = new JButton("Display Record");
        JButton exitButton = new JButton("Exit");

        // ActionListener for createAccountButton
        createAccountButton.addActionListener(e -> {
            // Prompting for account type
            String accountTypeStr = JOptionPane.showInputDialog("Please enter account type (SAVINGS or CHECKINGS):");
            AccountType accountType = AccountType.valueOf(accountTypeStr.toUpperCase());

            if (accountType == AccountType.CHECKINGS) {
                currentAccount = new CheckingsAccount();
            } else if (accountType == AccountType.SAVINGS) {
                currentAccount = new SavingsAccount();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid account type. Create account aborted.");
                return;
            }

            currentAccount.CreateAccount();
        });

        // ActionListener for transferButton
        transferButton.addActionListener(e -> {
            if (currentAccount == null) {
                JOptionPane.showMessageDialog(this, "Create an account first.");
                return;
            }
            currentAccount.Transfer();
        });

        // ActionListener for withdrawButton
        withdrawButton.addActionListener(e -> {
            if (currentAccount == null) {
                JOptionPane.showMessageDialog(this, "Create an account first.");
                return;
            }
            currentAccount.WithdrawMoney();
        });

        // ActionListener for depositButton
        depositButton.addActionListener(e -> {
            if (currentAccount == null) {
                JOptionPane.showMessageDialog(this, "Create an account first.");
                return;
            }
            currentAccount.DepositMoney();
        });

        // ActionListener for displayRecordButton
        displayRecordButton.addActionListener(e -> {
            if (currentAccount == null) {
                JOptionPane.showMessageDialog(this, "Create an account first.");
                return;
            }
            currentAccount.DisplayRecord();
        });

        // ActionListener for exitButton
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(BankAccountDemo.this,
                        "Are you sure you want to exit the application?",
                        "Confirm Exit", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        // Creating button panel
        JPanel buttonPanel = new JPanel(new GridLayout(6, 1));
        buttonPanel.add(createAccountButton);
        buttonPanel.add(transferButton);
        buttonPanel.add(withdrawButton);
        buttonPanel.add(depositButton);
        buttonPanel.add(displayRecordButton);
        buttonPanel.add(exitButton);

        // Creating main panel
        JPanel mainPanel = new JPanel(new GridBagLayout());

        mainPanel.add(buttonPanel, new GridBagConstraints());

        // Adding main panel to the frame
        add(mainPanel);
    }
}
