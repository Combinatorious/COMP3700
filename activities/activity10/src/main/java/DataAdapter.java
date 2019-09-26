public interface DataAdapter {

    public static final int SUCCESS = 0;
    public static final int ERROR = 1;

    public int connect();
    public int disconnect();

    public ProductModel loadProduct(int barcode);
    public int saveProduct(ProductModel product);

    public CustomerModel loadCustomer(int customerID);
    public int saveCustomer(CustomerModel customer);

//    public int loadPurchase(int purchaseID);
//    public int savePurchase(PurchaseModel purchase);
}
