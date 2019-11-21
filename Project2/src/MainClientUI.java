import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import com.google.gson.Gson;

public class MainClientUI {
    public static final int FRAME_WIDTH = 600, FRAME_HEIGHT = 400, FIELD_WIDTH = 30;

    public JFrame view;

    public JButton btnProd = new JButton("Load Product");
    public JButton btnCust = new JButton("Load Customer");

    public JTextField txtID = new JTextField(FIELD_WIDTH);

    Socket link;
    Scanner input;
    PrintWriter output;

    public MainClientUI() {
        this.view = new JFrame();
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        view.setTitle("Store Client");

        Container pane = view.getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));

        JPanel line = new JPanel();
        line.add(new JLabel("ID"));
        line.add(txtID);
        pane.add(line);

        pane.add(btnProd);
        btnProd.addActionListener(new LoadActionListener());

        pane.add(btnCust);
        btnCust.addActionListener(new LoadActionListener());


    }

    public static void main(String[] args) {
        MainClientUI client = new MainClientUI();
        client.view.pack();
        client.view.setVisible(true);
    }

    private class LoadActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String s = txtID.getText();
                if (s.length() != 0) { // load a product/customer
                    Gson gson = new Gson();
                    int id = Integer.parseInt(txtID.getText());

                    link = new Socket("localhost", 1000);
                    input = new Scanner(link.getInputStream());
                    output = new PrintWriter(link.getOutputStream(), true);

                    MessageModel msg = new MessageModel();
                    msg.data = Integer.toString(id);

                    if (e.getSource() == btnProd) {
                        msg.code = MessageModel.GET_PRODUCT;
                        output.println(gson.toJson(msg));
                        msg = gson.fromJson(input.nextLine(), MessageModel.class);

                        if (msg.code == MessageModel.SUCCESS) {
                            ProductModel product = gson.fromJson(msg.data, ProductModel.class);
                            new EditClientUI(product).run();
                        }
                        else {
                            JOptionPane.showMessageDialog(null,
                                    "Can't find product for barcode " + id);
                        }
                    }
                    else if (e.getSource() == btnCust) {
                        msg.code = MessageModel.GET_CUSTOMER;
                        output.println(gson.toJson(msg));
                        msg = gson.fromJson(input.nextLine(), MessageModel.class);

                        if (msg.code == MessageModel.SUCCESS) {
                            CustomerModel customer = gson.fromJson(msg.data, CustomerModel.class);
                            new EditClientUI(customer).run();
                        }
                        else {
                            JOptionPane.showMessageDialog(null,
                                    "Can't find customer for id " + id);
                        }
                    }
                    else {
                        msg.code = MessageModel.ERROR;
                        // I don't know we shouldn't get here
                    }

                }
                else { // empty id field, create a new object
                    if (e.getSource() == btnProd) {
                        new EditClientUI(new ProductModel()).run();
                    }
                    else {
                        new EditClientUI(new CustomerModel()).run();
                    }
                }

            }
            catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Error reading id");
                return;
            }
            catch (Exception ex) {
                ex.printStackTrace();
                return;
            }
        }
    }

}
