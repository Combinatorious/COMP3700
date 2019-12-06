import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddPurchaseUI extends JFrame {

    public static final int FRAME_HEIGHT = 1200, FRAME_WIDTH = 800, FIELD_WIDTH = 30;

    JTextField purchaseIDField;
    JTextField barcodeField = new JTextField(FIELD_WIDTH);
    JTextField customerIDField;
    JTextField quantityField = new JTextField(FIELD_WIDTH);

    JButton addButton = new JButton("Add Purchase");

    public AddPurchaseUI(int customerID) {
        this.setTitle("Add Purchase");
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

        if (customerID != 0) {
            customerIDField = new JTextField(Integer.toString(customerID), FIELD_WIDTH);
            customerIDField.setEditable(false);
        }
        else {
            customerIDField = new JTextField(FIELD_WIDTH);
        }

        int numPurchases = Application.getInstance().getDataAdapter().loadAllPurchases().length;
        // lol thats one way to do it

        purchaseIDField = new JTextField(Integer.toString(numPurchases + 1) , FIELD_WIDTH);

        JPanel field1 = new JPanel();
        field1.add(new JLabel("Purchase ID:"));
        field1.add(purchaseIDField);
        this.getContentPane().add(field1, Component.CENTER_ALIGNMENT);

        JPanel field2 = new JPanel();
        field2.add(new JLabel("Barcode:"));
        field2.add(barcodeField);
        this.getContentPane().add(field2, Component.CENTER_ALIGNMENT);

        JPanel field3 = new JPanel();
        field3.add(new JLabel("Customer ID:"));
        field3.add(customerIDField);
        this.getContentPane().add(field3, Component.CENTER_ALIGNMENT);

        JPanel field4 = new JPanel();
        field4.add(new JLabel("Quantity:"));
        field4.add(quantityField);
        this.getContentPane().add(field4, Component.CENTER_ALIGNMENT);

        JPanel button1 = new JPanel();
        button1.add(addButton);
        this.getContentPane().add(button1, Component.CENTER_ALIGNMENT);

        addButton.addActionListener(new AddListener());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                AddPurchaseUI.this.dispose();
            }
        });
    }

    public void run() {
        Dimension screen = Application.getInstance().getScreenSize();
        this.setLocation(screen.width/2-this.getSize().width/2, screen.height/2-this.getSize().height/2);
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
