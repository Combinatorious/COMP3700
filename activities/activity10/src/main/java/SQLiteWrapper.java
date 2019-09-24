import java.sql.*;

public class SQLiteWrapper {
    Connection conn = null;
    String url;

    public SQLiteWrapper() {
        url = "jdbc:sqlite:activity10.db";
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void connect() {
        try {

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
                        + "    Address text\n"
                        + "    PaymentInfo text\n);";

                ResultSet rs = stmt.executeQuery("SELECT * FROM Product");

                while (rs.next())
                    System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveProduct(ProductModel product) {

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
            }
        }
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


}

