import com.google.gson.Gson;

import javax.xml.crypto.Data;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SocketNetworkAdapter implements INetworkAdapter, DataAdapter {
    @Override
    public String exchange(String msg, String host, int port) throws Exception {
        Socket link = new Socket(host, port);
        Scanner input = new Scanner(link.getInputStream());
        PrintWriter output = new PrintWriter(link.getOutputStream(), true);

        output.println(msg);
        msg = input.nextLine();

        output.close();
        input.close();
        link.close();

        return msg;
    }

    @Override
    public MessageModel exchange(MessageModel msg, String host, int port) throws Exception {
        Gson gson = new Gson();
        return gson.fromJson(exchange(gson.toJson(msg), host, port), MessageModel.class);
    }

    @Override
    public int connect(String fileName) {
        return -1; // TODO: change connectivity strategy to work with server
    }

    @Override
    public int disconnect() {
        return -1;
    }

    @Override
    public ProductModel loadProduct(int barcode) {
        Gson gson = new Gson();

        MessageModel msg = new MessageModel();
        msg.code = MessageModel.GET_PRODUCT;
        msg.data = Integer.toString(barcode);

        try {
            msg = exchange(msg, StoreServer.HOST, StoreServer.PORT);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        if (msg.code == MessageModel.ERROR) {
            return null;
        }

        return gson.fromJson(msg.data, ProductModel.class);
    }

    public int saveProduct(ProductModel product) {
        Gson gson = new Gson();

        MessageModel msg = new MessageModel();
        msg.code = MessageModel.PUT_PRODUCT;
        msg.data = gson.toJson(product);

        try {
            msg = exchange(msg, StoreServer.HOST, StoreServer.PORT);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        if (msg.code == MessageModel.ERROR) {
            return DataAdapter.ERROR;
        }

        return DataAdapter.SUCCESS;
    }

    public CustomerModel loadCustomer(int customerID) {
        Gson gson = new Gson();

        MessageModel msg = new MessageModel();
        msg.code = MessageModel.GET_CUSTOMER;
        msg.data = Integer.toString(customerID);

        try {
            msg = exchange(msg, StoreServer.HOST, StoreServer.PORT);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        if (msg.code == MessageModel.ERROR) {
            return null;
        }

        return gson.fromJson(msg.data, CustomerModel.class);
    }

    public int saveCustomer(CustomerModel customer) {
        Gson gson = new Gson();

        MessageModel msg = new MessageModel();
        msg.code = MessageModel.PUT_CUSTOMER;
        msg.data = gson.toJson(customer);

        try {
            msg = exchange(msg, StoreServer.HOST, StoreServer.PORT);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        if (msg.code == MessageModel.ERROR) {
            return DataAdapter.ERROR;
        }

        return DataAdapter.SUCCESS;
    }

    public PurchaseModel loadPurchase(int purchaseID) {
        Gson gson = new Gson();

        MessageModel msg = new MessageModel();
        msg.code = MessageModel.GET_PURCHASE;
        msg.data = Integer.toString(purchaseID);

        try {
            msg = exchange(msg, StoreServer.HOST, StoreServer.PORT);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        if (msg.code == MessageModel.ERROR) {
            return null;
        }

        return gson.fromJson(msg.data, PurchaseModel.class);
    }

    public int savePurchase(PurchaseModel purchase) {
        Gson gson = new Gson();

        MessageModel msg = new MessageModel();
        msg.code = MessageModel.PUT_PURCHASE;
        msg.data = gson.toJson(purchase);

        try {
            msg = exchange(msg, StoreServer.HOST, StoreServer.PORT);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        if (msg.code == MessageModel.ERROR) {
            return DataAdapter.ERROR;
        }

        return DataAdapter.SUCCESS;
    }

    /*
     Load a user from the users database
     */
    public UserModel loadUser(UserModel user) {
        Gson gson = new Gson();

        MessageModel msg = new MessageModel();
        msg.code = MessageModel.GET_USER;
        msg.data = gson.toJson(user);

        try {
            msg = exchange(msg, StoreServer.HOST, StoreServer.PORT);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (msg.code == MessageModel.ERROR) {
            return null;
        }

        return gson.fromJson(msg.data, UserModel.class);
    }

    public int saveUser(UserModel user) {
        Gson gson = new Gson();

        MessageModel msg = new MessageModel();
        msg.code = MessageModel.PUT_USER;
        msg.data = gson.toJson(user);

        try{
        msg = exchange(msg, StoreServer.HOST, StoreServer.PORT);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (msg.code == MessageModel.ERROR) {
            return DataAdapter.ERROR;
        }

        return DataAdapter.SUCCESS;

    }

    public int removeUser(UserModel user) {
        Gson gson = new Gson();

        MessageModel msg = new MessageModel();
        msg.code = MessageModel.REMOVE_USER;
        msg.data = gson.toJson(user);

        try {
            msg = exchange(msg, StoreServer.HOST, StoreServer.PORT);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        if (msg.code == MessageModel.ERROR) {
            return  DataAdapter.ERROR;
        }

        return DataAdapter.SUCCESS;
    }

    /*
     Handled the same here as loadUser, but on server side the only difference
     is that load user only returns the user, login returns the user and stores
     it in active users.
     */
    public UserModel login(UserModel user) {
        Gson gson = new Gson();

        MessageModel msg = new MessageModel();
        msg.code = MessageModel.LOGIN;
        msg.data = gson.toJson(user);

        try {
            msg = exchange(msg, StoreServer.HOST, StoreServer.PORT);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (msg.code == MessageModel.ERROR) {
            return null;
        }

        return gson.fromJson(msg.data, UserModel.class);
    }

    /*
    Again handled pretty analogously here to removeUser, just we want the user removed
    from active users on the server, not removed from the data storage
     */
    public int logout(UserModel user) {
        Gson gson = new Gson();

        MessageModel msg = new MessageModel();
        msg.code = MessageModel.LOGOUT;
        msg.data = gson.toJson(user);

        try {
            msg = exchange(msg, StoreServer.HOST, StoreServer.PORT);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        if (msg.code == MessageModel.ERROR) {
            return  DataAdapter.ERROR;
        }

        return DataAdapter.SUCCESS;
    }

    public String[][] loadAllProducts() {
        Gson gson = new Gson();

        MessageModel msg = new MessageModel();
        msg.code = MessageModel.GET_ALL_PRODUCTS;

        try {
            msg = exchange(msg, StoreServer.HOST, StoreServer.PORT);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        if (msg.code == MessageModel.ERROR) {
            return null;
        }

        return gson.fromJson(msg.data, String[][].class);
    }

    public String[][] loadAllCustomers() {
        Gson gson = new Gson();

        MessageModel msg = new MessageModel();
        msg.code = MessageModel.GET_ALL_CUSTOMERS;

        try {
            msg = exchange(msg, StoreServer.HOST, StoreServer.PORT);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        if (msg.code == MessageModel.ERROR) {
            return null;
        }

        return gson.fromJson(msg.data, String[][].class);
    }

    public String[][] loadAllPurchases() {
        Gson gson = new Gson();

        MessageModel msg = new MessageModel();
        msg.code = MessageModel.GET_ALL_PURCHASES;

        // use 0 id to differentiate from specific customer's purchases
        msg.data = Integer.toString(0);

        try {
            msg = exchange(msg, StoreServer.HOST, StoreServer.PORT);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        if (msg.code == MessageModel.ERROR) {
            return null;
        }

        return gson.fromJson(msg.data, String[][].class);
    }

    public String[][] loadPurchasesForCustomer(int customerID) {
        Gson gson = new Gson();

        MessageModel msg = new MessageModel();
        msg.code = MessageModel.GET_ALL_PURCHASES;
        msg.data = Integer.toString(customerID);

        try {
            msg = exchange(msg, StoreServer.HOST, StoreServer.PORT);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        if (msg.code == MessageModel.ERROR) {
            return null;
        }

        return gson.fromJson(msg.data, String[][].class);
    }

    public String[][] loadAllUsers() {
        Gson gson = new Gson();

        MessageModel msg = new MessageModel();
        msg.code = MessageModel.GET_ALL_USERS;

        try {
            msg = exchange(msg, StoreServer.HOST, StoreServer.PORT);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        if (msg.code == MessageModel.ERROR) {
            return null;
        }

        return gson.fromJson(msg.data, String[][].class);
    }

    // Update methods used by table to allow some level of manipulation
    public int updateValue(String id, String[] newVals, String firstColHeader) {
        Gson gson = new Gson();

        MessageModel msg = new MessageModel();
        msg.code = MessageModel.UPDATE_VALUE;

        String[] data = new String[newVals.length + 2];
        data[0] = id;
        data[1] = firstColHeader;

        System.arraycopy(newVals, 0, data, 2, data.length - 2);

        msg.data = gson.toJson(data);

        try {
            msg = exchange(msg, StoreServer.HOST, StoreServer.PORT);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        if (msg.code == MessageModel.ERROR) {
            return DataAdapter.ERROR;
        }

        return DataAdapter.SUCCESS;
    }

    public int deleteRow(String id, String firstColHeader) {
        Gson gson = new Gson();

        MessageModel msg = new MessageModel();
        msg.code = MessageModel.DELETE_ROW;
        String[] data = {id, firstColHeader};

        msg.data = gson.toJson(data);

        try {
            msg = exchange(msg, StoreServer.HOST, StoreServer.PORT);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        if (msg.code == MessageModel.ERROR) {
            return DataAdapter.ERROR;
        }

        return DataAdapter.SUCCESS;
    }
}
