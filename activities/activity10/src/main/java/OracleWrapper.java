/*
    TBI
 */
public class OracleWrapper implements DataAdapter {
    public int connect(String fileName) {
        return DataAdapter.ERROR;
    }

    public int disconnect() {
        return DataAdapter.ERROR;
    }

    public ProductModel loadProduct(int barcode) {
        return null;
    }

    public int saveProduct(ProductModel product) {
        return DataAdapter.ERROR;
    }

    public CustomerModel loadCustomer(int customerID) {
        return null;
    }

    public int saveCustomer(CustomerModel customer) {
        return DataAdapter.ERROR;
    }

    public PurchaseModel loadPurchase(int purchaseID) {
        return null;
    }

    public int savePurchase(PurchaseModel purchase) {
        return DataAdapter.ERROR;
    }

    public String[][] loadAllProducts() {
        return null;
    }

    public String[][] loadAllCustomers() {
        return null;
    }

}
