import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class LoadProductClientUI {
    public static final int FRAME_WIDTH = 600, FRAME_HEIGHT = 400;

    public JFrame view;

    public JButton btnLoad = new JButton("Load Product");

    public JTextField txtBarcode = new JTextField(30);

    Socket link;
    Scanner input;
    PrintWriter output;

    public LoadProductClientUI() {
        this.view = new JFrame();
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        view.setTitle("Load Product");

        Container pane = view.getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));

        JPanel line = new JPanel();
        line.add(new JLabel("Barcode"));
        line.add(txtBarcode);
        pane.add(line);

        pane.add(btnLoad);
        btnLoad.addActionListener(new LoadActionListener());


    }

    public static void main(String[] args) {
        LoadProductClientUI client = new LoadProductClientUI();
        client.view.pack();
        client.view.setVisible(true);
    }

    private class LoadActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int barcode = Integer.parseInt(txtBarcode.getText());

                link = new Socket("localhost", 1000);
                input = new Scanner(link.getInputStream());
                output = new PrintWriter(link.getOutputStream(), true);

                output.println(ProductServer.LOAD);
                output.println(barcode);

                String name = input.nextLine();
                if (name.equals(ProductServer.FAIL)) {
                    JOptionPane.showMessageDialog(null, "Can't find product for barcode " + barcode);
                }

                double price = Double.parseDouble(input.nextLine());
                double quantity = Double.parseDouble(input.nextLine());
                String supplier = input.nextLine();

                ProductModel res = new ProductModel(barcode, name, price, quantity, supplier);
                new EditProductClientUI(res).run();
            }
            catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Error reading barcode");
                return;
            }
            catch (Exception ex) {
                ex.printStackTrace();
                return;
            }
        }
    }

}
