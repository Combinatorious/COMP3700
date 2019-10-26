import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;


public class ProductServer extends Thread {

    DataAdapter dataAccess;

    public static final int port = 1000;
    public static final String LOAD = "LOAD";
    public static final String SAVE = "SAVE";
    public static final String FAIL = "NULL";
    public static final String SUCCESS = "SUCCESS";

    private AtomicBoolean running = new AtomicBoolean(false);

    @Override
    public void interrupt() {
        running.set(false);
        super.interrupt();
    } // interrupting TBI

    public void run() {

        try {
            ServerSocket server = new ServerSocket(port);

            running.set(true);
            while (running.get()) {
                System.out.println("Server awaiting requests...");
                Socket pipe = server.accept();
                PrintWriter out = new PrintWriter(pipe.getOutputStream(), true);
                Scanner in = new Scanner(pipe.getInputStream());

                String command = in.nextLine();
                if (command.equals(LOAD)) {
                    int barcode = in.nextInt();
                    System.out.println("Load product with barcode: " + barcode);

                    dataAccess = Application.getInstance().getDataAdapter();

                    ProductModel res = dataAccess.loadProduct(barcode);


                    if (res != null) {
                        out.println(res.name);
                        out.println(res.price);
                        out.println(res.quantity);
                        out.println(res.supplier);
                    }
                    else {
                        out.println(FAIL);
                    }


                }
                else if (command.equals(SAVE)) {
                    int barcode = Integer.parseInt(in.nextLine());
                    String name = in.nextLine();
                    double price = Double.parseDouble(in.nextLine());
                    double quantity = Double.parseDouble(in.nextLine());
                    String supplier = in.nextLine();

                    System.out.println("Save product with barcode " + barcode);

                    dataAccess = Application.getInstance().getDataAdapter();

                    ProductModel product = new ProductModel(barcode, name, price, quantity, supplier);

                    dataAccess.saveProduct(product); //save product implementation updates existing products

                    out.println(SUCCESS);
                }

                else {
                    out.println(FAIL);
                }
            }

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
