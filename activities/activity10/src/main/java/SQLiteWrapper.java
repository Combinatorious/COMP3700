import java.sql.*;

public class SQLiteWrapper implements DataAdapter {
    Connection conn = null;
    /* Database name, will be created in working directory if none exists */
    public static final String url = "jdbc:sqlite:src/main/resources/databases/";



    /*
    Connects to database in working directory with name in second part of URL above.
    If none exists, creates one.
    Also creates tables for products and customers if none exist.
    Also prints out current records in db.
     */
    public int connect(String fileName) {
        if (conn == null) {
            try {

                conn = DriverManager.getConnection(url + fileName);

                if (conn != null) {
                    Statement stmt = conn.createStatement();

                    String create = "CREATE TABLE IF NOT EXISTS Product (\n"
                            + "    Barcode integer PRIMARY KEY,\n"
                            + "    Name text,\n"
                            + "    Price real,\n"
                            + "    Quantity real,\n"
                            + "    Supplier text\n);";
                    stmt.execute(create);

                    create = "CREATE TABLE IF NOT EXISTS Customer (\n"
                            + "    CustomerID integer PRIMARY KEY,\n"
                            + "    Name text,\n"
                            + "    Email text,\n"
                            + "    Phone text,\n"
                            + "    Address text,\n"
                            + "    PaymentInfo text\n);";
                    stmt.execute(create);

                    create = "CREATE TABLE IF NOT EXISTS Purchase (\n"
                            + "    PurchaseID integer PRIMARY KEY,\n"
                            + "    Date text,\n"
                            + "    Barcode integer,\n"
                            + "    CustomerID integer,\n"
                            + "    Quantity real,\n"
                            + "    Price real,\n"
                            + "    FOREIGN KEY(Barcode) REFERENCES Product(Barcode),\n"
                            + "    FOREIGN KEY(CustomerID) REFERENCES Customer(CustomerID)\n);";
                    stmt.execute(create);

                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
                return DataAdapter.ERROR;
            }
            return DataAdapter.SUCCESS;
        }
        else return DataAdapter.ALREADY_CONNECTED;
    }

    public int disconnect() {
        try {
            conn.close();
        }
        catch (Exception e) {
            System.out.println("Error disconnecting from database.");
            return DataAdapter.ERROR;
        }
        conn = null;
        return DataAdapter.SUCCESS;
    }

    public int saveProduct(ProductModel product) {

        if (conn != null && product != null) {
            try {
                Statement stmt = conn.createStatement();
                if (loadProduct(product.barcode) == null) {           // this is a new product!
                    stmt.execute("INSERT INTO Product(Barcode, Name, Price, Quantity, Supplier) VALUES ("
                            + product.barcode + ","
                            + '\'' + product.name + '\'' + ","
                            + product.price + ","
                            + product.quantity + ","
                            + '\'' + product.supplier + '\'' + ")"
                    );
                } else {
                    stmt.executeUpdate("UPDATE Product SET "
                            + "Barcode = " + product.barcode + ","
                            + "Name = " + '\'' + product.name + '\'' + ","
                            + "Price = " + product.price + ","
                            + "Quantity = " + product.quantity + ","
                            + "Supplier = " + '\'' + product.supplier + '\'' +
                            " WHERE Barcode = " + product.barcode
                    );

                }
            } catch (Exception ex) {
                ex.printStackTrace();
                return DataAdapter.ERROR;
            }
            return DataAdapter.SUCCESS;
        }

        return DataAdapter.ERROR;
    }

    public ProductModel loadProduct(int barcode) {
        ProductModel product = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Product WHERE Barcode = " + barcode);
            product = getProductFromResultSet(rs);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return product;
    }

    /*
        Loads all products in the current database into a 2d String array for display
     */
    public String[][] loadAllProducts() {
        return loadAllFromTable("Product");

    }

    public String[][] loadAllCustomers() {
        return loadAllFromTable("Customer");
    }

    public String[][] loadAllPurchases() {
        return loadAllFromTable("Purchase");
    }

    private String[][] loadAllFromTable(String table) {
        if (conn == null) {
            return null;
        }
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + table);
            int columns = rs.getMetaData().getColumnCount();
            String[][] result = new String[getRowCount(table)][columns];
            int i = 0;
            while (rs.next()) {
                for (int j = 0; j < columns; j++) {
                    result[i][j] = rs.getString(j + 1);
                }
                i++;
            }
            return result;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public int saveCustomer(CustomerModel customer) {
        if (conn != null && customer != null) {
            try {
                Statement stmt = conn.createStatement();
                if (loadCustomer(customer.customerID) == null) {
                    stmt.execute("INSERT INTO Customer(CustomerID, Name, Email, Phone, Address, PaymentInfo) VALUES ("
                            + customer.customerID + ","
                            + '\'' + customer.name + '\'' + ","
                            + '\'' + customer.email + '\'' + ","
                            + '\'' + customer.phone + '\'' + ","
                            + '\'' + customer.address + '\'' + ","
                            + '\'' + customer.paymentInfo + '\'' + ")"
                    );
                }
                else {
                    stmt.executeUpdate("UPDATE Customer SET "
                            + "CustomerID = " + customer.customerID + ","
                            + "Name = " + '\'' + customer.name + '\'' + ","
                            + "Email = " + '\'' + customer.email + '\'' + ","
                            + "Phone = " + '\'' + customer.phone + '\'' + ","
                            + "Address = " + '\'' + customer.address + '\'' + ","
                            + "PaymentInfo = " + '\'' + customer.paymentInfo + '\'' +
                            " WHERE CustomerId = " + customer.customerID
                    );
                }
            }
            catch(Exception ex) {
                ex.printStackTrace();
                return DataAdapter.ERROR;
            }
            return DataAdapter.SUCCESS;
        }
        return DataAdapter.ERROR;
    }

    public CustomerModel loadCustomer(int customerID) {
        CustomerModel customer = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Customer WHERE CustomerID = " + customerID);
            customer = getCustomerFromResultSet(rs);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return customer;
    }

    public int savePurchase(PurchaseModel purchase) {
        if (conn != null && purchase != null) {
            try {
                Statement stmt = conn.createStatement();
                if (loadPurchase(purchase.purchaseID) == null) {
                    stmt.execute("INSERT INTO Purchase(PurchaseID, Date, Barcode, CustomerID, Quantity, Price) VALUES ("
                            + purchase.purchaseID + ","
                            + '\'' + purchase.date + '\'' + ","
                            + purchase.barcode + ","
                            + purchase.customerID + ","
                            + purchase.quantity + ","
                            + purchase.price + ")"
                    );
                }
                else {
                    stmt.executeUpdate("UPDATE Purchase SET "
                            + "PurchaseID = " + purchase.purchaseID + ","
                            + "Date = " + '\'' + purchase.date + '\'' + ","
                            + "Barcode = " + purchase.barcode + ","
                            + "CustomerID = " + purchase.customerID + ","
                            + "Quantity = " + purchase.quantity + ","
                            + "Price = " + purchase.price +
                            " WHERE PurchaseId = " + purchase.purchaseID
                    );
                }
            }
            catch(Exception ex) {
                ex.printStackTrace();
                return DataAdapter.ERROR;
            }
            return DataAdapter.SUCCESS;
        }
        return DataAdapter.ERROR;
    }

    public PurchaseModel loadPurchase(int purchaseID) {
        PurchaseModel purchase = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Purchase WHERE PurchaseID = " + purchaseID);
            purchase = getPurchaseFromResultSet(rs);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return purchase;
    }


    private int getRowCount(String table) {
        if (conn == null) {
            return 0;
        }
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(1) FROM " + table);
            return rs.next() ? rs.getInt(1) : 0;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    /*
    Uses information from table model to update (delete and insert a new object) into one of the three tables
    id: the primary key
    updateVal: the string array of the entire row provided by table model
    fistColHeader: used to identify which table to update the value in
     */
    @Override
    public int updateValue(String id, String[] updateVal, String firstColHeader) {
        if (conn == null) {
            return DataAdapter.ERROR;
        }

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = null;
            String sql;
            switch (firstColHeader) {
                case "Barcode" :
                    sql = "SELECT * FROM Product WHERE ";
                    break;
                case "CustomerID" :
                    sql = "SELECT * FROM Customer WHERE ";
                    break;
                case "PurchaseID" :
                    sql = "SELECT * FROM Purchase WHERE ";
                    break;
                default : return DataAdapter.ERROR;
            }

            deleteRow(id, firstColHeader);
            rs.updateObject(col + 1, updateVal);
            switch (firstColHeader) {
                case "Barcode" :
                    return saveProduct(getProductFromResultSet(rs));
                case "CustomerID" :
                    return saveCustomer(getCustomerFromResultSet(rs));
                case "PurchaseID" :
                    return savePurchase(getPurchaseFromResultSet(rs));
                default : return DataAdapter.ERROR;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return DataAdapter.SUCCESS;
        }
    }


    @Override
    public int deleteRow(String id, String firstColHeader) {
        if (conn == null) {
            return DataAdapter.ERROR;
        }
        String sql;
        switch (firstColHeader) {
            case "Barcode" :
                sql = "DELETE FROM Product WHERE ";
                break;
            case "CustomerID" :
                sql = "DELETE FROM Customer WHERE ";
                break;

            case "PurchaseID" :
                sql = "DELETE FROM Purchase WHERE ";
                break;
            default : return DataAdapter.ERROR;
        }
        try {
            Statement stmt = conn.createStatement();
            if (stmt.executeUpdate(sql + firstColHeader + " = " + id) > 0) {
                return DataAdapter.SUCCESS;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return DataAdapter.ERROR;
        }
        return DataAdapter.ERROR;
    }

    /* Some private sets to aid in updating database */
    private PurchaseModel getPurchaseFromResultSet(ResultSet rs) {
        try {
            if (rs.next()) {
                return new PurchaseModel(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getDouble(5),
                        rs.getDouble(6)
                );
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return null;
    }
    private ProductModel getProductFromResultSet(ResultSet rs) {
        try {
            if (rs.next()) {
                return new ProductModel(rs.getInt(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getDouble(4),
                        rs.getString(5));
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return null;


    }
    private CustomerModel getCustomerFromResultSet(ResultSet rs) {
        try {
            if (rs.next()) {
                return new CustomerModel(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6)
                );
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return null;

    }
}

