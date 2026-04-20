public class AuditLog {
    private String taxpayerID;
    private int year;
    private double income;
    private double taxesPaid;
    private double expectedTax;
    private double discrepancy;
    private String status;
    private String auditDate;

    public AuditLog(String taxpayerID, int year, double income,
                    double taxesPaid, double expectedTax, String auditDate) {
        this.taxpayerID = taxpayerID;
        this.year = year;
        this.income = income;
        this.taxesPaid = taxesPaid;
        this.expectedTax = expectedTax;
        this.discrepancy = taxesPaid - expectedTax;
        // flagged if paid less than 90% of expected
        if (taxesPaid < expectedTax * 0.90) {
            this.status = "FLAGGED FOR REVIEW";
        } else {
            this.status = "PASS";
        }
        this.auditDate = auditDate;
    }

    // Getters
    public String getTaxpayerID() { return taxpayerID; }
    public int getYear() { return year; }
    public String getStatus() { return status; }

    @Override
    public String toString() {
        return "=== Audit Log ===" +
               "\nTaxpayer ID : " + taxpayerID +
               "\nAudit Date  : " + auditDate +
               "\nYear        : " + year +
               "\nIncome      : $" + String.format("%.2f", income) +
               "\nTaxes Paid  : $" + String.format("%.2f", taxesPaid) +
               "\nExpected Tax: $" + String.format("%.2f", expectedTax) +
               "\nDiscrepancy : $" + String.format("%.2f", discrepancy) +
               "\nStatus      : " + status;
    }
}
