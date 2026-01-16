package com.pluralsight.shop;

import com.pluralsight.Transaction;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GUI extends JFrame {
    private Transaction transaction;
    private JTextArea ledger;
    private JLabel totalCheckingLabel;
    private JLabel totalSavingLabel;


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
                    GUI newGUI;
                    try {
                        newGUI = new GUI();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    newGUI.setVisible(true);
                }
        );
    }

    public GUI() throws IOException{
        // Setup window
        setTitle("Ledger");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(null);

        createGUI();
    }

    private void createGUI() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel title = new JLabel("test 0", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(title, BorderLayout.CENTER);
        add(topPanel, BorderLayout.PAGE_START);

        ledger = new JTextArea();
        ledger.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(ledger);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(7, 1, 5, 5));

        JButton addDepositButton = new JButton("Add Deposit");
        JButton addPaymentButton = new JButton("Add Payment");
        JButton searchButton = new JButton("Search Transaction");
        totalSavingLabel = new JLabel("Total: $0.00", SwingConstants.CENTER);
        totalSavingLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalCheckingLabel = new JLabel("Total: $0.00", SwingConstants.CENTER);
        totalCheckingLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JButton viewTransactions = new JButton("Transactions");
        JButton addSavingsButton = new JButton("Clear Order");
        JButton addSavingsGoalButton = new JButton("Enter Savings Goal");

        addDepositButton.addActionListener(e -> /*placeholder function*/);
        addPaymentButton.addActionListener(e -> /*placeholder function*/);
        searchButton.addActionListener(e ->/*placeholder function*/);
        viewTransactions.addActionListener(e -> /*placeholder function*/);
        addSavingsButton.addActionListener(e -> /*placeholder function*/);
        addSavingsGoalButton.addActionListener(e -> /*placeholder function*/);

        buttonPanel.add(addDepositButton);
        buttonPanel.add(addPaymentButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(totalSavingLabel);
        buttonPanel.add(totalCheckingLabel);
        buttonPanel.add(viewTransactions);
        buttonPanel.add(addSavingsButton);
        buttonPanel.add(addSavingsGoalButton);


        add(buttonPanel, BorderLayout.LINE_END);
    }





}

