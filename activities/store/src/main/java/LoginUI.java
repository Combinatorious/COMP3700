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
    public static final int PORT = 1000;

    public JFrame view;

    public JButton btnLogin = new JButton("Login");

    public JTextField txtUsername = new JTextField(FIELD_WIDTH);
    public JTextField txtPassword = new JPasswordField(FIELD_WIDTH);

    Socket link;
    Scanner input;
    PrintWriter output;

    int accessToken;

    public LoginUI() {
        this.view = new JFrame();

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

            Gson gson = new Gson();

            MessageModel msg = new MessageModel();
            msg.code = MessageModel.LOGIN;
            msg.data = gson.toJson(user);

            SocketNetworkAdapter net = new SocketNetworkAdapter();

            try {
                msg = net.exchange(msg, "localhost", StoreServer.PORT);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            if (msg.code == MessageModel.ERROR)
                JOptionPane.showMessageDialog(null, "Invalid username or password! Access denied!");
            else {
                accessToken = msg.ssid;
                JOptionPane.showMessageDialog(null, "Access granted with access token = " + accessToken);

                user = gson.fromJson(msg.data, UserModel.class);

                if (user.userType == UserModel.MANAGER) {
                    new ManagerUI(user).run();
                }
                else if (user.userType == UserModel.CASHIER) {
                    new CashierUI(user).run();
                }
                else if (user.userType == UserModel.CUSTOMER) {
                    new CashierUI(user).run();
                }
                else if (user.userType == UserModel.ADMIN) {
                    new AdminUI(user).run();
                }

                view.dispose();

            }

        }
    }
}
