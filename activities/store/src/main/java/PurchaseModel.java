public class PurchaseModel {
    public static final String[] COL_NAMES = {"PurchaseID", "Date", "Barcode", "CustomerID", "Quantity", "Price"};

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

    /*
    PurchaseID, Date (string), Barcode, CustomerID, Quantity, Price
     */
    public static PurchaseModel getPurchaseFromStringArray(String[] values) {
        if (values == null || values.length != 6) {
            return null;
        }
        return new PurchaseModel(
                Integer.parseInt(values[0]),
                values[1],
                Integer.parseInt(values[2]),
                Integer.parseInt(values[3]),
                Double.parseDouble(values[4]),
                Double.parseDouble(values[5])
        );
    }
}
