import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class EditProductClientUI {
    public static final int FRAME_WIDTH = 600, FRAME_HEIGHT = 400;

    public JFrame view;

    public JButton btnSave = new JButton("Save Product");

    public JTextField txtBarcode, txtName, txtPrice, txtQuantity, txtSupplier;

    Socket link;
    Scanner input;
    PrintWriter output;

    public EditProductClientUI(ProductModel product) {
        this.view = new JFrame();
        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        view.setTitle("Edit product");

        txtBarcode = new JTextField(Integer.toString(product.barcode), 30);
        txtName = new JTextField(product.name, 30);
        txtPrice = new JTextField(Double.toString(product.price), 30);
        txtQuantity = new JTextField(Double.toString(product.quantity), 30);
        txtSupplier = new JTextField(product.supplier, 30);

        Container pane = view.getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));

        JPanel line = new JPanel();
        line.add(new JLabel("Barcode"));
        line.add(txtBarcode);
        pane.add(line);

        line = new JPanel();
        line.add(new JLabel("Name"));
        line.add(txtName);
        pane.add(line);

        line = new JPanel();
        line.add(new JLabel("Price"));
        line.add(txtPrice);
        pane.add(line);

        line = new JPanel();
        line.add(new JLabel("Quantity"));
        line.add(txtQuantity);
        pane.add(line);

        line = new JPanel();
        line.add(new JLabel("Supplier"));
        line.add(txtSupplier);
        pane.add(line);

        pane.add(btnSave);
        btnSave.addActionListener(new SaveActionListener());
    }

    public void run() {
        view.pack();
        view.setVisible(true);
    }

    private class SaveActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int barcode = Integer.parseInt(txtBarcode.getText());
                String name = txtName.getText();
                double price = Double.parseDouble(txtPrice.getText());
                double quantity = Double.parseDouble(txtQuantity.getText());
                String supplier = txtSupplier.getText();

                if (name.length() == 0 || supplier.length() == 0) {
                    JOptionPane.showMessageDialog(null, "A field cannot be empty");
                    return;
                }

                link = new Socket("localhost", 1000);
                input = new Scanner(link.getInputStream());
                output = new PrintWriter(link.getOutputStream(), true);

                output.println(ProductServer.SAVE);
                output.println(barcode);
                output.println(name);
                output.println(price);
                output.println(quantity);
                output.println(supplier);

                if (input.nextLine().equals(ProductServer.SUCCESS)) {
                    JOptionPane.showMessageDialog(null,"Success!");
                    view.dispose();
                }
                else {
                    JOptionPane.showMessageDialog(null, "Something went wrong");
                }


            }
            catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Error reading a value");
                return;
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
