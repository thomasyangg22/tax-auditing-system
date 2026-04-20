public class TaxRecord {
    private String taxpayerID;
    private int year;
    private double income;
    private double taxesPaid;

    public TaxRecord(String taxpayerID, int year, double income, double taxesPaid) {
        this.taxpayerID = taxpayerID;
        this.year = year;
        this.income = income;
        this.taxesPaid = taxesPaid;
    }

    // Getters
    public String getTaxpayerID() { return taxpayerID; }
    public int getYear() { return year; }
    public double getIncome() { return income; }
    public double getTaxesPaid() { return taxesPaid; }

    @Override
    public String toString() {
        return "Year: " + year +
               " | Income: $" + String.format("%.2f", income) +
               " | Taxes Paid: $" + String.format("%.2f", taxesPaid);
    }
}
