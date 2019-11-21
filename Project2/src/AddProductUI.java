import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/*
    Java Swing add product view
 */
public class AddProductUI extends JFrame {

    public static final int FRAME_HEIGHT = 1200, FRAME_WIDTH = 800, FIELD_WIDTH = 30;

    DataAdapter dataAccess;
    ProductUI parent;

    JTextField nameField = new JTextField(FIELD_WIDTH);
    JTextField barcodeField = new JTextField(FIELD_WIDTH);
    JTextField priceField = new JTextField(FIELD_WIDTH);
    JTextField quantityField = new JTextField(FIELD_WIDTH);
    JTextField supplierField = new JTextField(FIELD_WIDTH);

    JButton saveButton = new JButton("Save Product");

    public AddProductUI () {
        this.setTitle("Add Product");
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

        dataAccess = Application.getInstance().getDataAdapter();

        JPanel field1 = new JPanel();
        field1.add(new JLabel("Name:"));
        field1.add(nameField);
        this.getContentPane().add(field1);

        JPanel field2 = new JPanel();
        field2.add(new JLabel("Barcode:"));
        field2.add(barcodeField);
        this.getContentPane().add(field2);

        JPanel field3 = new JPanel();
        field3.add(new JLabel("Price:"));
        field3.add(priceField);
        this.getContentPane().add(field3);

        JPanel field4 = new JPanel();
        field4.add(new JLabel("Quantity:"));
        field4.add(quantityField);
        this.getContentPane().add(field4);

        JPanel field5 = new JPanel();
        field5.add(new JLabel("Supplier:"));
        field5.add(supplierField);
        this.getContentPane().add(field5);

        JPanel button1 = new JPanel();
        button1.add(saveButton);
        this.getContentPane().add(button1);

        saveButton.addActionListener(new SaveListener());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                AddProductUI.this.dispose();
            }
        });
    }

    public void run() {
        this.pack();
        this.setVisible(true);
    }

    class SaveListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            JOptionPane.showMessageDialog(AddProductUI.this, "I'll make sure to save that for you.");

            try {
                ProductModel inputProduct = new ProductModel(
                        Integer.parseInt(barcodeField.getText()),
                        nameField.getText(),
                        Double.parseDouble(priceField.getText()),
                        Double.parseDouble(quantityField.getText()),
                        supplierField.getText()
                );

                dataAccess.saveProduct(inputProduct);
            }
            catch (Exception err) {
                err.printStackTrace();
            }

            nameField.setText("");
            barcodeField.setText("");
            priceField.setText("");
            quantityField.setText("");
            supplierField.setText("");

            if (parent != null) {
                parent.updateTable();
            }
        }
    }

}
