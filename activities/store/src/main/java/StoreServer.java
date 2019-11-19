import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.Callable;
import java.util.HashMap;

import com.google.gson.Gson;


public class StoreServer extends Thread {

    DataAdapter dataAccess;

    public static final int PORT = 1000;
    public static final String HOST = "localhost";

    private AtomicBoolean running = new AtomicBoolean(false);

    public int totalActiveUsers = 0;

    public HashMap<Integer, UserModel> activeUsers = new HashMap<Integer, UserModel>();

    public HashMap<Integer, Callable > runTable = new HashMap<Integer, Callable>();

    @Override
    public void interrupt() {
        running.set(false);
        super.interrupt();
    } // interrupting TBI

    public void run() {
        

        public static final int GET_PRODUCT = 100;
        public static final int PUT_PRODUCT = 101;

        public static final int GET_CUSTOMER = 200;
        public static final int PUT_CUSTOMER = 201;

        public static final int GET_PURCHASE = 300;
        public static final int PUT_PURCHASE = 301;

        public static final int GET_USER = 400;
        public static final int PUT_USER = 401;
        public static final int REMOVE_USER = 402;

        public static final int LOGIN  = 10;
        public static final int LOGOUT = 11;

        public static final int GET_ALL_PRODUCTS = 500;
        public static final int GET_ALL_CUSTOMERS = 501;
        public static final int GET_ALL_PURCHASES = 502;

        public static final int UPDATE_VALUE = 1000;
        public static final int DELETE_ROW = 1001;

        try {
            ServerSocket server = new ServerSocket(PORT);
            Gson gson = new Gson();

            running.set(true);
            while (running.get()) {
                System.out.println("Server awaiting requests...");
                Socket pipe = server.accept();
                PrintWriter out = new PrintWriter(pipe.getOutputStream(), true);
                Scanner in = new Scanner(pipe.getInputStream());

                MessageModel msg = gson.fromJson(in.nextLine(), MessageModel.class);

                if (msg.code == MessageModel.GET_PRODUCT) {
                    int barcode = Integer.parseInt(msg.data);
                    System.out.println("Load product with barcode: " + barcode);

                    dataAccess = Application.getInstance().getDataAdapter();

                    ProductModel res = dataAccess.loadProduct(barcode);


                    if (res != null) {
                        msg.code = MessageModel.SUCCESS;
                        msg.data = gson.toJson(res);
                    }
                    else { // product was not found
                        msg.code = MessageModel.ERROR;
                    }

                    out.println(gson.toJson(msg));


                }
                else if (msg.code == MessageModel.PUT_PRODUCT) {

                    ProductModel product = gson.fromJson(msg.data, ProductModel.class);

                    System.out.println("Save product with barcode " + product.barcode);

                    dataAccess = Application.getInstance().getDataAdapter();

                    int res = dataAccess.saveProduct(product); //save product implementation updates existing products

                    if (res == DataAdapter.SUCCESS) {
                        msg.code = MessageModel.SUCCESS;
                    }
                    else {
                        msg.code = MessageModel.ERROR;
                    }
                    out.println(gson.toJson(msg));
                }
                else if (msg.code == MessageModel.GET_CUSTOMER) {

                    int customerID = Integer.parseInt(msg.data);
                    System.out.println("Load customer with ID: " + customerID);

                    dataAccess = Application.getInstance().getDataAdapter();

                    CustomerModel res = dataAccess.loadCustomer(customerID);

                    if (res != null) {
                        msg.code = MessageModel.SUCCESS;
                        msg.data = gson.toJson(res);
                    }
                    else { // product was not found
                        msg.code = MessageModel.ERROR;
                    }

                    out.println(gson.toJson(msg));

                }
                else if (msg.code == MessageModel.PUT_CUSTOMER) {

                    CustomerModel customer = gson.fromJson(msg.data, CustomerModel.class);

                    System.out.println("Save customer with ID " + customer.customerID);

                    dataAccess = Application.getInstance().getDataAdapter();

                    int res = dataAccess.saveCustomer(customer); //save customer implementation updates existing

                    if (res == DataAdapter.SUCCESS) {
                        msg.code = MessageModel.SUCCESS;
                    }
                    else {
                        msg.code = MessageModel.ERROR;
                    }
                    out.println(gson.toJson(msg));

                }
                else if (msg.code == MessageModel.LOGIN) {

                    UserModel user = gson.fromJson(msg.data, UserModel.class);

                    System.out.println("Login from " + user.username);

                    dataAccess = Application.getInstance().getDataAdapter();

                    UserModel res = dataAccess.loadUser(user);

                    if (res != null && user.password.equals(res.password)) {
                        msg.code = MessageModel.SUCCESS;
                        msg.ssid = ++totalActiveUsers;
                        res.ssid = msg.ssid;
                        msg.data = gson.toJson(res);
                        activeUsers.put(msg.ssid, res);
                    }
                    else {
                        msg.code = MessageModel.ERROR;
                    }

                    out.println(gson.toJson(msg));
                }
                else if (msg.code == MessageModel.LOGOUT) {

                    UserModel user = gson.fromJson(msg.data, UserModel.class);

                    System.out.println("Logout from " + user.username);

                    dataAccess = Application.getInstance().getDataAdapter();

                    if (activeUsers.remove(user.ssid, user)) {
                        // success
                        totalActiveUsers--;
                        msg.code = MessageModel.SUCCESS;
                    }
                    else {
                        // error logging out
                        msg.code = MessageModel.ERROR;
                    }

                    out.println(gson.toJson(msg));

                }
                else if (msg.code == MessageModel.PUT_USER) {
                    UserModel user = gson.fromJson(msg.data, UserModel.class);

                    System.out.println("Put user " + user.username);

                    dataAccess = Application.getInstance().getDataAdapter();

                    if (dataAccess.saveUser(user) == DataAdapter.SUCCESS) {
                        msg.code = MessageModel.SUCCESS;
                    }
                    else {
                        msg.code = MessageModel.ERROR;
                    }
                    out.println(gson.toJson(msg));
                }
                else if (msg.code == MessageModel.GET_USER) {
                    UserModel user = gson.fromJson(msg.data, UserModel.class);
                }
                
                else if (msg.code == MessageModel.REMOVE_USER) {

                }

                else {
                    msg.code = MessageModel.ERROR;
                    out.println(gson.toJson(msg));
                }
            }

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
