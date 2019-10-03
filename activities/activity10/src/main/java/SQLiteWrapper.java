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

        if (conn != null) {
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
            if (rs.next()) {
                product = new ProductModel(rs.getInt(1),
                                            rs.getString(2),
                                            rs.getDouble(3),
                                            rs.getDouble(4),
                                            rs.getString(5));
            }

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
        if (conn != null) {
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
            if (rs.next()) {
                customer = new CustomerModel(
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
        }
        return customer;
    }

    /*
     Result set row counter ripped off of Stack Overflow
     https://stackoverflow.com/a/8292514
     */
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

}

