import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.Callable;
import java.util.Map;
import java.util.HashMap;

import com.google.gson.Gson;

import javax.swing.*;


public class StoreServer {

    static DataAdapter dataAccess;

    public static final int PORT = 1000;
    public static final String HOST = "localhost";
    public static final String DEFAULT_DB = "store.db";
    // note: requires a databases directory to exist to open to the directory
    public static final String RELATIVE_PATH = "src/main/databases/";

    public static int totalActiveUsers = 0;

    public static Map<Integer, UserModel> activeUsers = new HashMap<Integer, UserModel>();

    private static final Map<Integer, Runnable> runTable = new HashMap<Integer, Runnable>() {{
        put(MessageModel.GET_PRODUCT, () -> getProduct());
        put(MessageModel.PUT_PRODUCT, () -> putProduct());
        put(MessageModel.GET_CUSTOMER, () -> getCustomer());
        put(MessageModel.PUT_CUSTOMER, () -> putCustomer());
        put(MessageModel.GET_PURCHASE, () -> getPurchase());
        put(MessageModel.PUT_PURCHASE, () -> putPurchase());
        put(MessageModel.GET_USER, () -> getUser());
        put(MessageModel.PUT_USER, () -> putUser());
        put(MessageModel.REMOVE_USER, () -> removeUser());
        put(MessageModel.LOGIN, () -> login());
        put(MessageModel.LOGOUT, () -> logout());
        put(MessageModel.GET_ALL_PRODUCTS, () -> getAllProducts());
        put(MessageModel.GET_ALL_CUSTOMERS, () -> getAllCustomers());
        put(MessageModel.GET_ALL_PURCHASES, () -> getAllPurchases());
        put(MessageModel.UPDATE_VALUE, () -> updateValue());
        put(MessageModel.DELETE_ROW, () -> deleteRow());
    }};

    public static PrintWriter out;
    public static Scanner in;
    public static MessageModel msg;
    public static Gson gson;

