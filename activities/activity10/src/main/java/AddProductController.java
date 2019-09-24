import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * Add product view controller
 */
public class AddProductController implements ActionListener{
    private AddProductView myView;

    public AddProductController(AddProductView view) {
        myView = view;
        myView.saveButton.addActionListener(this);
        myView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(myView, "I'll make sure to save that for you.");

        try {
            ProductModel inputProduct = new ProductModel(
                    Integer.parseInt(myView.barcodeField.getText()),
                    myView.nameField.getText(),
                    Double.parseDouble(myView.priceField.getText()),
                    Double.parseDouble(myView.quantityField.getText()),
                    myView.supplierField.getText()
            );


            SQLiteWrapper dataAccess = new SQLiteWrapper();
            dataAccess.connect();
            dataAccess.saveProduct(inputProduct);
        }
        catch (Exception err) {
            err.printStackTrace();
        }

        myView.nameField.setText("");
        myView.barcodeField.setText("");
        myView.priceField.setText("");
        myView.quantityField.setText("");
        myView.supplierField.setText("");
    }
}
