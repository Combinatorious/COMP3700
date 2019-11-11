import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import com.google.gson.Gson;


public class StoreServer extends Thread {

    DataAdapter dataAccess;

    public static final int port = 1000;

    private AtomicBoolean running = new AtomicBoolean(false);

    @Override
    public void interrupt() {
        running.set(false);
        super.interrupt();
    } // interrupting TBI

    public void run() {

        try {
            ServerSocket server = new ServerSocket(port);
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
