import javax.swing.*;

/*
    Java Swing add product view
 */
public class AddProductView extends JFrame {

    public static final int FRAME_HEIGHT = 1200, FRAME_WIDTH = 800;

    JTextField nameField = new JTextField(30);
    JTextField barcodeField = new JTextField(30);
    JTextField priceField = new JTextField(30);
    JTextField quantityField = new JTextField(30);
    JTextField supplierField = new JTextField(30);

    JButton saveButton = new JButton("Save Product");

    public AddProductView () {
        this.setTitle("Add Product");
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

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
    }
}
