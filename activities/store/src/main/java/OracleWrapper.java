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

    public int removeUser(UserModel user) {
        return DataAdapter.ERROR;
    }

    public int saveUser(UserModel user) {
        return DataAdapter.ERROR;
    }

    public UserModel loadUser(UserModel user) {
        return null;
    }

    public String[][] loadAllProducts() {
        return null;
    }

    public String[][] loadAllCustomers() {
        return null;
    }

    public String[][] loadAllPurchases() {
        return null;
    }

    @Override
    public int deleteRow(String ID, String idType) {
        return DataAdapter.ERROR;
    }

    @Override
    public int updateValue(String id, String[] newVals, String firstColHeader) {
        return DataAdapter.ERROR;
    }
}
