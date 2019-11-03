import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import com.google.gson.Gson;

public class EditClientUI {
    public static final int FRAME_WIDTH = 600, FRAME_HEIGHT = 400;

    public JFrame view;

    public JButton btnSave;

    // product fields
    public JTextField txtBarcode, txtName, txtPrice, txtQuantity, txtSupplier;
    // customer fields
    public JTextField txtCustID, txtCustName, txtEmail, txtPhone, txtAddress, txtPayInfo;

    Socket link;
    Scanner input;
    PrintWriter output;

    public EditClientUI(ProductModel product) {
        this.view = new JFrame();
        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        view.setTitle("Edit Product");

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

        btnSave = new JButton("Save Product");
        pane.add(btnSave);
        btnSave.addActionListener(new SaveProductListener());
    }

    public EditClientUI(CustomerModel customer) {
        this.view = new JFrame();
        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        view.setTitle("Edit Customer");

        txtCustID = new JTextField(Integer.toString(customer.customerID), 30);
        txtCustName = new JTextField(customer.name, 30);
        txtEmail = new JTextField(customer.email, 30);
        txtPhone = new JTextField(customer.phone, 30);
        txtAddress = new JTextField(customer.address, 30);
        txtPayInfo = new JTextField(customer.paymentInfo, 30);

        Container pane = view.getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));

        JPanel line = new JPanel();
        line.add(new JLabel("Customer ID"));
        line.add(txtCustID);
        pane.add(line);

        line = new JPanel();
        line.add(new JLabel("Name"));
        line.add(txtCustName);
        pane.add(line);

        line = new JPanel();
        line.add(new JLabel("Email"));
        line.add(txtEmail);
        pane.add(line);

        line = new JPanel();
        line.add(new JLabel("Phone"));
        line.add(txtPhone);
        pane.add(line);

        line = new JPanel();
        line.add(new JLabel("Address"));
        line.add(txtAddress);
        pane.add(line);

        line = new JPanel();
        line.add(new JLabel("Payment Info"));
        line.add(txtPayInfo);
        pane.add(line);

        btnSave = new JButton("Save Customer");
        pane.add(btnSave);
        btnSave.addActionListener(new SaveCustomerListener());
    }

    public void run() {
        view.pack();
        view.setVisible(true);
    }

    private class SaveProductListener implements ActionListener {
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

                Gson gson = new Gson();

                ProductModel product = new ProductModel(barcode, name, price, quantity, supplier);

                link = new Socket("localhost", 1000);
                input = new Scanner(link.getInputStream());
                output = new PrintWriter(link.getOutputStream(), true);

                MessageModel msg = new MessageModel();
                msg.code = MessageModel.PUT_PRODUCT;
                msg.data = gson.toJson(product);

                output.println(gson.toJson(msg));

                msg = gson.fromJson(input.nextLine(), MessageModel.class);

                if (msg.code == MessageModel.SUCCESS) {
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

    private class SaveCustomerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int customerID = Integer.parseInt(txtCustID.getText());
                String name = txtCustName.getText();
                String email = txtEmail.getText();
                String phone = txtPhone.getText();
                String address = txtAddress.getText();
                String payInfo = txtPayInfo.getText();

                if (name.length() == 0) {
                    JOptionPane.showMessageDialog(null, "Name cannot be empty");
                    return;
                }

                Gson gson = new Gson();

                CustomerModel customer = new CustomerModel(customerID, name, email, phone, address, payInfo);

                link = new Socket("localhost", 1000);
                input = new Scanner(link.getInputStream());
                output = new PrintWriter(link.getOutputStream(), true);

                MessageModel msg = new MessageModel();
                msg.code = MessageModel.PUT_CUSTOMER;
                msg.data = gson.toJson(customer);

                output.println(gson.toJson(msg));

                msg = gson.fromJson(input.nextLine(), MessageModel.class);

                if (msg.code == MessageModel.SUCCESS) {
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
