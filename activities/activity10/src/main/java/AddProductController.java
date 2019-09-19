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
        myView.nameField.setText("");
        myView.barcodeField.setText("");
        myView.priceField.setText("");
        myView.quantityField.setText("");
        myView.supplierField.setText("");
    }
}
