import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PurchaseModel {
    public static final String[] COL_NAMES = {"PurchaseID", "Date", "Barcode", "CustomerID", "Quantity", "Price"};

    DataAdapter dataAccess;

    public int purchaseID;
    public String date;
    public int barcode;
    public int customerID;
    public double quantity;
    public double price;

    // Initialize a purchase model and derive date and price
    public PurchaseModel(int purchaseID, int barcode, int customerID, double quantity) {
        dataAccess = Application.getInstance().getDataAdapter();
        dataAccess.connect(Application.getInstance().dbFileName);
        // TODO: fix this connect strategy
        // Using a singleton data adapter should allow you to connect once and disconnect on app exit
        // which is a lot easier to manage than having each window connect and disconnect which could
        // potentially cause issues when you do something like create a new purchase in the middle of
        // another window that uses database access

        // for now though can disconnect data access without causing issues since this is only created
        // within AddPurchaseUI which is does not use database access

        this.purchaseID = purchaseID;
        this.barcode = barcode;
        this.customerID = customerID;
        this.quantity = quantity;

        ProductModel product = dataAccess.loadProduct(barcode);

        this.date = getDateAsString();
        this.price = product.price * quantity;

        dataAccess.disconnect();
    }

    public PurchaseModel(int purchaseID, String date, int barcode, int customerID, double quantity, double price) {
        this.purchaseID = purchaseID;
        this.date = date;
        this.barcode = barcode;
        this.customerID = customerID;
        this.quantity = quantity;
        this.price = price;
    }

    private String getDateAsString() {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        return df.format(new Date());
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
