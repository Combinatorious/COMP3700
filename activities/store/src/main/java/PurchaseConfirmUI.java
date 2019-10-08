import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DecimalFormat;

public class PurchaseConfirmUI extends JFrame {

    public static final int FRAME_HEIGHT = 1200, FRAME_WIDTH = 800;

    DataAdapter dataAccess;

    PurchaseModel purchase;

    JButton confirm = new JButton("Confirm transaction");

    public PurchaseConfirmUI(PurchaseModel input) {
        this.setTitle("Is this correct?");
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

        dataAccess = Application.getInstance().getDataAdapter();
        dataAccess.connect(Application.getInstance().dbFileName);

        String currentDate = getDateAsString();
        CustomerModel customer = dataAccess.loadCustomer(input.customerID);
        ProductModel product = dataAccess.loadProduct(input.barcode);
        purchase = input;
        purchase.date = currentDate;
        purchase.price = product.price * purchase.quantity;

        JPanel label1 = new JPanel();
        label1.add(new JLabel("Purchase ID: " + purchase.purchaseID));
        this.getContentPane().add(label1);

        JPanel label2 = new JPanel();
        label2.add(new JLabel("Date: " + currentDate));
        this.getContentPane().add(label2);

        JPanel label3 = new JPanel();
        label3.add(new JLabel("Customer name: " + customer.name));
        this.getContentPane().add(label3);

        JPanel label4 = new JPanel();
        label4.add(new JLabel("Product name: " + product.name));
        this.getContentPane().add(label4);

        JPanel label5 = new JPanel();
        label5.add(new JLabel("Quantity: " + purchase.quantity));
        this.getContentPane().add(label5);

        DecimalFormat decFormat = new DecimalFormat("$#,###.00");
        JPanel label6 = new JPanel();
        label6.add(new JLabel("Total price: " + decFormat.format(purchase.price)));
        this.getContentPane().add(label6);

        JPanel button1 = new JPanel();
        button1.add(confirm);
        this.getContentPane().add(button1);

        confirm.addActionListener(new ConfirmActionListener());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dataAccess.disconnect();
                PurchaseConfirmUI.this.dispose();
            }
        });
    }

    class ConfirmActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(PurchaseConfirmUI.this, "I'll make sure to save that for you.");
            dataAccess.savePurchase(PurchaseConfirmUI.this.purchase);
            PurchaseConfirmUI.this.dispatchEvent(new WindowEvent(PurchaseConfirmUI.this, WindowEvent.WINDOW_CLOSING));
        }
    }

    private String getDateAsString() {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        return df.format(new Date());
    }

    public void run() {
        this.pack();
        this.setVisible(true);
    }
}
