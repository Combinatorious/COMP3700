import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DecimalFormat;

public class PurchaseDisplayUI extends JFrame {

    public static final int FRAME_HEIGHT = 1200, FRAME_WIDTH = 800;
    public static final int CONFIRM_TYPE = 0;
    public static final int DELETE_TYPE = 1;

    DataAdapter dataAccess;

    PurchaseModel purchase;

    JButton button;

    TransactionHistoryUI parent;

    public PurchaseDisplayUI(PurchaseModel input, int type) {
        if (type == CONFIRM_TYPE) {
            this.setTitle("Is this correct?");
            button = new JButton("Confirm transaction");
        }
        else { // type == DELETE_TYPE
            this.setTitle("Receipt");
            button = new JButton("Delete transaction");
        }
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

        dataAccess = Application.getInstance().getDataAdapter();

        CustomerModel customer = dataAccess.loadCustomer(input.customerID);
        ProductModel product = dataAccess.loadProduct(input.barcode);
        purchase = input;

        JPanel label1 = new JPanel();
        label1.add(new JLabel("Purchase ID: " + purchase.purchaseID));
        this.getContentPane().add(label1);

        JPanel label2 = new JPanel();
        label2.add(new JLabel("Date: " + purchase.date));
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
        button1.add(button);
        this.getContentPane().add(button1);

        if (type == CONFIRM_TYPE) {
            button.addActionListener(new ConfirmActionListener());
        }
        else {
            button.addActionListener(new DeleteActionListener());
        }

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                PurchaseDisplayUI.this.dispose();
            }
        });
    }

    class ConfirmActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(PurchaseDisplayUI.this, "I'll make sure to save that for you.");
            dataAccess.savePurchase(PurchaseDisplayUI.this.purchase);
            PurchaseDisplayUI.this.dispatchEvent(new WindowEvent(PurchaseDisplayUI.this, WindowEvent.WINDOW_CLOSING));
        }
    }

    class DeleteActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(PurchaseDisplayUI.this, "I'll make sure to delete that for you.");
            dataAccess.deleteRow(Integer.toString(PurchaseDisplayUI.this.purchase.purchaseID), "PurchaseID");
            if (parent != null) {
                parent.updateTable();
            }
            PurchaseDisplayUI.this.dispatchEvent(new WindowEvent(PurchaseDisplayUI.this, WindowEvent.WINDOW_CLOSING));
        }
    }

    public void run() {
        Dimension screen = Application.getInstance().getScreenSize();
        this.setLocation(screen.width/2-this.getSize().width/2, screen.height/2-this.getSize().height/2);
        this.pack();
        this.setVisible(true);
    }
}
