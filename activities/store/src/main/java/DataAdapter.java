public interface DataAdapter {

    public static final int SUCCESS = 1;
    public static final int ERROR = 0;
    public static final int ALREADY_CONNECTED = 2;

    public int connect(String fileName);
    public int disconnect();

    public ProductModel loadProduct(int barcode);
    public int saveProduct(ProductModel product);

    public CustomerModel loadCustomer(int customerID);
    public int saveCustomer(CustomerModel customer);

    public PurchaseModel loadPurchase(int purchaseID);
    public int savePurchase(PurchaseModel purchase);

    public String[][] loadAllProducts();
    public String[][] loadAllCustomers();
    public String[][] loadAllPurchases();

    // Update methods used by table to allow some level of manipulation
    public int updateValue(String id, String[] newVals, String firstColHeader);
    public int deleteRow(String ID, String idType);
}
