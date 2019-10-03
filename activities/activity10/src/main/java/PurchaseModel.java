public class PurchaseModel {
    public static final String[] COL_NAMES = {"Purchase ID", "Barcode", "Customer ID", "Date", "Quantity", "Price"};

    // as of right now date and price are derived and set elsewhere, this should probably be handled within though
    public int purchaseID;
    public String date;
    public int barcode;
    public int customerID;
    public double quantity;
    public double price;

    public PurchaseModel(int purchaseID, int barcode, int customerID, double quantity) {
        this.purchaseID = purchaseID;
        this.barcode = barcode;
        this.customerID = customerID;
        this.quantity = quantity;
    }

    public PurchaseModel(int purchaseID, String date, int barcode, int customerID, double quantity, double price) {
        this.purchaseID = purchaseID;
        this.date = date;
        this.barcode = barcode;
        this.customerID = customerID;
        this.quantity = quantity;
        this.price = price;
    }
}
