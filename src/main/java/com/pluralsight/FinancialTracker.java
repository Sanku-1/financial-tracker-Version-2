package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class FinancialTracker {

    /* ------------------------------------------------------------------
       Shared data and formatters
       ------------------------------------------------------------------ */
    private static final ArrayList<Transaction> transactions = new ArrayList<>();
    private static final String FILE_NAME = "transactions.csv";

    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String TIME_PATTERN = "HH:mm:ss";
    private static final String DATETIME_PATTERN = DATE_PATTERN + " " + TIME_PATTERN;

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern(DATE_PATTERN);
    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern(TIME_PATTERN);
    private static final DateTimeFormatter DATETIME_FMT = DateTimeFormatter.ofPattern(DATETIME_PATTERN);

    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String RESET = "\u001B[0m";

    /* ------------------------------------------------------------------
       Main menu
       ------------------------------------------------------------------ */
    public static void main(String[] args) {
        loadTransactions(FILE_NAME);

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Welcome to TransactionApp");
            System.out.println("Choose an option:");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment (Debit)");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "D" -> addDeposit(scanner);
                case "P" -> addPayment(scanner);
                case "L" -> ledgerMenu(scanner);
                case "X" -> running = false;
                default -> System.out.println("Invalid option");
            }
        }
        scanner.close();
    }
    /* ------------------------------------------------------------------
       File I/O
       ------------------------------------------------------------------ */
    public static void loadTransactions(String fileName) {
        File file = new File(FILE_NAME);
        try {
            if(!file.exists()) {
                file.createNewFile();
                System.out.println("No File found....Successfullly created a new file: " + FILE_NAME);
            }
        } catch (IOException e) {
            System.err.println("Failed to create a new file");
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
            String line;
            while ((line =reader.readLine()) !=null) {
                String[] tokens = line.split("\\|");
                LocalDate date = LocalDate.parse(tokens[0]);
                LocalTime time = LocalTime.parse(tokens[1]);
                String description = tokens[2];
                String vendor = tokens[3];
                double amount = Double.parseDouble(tokens[4]);

                Transaction transaction = new Transaction(date, time, description, vendor, amount);

                transactions.add(transaction);
            }
            reader.close();

        } catch (IOException e) {
            System.err.println("error reading file: " + FILE_NAME);
        }

        transactions.sort((d1, d2) -> {
            int compareDate = d2.getDate().compareTo(d1.getDate());
            if (compareDate !=0) {
                return compareDate;
            }
            return d2.getTime().compareTo(d1.getTime());
        });
    }

    /* ------------------------------------------------------------------
       Add new transactions
       ------------------------------------------------------------------ */
    private static void addDeposit(Scanner scanner) {
        LocalDate dateFormatted = null;
        LocalTime timeFormatted = null;

        boolean validDate = false;

        while(!validDate) {
            try {
                System.out.println("Date (yyyy-MM-dd):");
                String date = scanner.nextLine();
                dateFormatted = LocalDate.parse(date, DATE_FMT);
                validDate = true;
            } catch (Exception e) {
                System.out.println("invalid date. Please enter the date in this format (yyyy-MM-dd), example: 2012-10-20");
            }
        }
        boolean validTime = false;

        while(!validTime) {
            try {
                System.out.println("Time(HH:mm:ss):");
                String time = scanner.nextLine();
                timeFormatted = LocalTime.parse(time, TIME_FMT);
                validTime = true;
            } catch (Exception e) {
                System.out.println("Invalid time. Please enter the time in this format (HH:mm:ss), example: 08:25:50");
            }
        }
        System.out.println("Description:");
        String description = scanner.nextLine();

        System.out.println("Vendor:");
        String vendor = scanner.nextLine();

        double positiveAmount = 0.0;
        boolean validAmount = false;

        while(!validAmount) {
            try {
                System.out.println("Amount (positive):");
                positiveAmount = Double.parseDouble(scanner.nextLine());
                if (positiveAmount <= 0){
                    System.out.println("Invalid number. Please enter a positive number.");
                } else {
                    validAmount = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number");
            }
        }

        transactions.add(new Transaction(dateFormatted, timeFormatted, description, vendor, positiveAmount));

        try {
        BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true));
        writer.write(dateFormatted.format(DATE_FMT) + "|" + timeFormatted.format(TIME_FMT)+ "|" + description + "|" + vendor + "|" + String.format("%.2f", positiveAmount));
        writer.newLine();
            System.out.println("Deposit recorded!");
        writer.close();
        } catch (IOException e) {
            System.err.print("Error writing to the file: " + FILE_NAME);
        }
        transactions.sort((d1, d2) -> {
            int compareDate = d2.getDate().compareTo(d1.getDate());
            if (compareDate !=0) {
                return compareDate;
            }
            return d2.getTime().compareTo(d1.getTime());
        });
    }

    private static void addPayment(Scanner scanner) {
        LocalDate dateFormatted = null;
        LocalTime timeFormatted = null;

        boolean validDate = false;

        while(!validDate) {
            try {
                System.out.println("Date (yyyy-MM-dd):");
                String date = scanner.nextLine();
                dateFormatted = LocalDate.parse(date, DATE_FMT);
                validDate = true;
            } catch (Exception e) {
                System.out.println("invalid date. Please enter the date in this format (yyyy-MM-dd), example: 2012-10-20");
            }
        }
        boolean validTime = false;

        while(!validTime) {
            try {
                System.out.println("Time(HH:mm:ss):");
                String time = scanner.nextLine();
                timeFormatted = LocalTime.parse(time, TIME_FMT);
                validTime = true;
            } catch (Exception e) {
                System.out.println("Invalid time. Please enter the time in this format (HH:mm:ss), example: 08:25:50");
            }
        }
        System.out.println("Description:");
        String description = scanner.nextLine();

        System.out.println("Vendor:");
        String vendor = scanner.nextLine();

        double positiveAmount = 0.0;
        boolean validAmount = false;

        while(!validAmount) {
            try {
                System.out.println("Amount (positive):");
                positiveAmount = Double.parseDouble(scanner.nextLine());
                if (positiveAmount <= 0){
                    System.out.println("Invalid number. Please enter a positive number.");
                } else {
                    validAmount = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number");
            }
        }

        double negativeAmount = -Math.abs(positiveAmount);
        transactions.add(new Transaction(dateFormatted, timeFormatted, description, vendor, negativeAmount));
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true));
            writer.write(dateFormatted.format(DATE_FMT) + "|" + timeFormatted.format(TIME_FMT)+ "|" + description + "|" + vendor + "|" + String.format("%.2f", negativeAmount));
            writer.newLine();
            System.out.println("Payment recorded!");
            writer.close();
        } catch (IOException e) {
            System.err.print("Error writing to the file: " + FILE_NAME);
        }
        transactions.sort((d1, d2) -> {
            int compareDate = d2.getDate().compareTo(d1.getDate());
            if (compareDate !=0) {
                return compareDate;
            }
            return d2.getTime().compareTo(d1.getTime());
        });
    }
    /* ------------------------------------------------------------------
       Ledger menu
       ------------------------------------------------------------------ */
    private static void ledgerMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Ledger");
            System.out.println("Choose an option:");
            System.out.println("A) All");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "A" -> displayLedger();
                case "D" -> displayDeposits();
                case "P" -> displayPayments();
                case "R" -> reportsMenu(scanner);
                case "H" -> running = false;
                default -> System.out.println("Invalid option");
            }
        }
    }
    /* ------------------------------------------------------------------
       Display helpers: show data in neat columns
       ------------------------------------------------------------------ */
    private static void displayLedger() {
        defaultHeader();
        for (Transaction t: transactions){
            defaultOutput(t);
        }
    }
    private static void displayDeposits() {
        defaultHeader();
        for (Transaction t: transactions) {
            if(t.getAmount() > 0){
                defaultOutput(t);
            }
        }
    }
    private static void displayPayments() {
        defaultHeader();
        for (Transaction t: transactions) {
            if(t.getAmount() < 0){
                defaultOutput(t);
            }
        }
          }
    /* ------------------------------------------------------------------
       Reports menu
       ------------------------------------------------------------------ */
    private static void reportsMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Reports");
            System.out.println("Choose an option:");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("6) Custom Search");
            System.out.println("0) Back");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1" -> {
                    LocalDate start = LocalDate.now().withDayOfMonth(1);
                    LocalDate end = LocalDate.now();
                    filterTransactionsByDate(start, end);
                }
                case "2" -> {
                    LocalDate start = LocalDate.now().minusMonths(1).withDayOfMonth(1);
                    LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
                    filterTransactionsByDate(start, end);
                }
                case "3" -> {
                    LocalDate start = LocalDate.now().withDayOfYear(1);
                    LocalDate end = LocalDate.now();
                    filterTransactionsByDate(start, end);
                }
                case "4" -> {
                    LocalDate start = LocalDate.now().minusYears(1).withDayOfYear(1);
                    LocalDate end = start.withDayOfYear(start.lengthOfYear());
                    filterTransactionsByDate(start, end);
                }
                case "5" -> {
                    System.out.println("Vendor: ");
                    String vendor = scanner.nextLine();
                    filterTransactionsByVendor(vendor);
                }
                case "6" -> customSearch(scanner);
                case "0" -> running = false;
                default -> System.out.println("Invalid option");
            }
        }
    }

    /* ------------------------------------------------------------------
       Reporting helpers
       ------------------------------------------------------------------ */
    private static void filterTransactionsByDate(LocalDate start, LocalDate end) {
        defaultHeader();
        boolean isFound = false;
        for (Transaction t: transactions) {
            LocalDate userDate = t.getDate();
            if((userDate.isEqual(start) || userDate.isAfter(start)) &&
                    (userDate.isEqual(end) || userDate.isBefore(end))){
                defaultOutput(t);
                isFound = true;
            }
        }
        if(!isFound){
            System.out.println("There are no transaction found between " + start + " and " + end);
        }
    }

    private static void filterTransactionsByVendor(String vendor) {
        defaultHeader();
        boolean isFound = false;
        for (Transaction t: transactions) {
            if(( vendor.equalsIgnoreCase(t.getVendor()))){
                defaultOutput(t);
                isFound = true;
            }
        }
        if(!isFound){
            System.out.println("There are no transaction found with " + vendor);
        }
    }

    private static void customSearch(Scanner scanner) {
        System.out.print("Enter start date (yyyy-MM-dd, blank = none):");
        String startDate = scanner.nextLine();

        System.out.print("Enter end date (yyyy-MM-dd, blank = none):");
        String endDate = scanner.nextLine();

        System.out.print("Description:");
        String description = scanner.nextLine();

        System.out.print("Vendor:");
        String vendor = scanner.nextLine();

        System.out.print("Amount (positive):");
        String amount = scanner.nextLine();

        LocalDate formattedStartDate = parseDate(startDate, scanner);
        LocalDate formattedEndDate = parseDate(endDate, scanner);
        Double parsedAmount = parseDouble(amount);

       defaultHeader();

        for( Transaction t: transactions) {
            boolean found = false;
            if(formattedStartDate != null && !t.getDate().isBefore(formattedStartDate)){
                found = true;
            }
            if(formattedEndDate != null && !t.getDate().isAfter(formattedEndDate)){
                found = true;
            }
            if(!description .isEmpty() && t.getDescription().toLowerCase().contains(description.toLowerCase())){
                found = true;
            }
            if(!vendor .isEmpty() && t.getVendor().toLowerCase().contains(vendor.toLowerCase())) {
                found = true;
            }
            if(parsedAmount != null && Double.compare(t.getAmount(), parsedAmount) ==0){
                found = true;
            }

            if(found) {
              defaultOutput(t);
            }
        }
    }
    /* ------------------------------------------------------------------
       Utility parsers (you can reuse in many places)
       ------------------------------------------------------------------ */
    private static LocalDate parseDate(String s, Scanner scanner) {
        while (true) {
        if (s == null || s.isEmpty()) {
            return null;
        }
            try {
                return LocalDate.parse(s, DATE_FMT);
            } catch (Exception e) {
                System.out.print("invalid date. Please enter the date in this format (yyyy-MM-dd, xample: 2012-10-20:): ");
                s = scanner.nextLine();
                if(s.isEmpty()) {
                    return null;
                }
            }
        }
    }
    private static Double parseDouble(String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid positive number: ");
                return null;
            }
    }

    private static String amountColor(double amount) {
        if(amount >= 0) {
            return GREEN + String.format("%.2f", amount) + RESET;
        }
        return RED + String.format("%.2f", amount) + RESET;
    }

    private static void defaultHeader(){
        System.out.printf("%-12s %-10s %-20s %-15s %10s%n", "Date", "Time", "Description", "Vendor", "Amount");
        System.out.println("=======================================================================");
    }
    private static void defaultOutput(Transaction t){
        System.out.printf("%-12s %-10s %-20s %-15s %10s%n",
                t.getDate().format(DATE_FMT),
                t.getTime().format(TIME_FMT),
                t.getDescription(),
                t.getVendor(),
                amountColor(t.getAmount()));
    }
}
