package com.pluralsight.shop;

import com.pluralsight.Transaction;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class GUI extends JFrame {
    private Transaction transaction;
    private JTextArea ledger;
    private JLabel totalLabel;

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
    }



}

