/*
 Product data model with public (package-private) instance variables
 */
public class ProductModel {

    public static final String[] COL_NAMES = {"Barcode", "Name", "Price", "Quantity", "Supplier"};

    /*
    TODO: handle barcode automatically in database
     */
    int barcode;
    String name;
    double price;
    double quantity;
    String supplier;

    public ProductModel(int barcode, String name, double price, double quantity, String supplier) {
        this.barcode = barcode;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.supplier = supplier;
    }

    public ProductModel() {
        // default initialize
    }
}
