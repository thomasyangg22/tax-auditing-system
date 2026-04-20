public class Taxpayer {
    private String taxpayerID;
    private String name;
    private String address;
    private String phoneNumber;

    public Taxpayer(String taxpayerID, String name, String address, String phoneNumber) {
        this.taxpayerID = taxpayerID;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    // Getters
    public String getTaxpayerID() { return taxpayerID; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getPhoneNumber() { return phoneNumber; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setAddress(String address) { this.address = address; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    @Override
    public String toString() {
        return "ID: " + taxpayerID + " | Name: " + name +
               " | Address: " + address + " | Phone: " + phoneNumber;
    }
}
