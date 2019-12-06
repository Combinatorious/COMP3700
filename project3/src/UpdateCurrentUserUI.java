import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UpdateCurrentUserUI extends JFrame {

    public static final int FRAME_HEIGHT = 1200, FRAME_WIDTH = 800, FIELD_WIDTH = 30;

    DataAdapter dataAccess;
    UserModel user;

    JTextField txtUsername;
    JTextField txtPassword;

    JButton btnSave = new JButton("Save Changes");

    public UpdateCurrentUserUI () {
        this.setTitle("Change username or password");
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

        dataAccess = Application.getInstance().getDataAdapter();
        user = Application.getInstance().getCurrentUser();

        if (user == null) {
            // must have a user
            this.dispose();
        }

        txtUsername = new JTextField(user.username, FIELD_WIDTH);
        txtPassword = new JTextField(user.password, FIELD_WIDTH);

        JPanel field1 = new JPanel();
        field1.add(new JLabel("Username:"));
        field1.add(txtUsername);
        this.getContentPane().add(field1, Component.CENTER_ALIGNMENT);

        JPanel field2 = new JPanel();
        field2.add(new JLabel("Password:"));
        field2.add(txtPassword);
        this.getContentPane().add(field2, Component.CENTER_ALIGNMENT);



        JPanel button1 = new JPanel();
        button1.add(btnSave);
        this.getContentPane().add(button1, Component.CENTER_ALIGNMENT);

        btnSave.addActionListener(new SaveListener());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                UpdateCurrentUserUI.this.dispose();
            }
        });
    }

    public void run() {
        Dimension screen = Application.getInstance().getScreenSize();
        this.setLocation(screen.width/2-this.getSize().width/2, screen.height/2-this.getSize().height/2);
        this.pack();
        this.setVisible(true);
    }

    class SaveListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            String username = txtUsername.getText();
            String password = txtPassword.getText();

            if (username.equals("") || password.equals("")) {
                JOptionPane.showMessageDialog(UpdateCurrentUserUI.this,
                        "Username or password cannot be empty");
                return;
            }

            user.username = username;
            user.password = password;

            if (dataAccess.removeUser(user) == DataAdapter.ERROR
                    || dataAccess.saveUser(user) == DataAdapter.ERROR) {
                JOptionPane.showMessageDialog(UpdateCurrentUserUI.this,
                        "Something went wrong, you may not have an account anymore. Contact IT.");
                return;
            }

            JOptionPane.showMessageDialog(UpdateCurrentUserUI.this,
                    "Username and password updated.");

            UpdateCurrentUserUI.this.dispose();
        }
    }

}
