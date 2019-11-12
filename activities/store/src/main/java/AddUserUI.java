import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddUserUI extends JFrame {

    public static final int FRAME_WIDTH = 1200, FRAME_HEIGHT = 800, FIELD_WIDTH = 20;

    public static final String[] userTypes = {"Customer", "Cashier", "Manager", "Admin"};

    JTextField txtUsername;
    JTextField txtPassword;
    JComboBox<String> comboUserType;
    JTextField txtCustomerID;
    JButton btnSave;

    public AddUserUI() {

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.setTitle("Add User");

        txtUsername = new JTextField(FIELD_WIDTH);
        txtPassword = new JTextField(FIELD_WIDTH);
        comboUserType = new JComboBox<String>(userTypes);
        txtCustomerID = new JTextField(FIELD_WIDTH);

        Container pane = this.getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));

        JPanel line = new JPanel();
        line.add(new JLabel("Username"));
        line.add(txtUsername);
        pane.add(line);

        line = new JPanel();
        line.add(new JLabel("Password"));
        line.add(txtPassword);
        pane.add(line);

        line = new JPanel();
        line.add(new JLabel("User Type"));
        line.add(comboUserType);
        pane.add(line);

        line = new JPanel();
        line.add(new JLabel("Customer ID"));
        line.add(txtCustomerID);
        pane.add(line);


        btnSave = new JButton("Save User");
        pane.add(btnSave);
        btnSave.addActionListener(new SaveUserListener());
    }

    public class SaveUserListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = txtUsername.getText();
            String password = txtPassword.getText();
            int userType = comboUserType.getSelectedIndex();
            int customerID = 0;
            if (userType == UserModel.CUSTOMER) {
                customerID = Integer.parseInt(txtCustomerID.getText());
            }

            if (username.length() == 0 || password.length() == 0) {
                JOptionPane.showMessageDialog(null, "Error, username or password cannot be empty.");
                return;
            }

            Gson gson = new Gson();

            UserModel user = new UserModel();
            user.username = username;
            user.password = password;
            user.userType = userType;
            user.customerID = customerID;

            MessageModel msg = new MessageModel();
            msg.code = MessageModel.PUT_USER;
            msg.data = gson.toJson(user);

            SocketNetworkAdapter net = new SocketNetworkAdapter();

            try {
                msg = net.exchange(msg, "localhost", StoreServer.PORT);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }

            if (msg.code == MessageModel.ERROR) {
                JOptionPane.showMessageDialog(null, "Error adding user");
            }
            else {
                JOptionPane.showMessageDialog(null, "Success");
            }

            AddUserUI.this.dispose();
        }
    }

    public void run() {
        this.pack();
        this.setVisible(true);
    }
}
