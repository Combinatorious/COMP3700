import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class LoginUI {

    public static final int FRAME_WIDTH = 600, FRAME_HEIGHT = 400, FIELD_WIDTH = 20;

    public JFrame view;

    public JButton btnLogin = new JButton("Login");

    public JTextField txtUsername = new JTextField(FIELD_WIDTH);
    public JTextField txtPassword = new JPasswordField(FIELD_WIDTH);

    DataAdapter dataAccess;
    INetworkAdapter loginAdapter;

    int accessToken;

    public LoginUI() {
        this.view = new JFrame();

        dataAccess = Application.getInstance().getDataAdapter();
        loginAdapter = Application.getInstance().getNetworkAdapter();

        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        view.setTitle("Login");
        view.setSize(FRAME_WIDTH, FRAME_HEIGHT);

        Container pane = view.getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));

        JPanel line = new JPanel();
        line.add(new JLabel("Username"));
        line.add(txtUsername);
        pane.add(line);

        line = new JPanel();
        line.add(new JLabel("Password"));
        line.add(txtPassword);
        pane.add(line);

        pane.add(btnLogin);

        btnLogin.addActionListener(new LoginActionListener());

    }

    public void run() {
        this.view.pack();
        this.view.setVisible(true);
    }

    private class LoginActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            UserModel user = new UserModel();

            user.username = txtUsername.getText();
            user.password = txtPassword.getText();

            if (user.username.length() == 0 || user.password.length() == 0) {
                JOptionPane.showMessageDialog(null, "Username or password cannot be empty.");
                return;
            }

            // TODO: move all of this stuff into a DataAdapter client/server communicator
            UserModel res = loginAdapter.login(user);

            if (res == null)
                JOptionPane.showMessageDialog(null, "Invalid username or password! Access denied!");
            else {
                accessToken = res.ssid;
                JOptionPane.showMessageDialog(null, "Access granted with access token = " + accessToken);

                if (res.userType == UserModel.MANAGER) {
                    new ManagerUI(res).run();
                }
                else if (res.userType == UserModel.CASHIER) {
                    new CashierUI(res).run();
                }
                else if (res.userType == UserModel.CUSTOMER) {
                    new CashierUI(res).run();
                }
                else if (res.userType == UserModel.ADMIN) {
                    new AdminUI(res).run();
                }

                view.dispose();

            }

        }
    }
}