    public static void main(String[] args) {

        try {
            ServerSocket server = new ServerSocket(PORT);
            gson = new Gson();

            // TODO: set up data access and file loader here perhaps

            String dbFile = getUserFile();
            if (dbFile == null) {
                dbFile = DEFAULT_DB;
            }
            dataAccess = new SQLiteWrapper();
            try {
                dataAccess.connect(dbFile);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }

            // TODO: set up an interrupt to kill the loop and disconnect from database
            while (true) {
                System.out.println("Server awaiting requests...");
                Socket pipe = server.accept();
                out = new PrintWriter(pipe.getOutputStream(), true);
                in = new Scanner(pipe.getInputStream());

                msg = gson.fromJson(in.nextLine(), MessageModel.class);

                if (runTable.containsKey(msg.code)) {
                    runTable.get(msg.code).run();
                } else {
                    msg.code = MessageModel.ERROR;
                    out.println(gson.toJson(msg));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void getProduct() {
        int barcode = Integer.parseInt(msg.data);
        System.out.println("Load product with barcode: " + barcode);

        ProductModel res = dataAccess.loadProduct(barcode);

        if (res != null) {
            msg.code = MessageModel.SUCCESS;
            msg.data = gson.toJson(res);
        }
        else { // product not found
            msg.code = MessageModel.ERROR;
        }

        out.println(gson.toJson(msg));
    }

    public static void putProduct() {

        ProductModel product = gson.fromJson(msg.data, ProductModel.class);
        System.out.println("Save product with barcode " + product.barcode);
        int res = dataAccess.saveProduct(product); //save product implementation updates existing products

        if (res == DataAdapter.SUCCESS) {
            msg.code = MessageModel.SUCCESS;
        } else {
            msg.code = MessageModel.ERROR;
        }
        out.println(gson.toJson(msg));
    }

    public static void getCustomer() {

        int customerID = Integer.parseInt(msg.data);
        System.out.println("Load customer with ID: " + customerID);

        CustomerModel res = dataAccess.loadCustomer(customerID);

        if (res != null) {
            msg.code = MessageModel.SUCCESS;
            msg.data = gson.toJson(res);
        } else { // product was not found
            msg.code = MessageModel.ERROR;
        }

        out.println(gson.toJson(msg));
    }

    public static void putCustomer() {

        CustomerModel customer = gson.fromJson(msg.data, CustomerModel.class);
        System.out.println("Save customer with ID " + customer.customerID);

        int res = dataAccess.saveCustomer(customer); //save customer implementation updates existing

        if (res == DataAdapter.SUCCESS) {
            msg.code = MessageModel.SUCCESS;
        } else {
            msg.code = MessageModel.ERROR;
        }
        out.println(gson.toJson(msg));
    }

    public static void getPurchase() {

        int purchaseID = Integer.parseInt(msg.data);
        System.out.println("Load purchase with ID: " + purchaseID);

        PurchaseModel res = dataAccess.loadPurchase(purchaseID);

        if (res != null) {
            msg.code = MessageModel.SUCCESS;
            msg.data = gson.toJson(res);
        } else {
            msg.code = MessageModel.ERROR;
        }
        out.println(gson.toJson(msg));
    }

    public static void putPurchase() {

        PurchaseModel purchase = gson.fromJson(msg.data, PurchaseModel.class);
        System.out.println("Save purchase with ID " + purchase.purchaseID);

        int res = dataAccess.savePurchase(purchase);

        if (res == DataAdapter.SUCCESS) {
            msg.code = MessageModel.SUCCESS;
        } else {
            msg.code = MessageModel.ERROR;
        }
        msg.data = Integer.toString(MessageModel.SUCCESS);
        out.println(gson.toJson(msg));
    }

    public static void getUser() {

        UserModel user = gson.fromJson(msg.data, UserModel.class);

        System.out.println("Get user " + user.username);

        UserModel res = dataAccess.loadUser(user);

        if (res != null) {
            msg.code = MessageModel.SUCCESS;
            msg.data = gson.toJson(res);
        }
        else {
            msg.code = MessageModel.ERROR;
        }
        out.println(gson.toJson(msg));
    }

    public static void putUser() {

        UserModel user = gson.fromJson(msg.data, UserModel.class);

        System.out.println("Put user " + user.username);

        if (dataAccess.saveUser(user) == DataAdapter.SUCCESS) {
            msg.code = MessageModel.SUCCESS;
        }
        else {
            msg.code = MessageModel.ERROR;
        }
        out.println(gson.toJson(msg));
    }

    public static void removeUser() {

        UserModel user = gson.fromJson(msg.data, UserModel.class);

        System.out.println("Remove user " + user.username);

        if (dataAccess.removeUser(user) == DataAdapter.SUCCESS){
            msg.code = MessageModel.SUCCESS;
        }
        else{
            msg.code = MessageModel.ERROR;
        }
        out.println(gson.toJson(msg));
    }

    public static void login() {

        UserModel user = gson.fromJson(msg.data, UserModel.class);
        System.out.println("Login from " + user.username);

        UserModel res = dataAccess.loadUser(user);

        if (res != null && user.password.equals(res.password)) {
            msg.code = MessageModel.SUCCESS;
            msg.ssid = ++totalActiveUsers;
            res.ssid = msg.ssid;
            msg.data = gson.toJson(res);
            activeUsers.put(msg.ssid, res);
        } else {
            msg.code = MessageModel.ERROR;
        }
        out.println(gson.toJson(msg));
    }

    public static void logout() {

        UserModel user = gson.fromJson(msg.data, UserModel.class);
        System.out.println("Logout from " + user.username);

        if (activeUsers.remove(user.ssid, user)) {
            // success
            totalActiveUsers--;
            msg.code = MessageModel.SUCCESS;
        } else {
            msg.code = MessageModel.ERROR;
        }
        out.println(gson.toJson(msg));
    }

    public static void getAllProducts() {

        String[][] products = dataAccess.loadAllProducts();

        msg.data = gson.toJson(products);

        out.println(gson.toJson(msg));

    }
    public static void getAllCustomers() {

        String[][] customers = dataAccess.loadAllCustomers();

        msg.data = gson.toJson(customers);

        out.println(gson.toJson(msg));

    }
    public static void getAllPurchases() {
        String[][] purchases;
        int customerID = Integer.parseInt(msg.data);
        if (customerID == 0) {
            purchases = dataAccess.loadAllPurchases();
        }
        else {
            if (dataAccess.loadCustomer(customerID) == null) {
                msg.code = MessageModel.ERROR;
            }
            purchases = dataAccess.loadPurchasesForCustomer(customerID);
        }

        msg.data = gson.toJson(purchases);

        out.println(gson.toJson(msg));
    }

    public static void updateValue() {
        String[] params = gson.fromJson(msg.data, String[].class);
        String[] newVals = new String[params.length - 2];
        System.arraycopy(params, 2, newVals, 0, params.length - 2);
        if (dataAccess.updateValue(params[0], newVals, params[1]) == DataAdapter.ERROR) {
            msg.code = MessageModel.ERROR;
        }
        else {
            msg.code = MessageModel.SUCCESS;
        }
        out.println(gson.toJson(msg));
    }

    public static void deleteRow() {
        String[] params = gson.fromJson(msg.data, String[].class);
        if (dataAccess.deleteRow(params[0], params[1]) == DataAdapter.ERROR) {
            msg.code = MessageModel.ERROR;
        }
        else {
            msg.code = MessageModel.SUCCESS;
        }
        out.println(gson.toJson(msg));
    }

    private static String getUserFile() {
        Application.SaveFileChooser fc = new Application.SaveFileChooser(RELATIVE_PATH);
        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return fc.getSelectedFile().getName();
        }
        else return null;
    }

    public static class SaveFileChooser extends JFileChooser {
        JButton createNew = new JButton("Create new file");

        public SaveFileChooser(String currentDirectoryPath) {
            super(currentDirectoryPath);
            add(createNew);
            createNew.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showSaveDialog(null);
                }
            });
        }
    }

}
