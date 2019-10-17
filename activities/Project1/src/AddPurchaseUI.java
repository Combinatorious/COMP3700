import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddPurchaseUI extends JFrame {

    public static final int FRAME_HEIGHT = 1200, FRAME_WIDTH = 800, FIELD_WIDTH = 30;

    JTextField purchaseIDField = new JTextField(FIELD_WIDTH);
    JTextField barcodeField = new JTextField(FIELD_WIDTH);
    JTextField customerIDField = new JTextField(FIELD_WIDTH);
    JTextField quantityField = new JTextField(FIELD_WIDTH);

    JButton addButton = new JButton("Add Purchase");

    public AddPurchaseUI() {
        this.setTitle("Add Purchase");
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

        JPanel field1 = new JPanel();
        field1.add(new JLabel("Purchase ID:"));
        field1.add(purchaseIDField);
        this.getContentPane().add(field1);

        JPanel field2 = new JPanel();
        field2.add(new JLabel("Barcode:"));
        field2.add(barcodeField);
        this.getContentPane().add(field2);

        JPanel field3 = new JPanel();
        field3.add(new JLabel("Customer ID:"));
        field3.add(customerIDField);
        this.getContentPane().add(field3);

        JPanel field4 = new JPanel();
        field4.add(new JLabel("Quantity:"));
        field4.add(quantityField);
        this.getContentPane().add(field4);

        JPanel button1 = new JPanel();
        button1.add(addButton);
        this.getContentPane().add(button1);

        addButton.addActionListener(new AddListener());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                AddPurchaseUI.this.dispose();
            }
        });
    }

    public void run() {
        this.pack();
        this.setVisible(true);
    }

    class AddListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            PurchaseModel newPurchase = new PurchaseModel(
                    Integer.parseInt(purchaseIDField.getText()),
                    Integer.parseInt(barcodeField.getText()),
                    Integer.parseInt(customerIDField.getText()),
                    Double.parseDouble(quantityField.getText())
            );
            new PurchaseDisplayUI(newPurchase, PurchaseDisplayUI.CONFIRM_TYPE).run();
            AddPurchaseUI.this.dispatchEvent(new WindowEvent(AddPurchaseUI.this, WindowEvent.WINDOW_CLOSING));
        }
    }
}
