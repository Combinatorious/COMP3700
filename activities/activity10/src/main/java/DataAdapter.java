public interface DataAdapter {

    public static final int SUCCESS = 1;
    public static final int ERROR = 0;

    public int connect(String fileName);
    public int disconnect();

    public ProductModel loadProduct(int barcode);
    public int saveProduct(ProductModel product);

    public CustomerModel loadCustomer(int customerID);
    public int saveCustomer(CustomerModel customer);
// TBI:
//    public PurchaseModel loadPurchase(int purchaseID);
//    public int savePurchase(PurchaseModel purchase);
}
