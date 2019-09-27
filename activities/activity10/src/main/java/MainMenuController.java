import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuController implements ActionListener {
    MainMenuView myView;
    DataAdapter dataAccess;

    public MainMenuController(MainMenuView view, DataAdapter dataAccess) {
        myView = view;
        myView.addCustomerButton.addActionListener(this);
        myView.addProductButton.addActionListener(this);
        myView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.dataAccess = dataAccess;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == myView.addCustomerButton) {
            AddCustomerView view = new AddCustomerView();
            view.pack();
            view.setVisible(true);
            AddCustomerController ctrl = new AddCustomerController(view, dataAccess);
        }
        else {
            AddProductView view = new AddProductView();
            view.pack();
            view.setVisible(true);
            AddProductController ctrl = new AddProductController(view, dataAccess);
        }
    }


}
