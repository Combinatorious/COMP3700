/*
 Product data model with public (package-private) instance variables
 */
public class ProductModel {

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
}
