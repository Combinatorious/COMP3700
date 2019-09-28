import java.sql.*;

public class SQLiteWrapper implements DataAdapter {
    Connection conn = null;
    /* Database name, will be created in working directory if none exists */
    public static final String url = "jdbc:sqlite:activity11.db";



    /*
    Connects to database in working directory with name in second part of URL above.
    If none exists, creates one.
    Also creates tables for products and customers if none exist.
    Also prints out current records in db.
     */
    public int connect() {
        try {

            conn = DriverManager.getConnection(url);

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

                ResultSet rs = stmt.executeQuery("SELECT * FROM Product");
                System.out.println("Products");
                while (rs.next())
                    System.out.println(rs.getString(1) + " " + rs.getString(2)
                            + " " + rs.getString(3) + " " + rs.getString(4)
                            + " " + rs.getString(5));
                rs = stmt.executeQuery("SELECT * FROM Customer");
                System.out.println("Customers");
                while (rs.next())
                    System.out.println(rs.getString(1) + " " + rs.getString(2)
                            + " " + rs.getString(3) + " " + rs.getString(4)
                            + " " + rs.getString(5) + " " + rs.getString(6));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return DataAdapter.ERROR;
        }
        return DataAdapter.SUCCESS;
    }

    public int disconnect() {
        try {
            conn.close();
        }
        catch (Exception e) {
            System.out.println("Error disconnecting from database.");
            return DataAdapter.ERROR;
        }
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


}

