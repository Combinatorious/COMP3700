import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddCustomerController implements ActionListener{
    private AddCustomerView myView;
    DataAdapter dataAccess;

    public AddCustomerController(AddCustomerView view, DataAdapter dataAccess) {
        myView = view;
        myView.saveButton.addActionListener(this);
        myView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.dataAccess = dataAccess;
        dataAccess.connect();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(myView, "I'll make sure to save that for you.");

        try {
            CustomerModel inputCustomer = new CustomerModel(
                    Integer.parseInt(myView.customerIDField.getText()),
                    myView.nameField.getText(),
                    myView.emailField.getText(),
                    myView.phoneField.getText(),
                    myView.addressField.getText(),
                    myView.paymentInfoField.getText()
            );

            dataAccess.saveCustomer(inputCustomer);
        }
        catch (Exception err) {
            err.printStackTrace();
        }

        myView.customerIDField.setText("");
        myView.nameField.setText("");
        myView.emailField.setText("");
        myView.phoneField.setText("");
        myView.addressField.setText("");
        myView.paymentInfoField.setText("");
    }
}
