import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class TaxAuditSystem {

    // ArrayLists to store all data (simulating a database)
    private static ArrayList<Taxpayer> taxpayers = new ArrayList<>();
    private static ArrayList<TaxRecord> taxRecords = new ArrayList<>();
    private static ArrayList<AuditLog> auditLogs = new ArrayList<>();

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("   Welcome to the Tax Auditing System     ");
        System.out.println("==========================================");

        int keepRunning = 1;
        while (keepRunning == 1) {
            printMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1 -> addTaxpayer();
                case 2 -> viewAllTaxpayers();
                case 3 -> addTaxRecord();
                case 4 -> viewTaxRecords();
                case 5 -> runAudit();
                case 6 -> viewAuditLogs();
                case 7 -> {
                    System.out.println("\nThank you for using the Tax Auditing System. Goodbye!");
                    keepRunning = 0;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    // ─── MENU ────────────────────────────────────────────────────────────────

    private static void printMenu() {
        System.out.println("\n==================== MENU ====================");
        System.out.println(" 1. Add Taxpayer");
        System.out.println(" 2. View All Taxpayers");
        System.out.println(" 3. Add Tax Record");
        System.out.println(" 4. View Tax Records for a Taxpayer");
        System.out.println(" 5. Run Audit on a Taxpayer");
        System.out.println(" 6. View All Audit Logs");
        System.out.println(" 7. Exit");
        System.out.println("==============================================");
    }

    // ─── 1. ADD TAXPAYER ─────────────────────────────────────────────────────

    private static void addTaxpayer() {
        System.out.println("\n--- Add New Taxpayer ---");
        System.out.print("Enter Taxpayer ID: ");
        String id = scanner.nextLine().trim();

        // Check for duplicate ID
        if (findTaxpayer(id) != null) {
            System.out.println("Error: A taxpayer with ID " + id + " already exists.");
            return;
        }

        System.out.print("Enter Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter Address: ");
        String address = scanner.nextLine().trim();
        System.out.print("Enter Phone Number: ");
        String phone = scanner.nextLine().trim();

        taxpayers.add(new Taxpayer(id, name, address, phone));
        System.out.println("Taxpayer added successfully!");
    }

    // ─── 2. VIEW ALL TAXPAYERS ───────────────────────────────────────────────

    private static void viewAllTaxpayers() {
        System.out.println("\n--- All Taxpayers ---");
        if (taxpayers.isEmpty()) {
            System.out.println("No taxpayers found.");
            return;
        }
        for (int i = 0; i < taxpayers.size(); i++) {
            System.out.println((i + 1) + ". " + taxpayers.get(i));
        }
    }

    // ─── 3. ADD TAX RECORD ───────────────────────────────────────────────────

    private static void addTaxRecord() {
        System.out.println("\n--- Add Tax Record ---");
        System.out.print("Enter Taxpayer ID: ");
        String id = scanner.nextLine().trim();

        if (findTaxpayer(id) == null) {
            System.out.println("Error: Taxpayer with ID " + id + " not found.");
            return;
        }

        int year = getIntInput("Enter Tax Year (e.g. 2023): ");
        double income = getDoubleInput("Enter Annual Income ($): ");
        double taxesPaid = getDoubleInput("Enter Taxes Paid ($): ");

        taxRecords.add(new TaxRecord(id, year, income, taxesPaid));
        System.out.println("Tax record added successfully!");
    }

    // ─── 4. VIEW TAX RECORDS ─────────────────────────────────────────────────

    private static void viewTaxRecords() {
        System.out.println("\n--- View Tax Records ---");
        System.out.print("Enter Taxpayer ID: ");
        String id = scanner.nextLine().trim();

        Taxpayer tp = findTaxpayer(id);
        if (tp == null) {
            System.out.println("Error: Taxpayer with ID " + id + " not found.");
            return;
        }

        System.out.println("Tax records for: " + tp.getName());
        int count = 0; 
        for (TaxRecord record : taxRecords) {
            if (record.getTaxpayerID().equals(id)) {
                System.out.println("  " + record);
                count++;
            }
        }
        if (count == 0) System.out.println("No tax records found for this taxpayer.");
    }

    // ─── 5. RUN AUDIT ────────────────────────────────────────────────────────

    private static void runAudit() {
        System.out.println("\n--- Run Audit ---");
        System.out.print("Enter Taxpayer ID: ");
        String id = scanner.nextLine().trim();

        Taxpayer tp = findTaxpayer(id);
        if (tp == null) {
            System.out.println("Error: Taxpayer with ID " + id + " not found.");
            return;
        }

        int year = getIntInput("Enter the tax year to audit: ");
        TaxRecord record = findTaxRecord(id, year);

        if (record == null) {
            System.out.println("Error: No tax record found for taxpayer " + id + " in year " + year + ".");
            return;
        }

        double expectedTax = calculateExpectedTax(record.getIncome());
        String today = LocalDate.now().toString();

        AuditLog log = new AuditLog(id, year, record.getIncome(),
                                    record.getTaxesPaid(), expectedTax, today);
        auditLogs.add(log);

        System.out.println("\nAudit complete for: " + tp.getName());
        System.out.println(log);
    }

    // ─── 6. VIEW AUDIT LOGS ──────────────────────────────────────────────────

    private static void viewAuditLogs() {
        System.out.println("\n--- All Audit Logs ---");
        if (auditLogs.isEmpty()) {
            System.out.println("No audit logs found.");
            return;
        }
        for (AuditLog log : auditLogs) {
            System.out.println(log);
            System.out.println("----------------------------------------------");
        }
    }

    // ─── TAX BRACKET CALCULATION ─────────────────────────────────────────────
    // Based on simplified US 2023 federal tax brackets

    private static double calculateExpectedTax(double income) {
        double tax = 0;

        if (income <= 11000) {
            tax = income * 0.10;
        } else if (income <= 44725) {
            tax = 1100 + (income - 11000) * 0.12;
        } else if (income <= 95375) {
            tax = 5147 + (income - 44725) * 0.22;
        } else if (income <= 182050) {
            tax = 16290 + (income - 95375) * 0.24;
        } else {
            tax = 37104 + (income - 182050) * 0.32;
        }

        return tax;
    }

    // ─── HELPER METHODS ──────────────────────────────────────────────────────

    private static Taxpayer findTaxpayer(String id) {
        for (Taxpayer tp : taxpayers) {
            if (tp.getTaxpayerID().equals(id)) return tp;
        }
        return null;
    }

    private static TaxRecord findTaxRecord(String id, int year) {
        for (TaxRecord record : taxRecords) {
            if (record.getTaxpayerID().equals(id) && record.getYear() == year) return record;
        }
        return null;
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }

    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = Double.parseDouble(scanner.nextLine().trim());
                if (value < 0) {
                    System.out.println("Value cannot be negative.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
}
