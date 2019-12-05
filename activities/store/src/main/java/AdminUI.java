import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AdminUI extends JFrame {
    public static final int FRAME_HEIGHT = 1200, FRAME_WIDTH = 1000;

    JButton btnManageUsers = new JButton("Manage Users");
    JButton btnUpdateUser = new JButton("Change my username/password");

    public AdminUI(UserModel user) {
        this.setTitle("Administration: " + user.username);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel line = new JPanel();
        line.add(btnManageUsers);
        this.getContentPane().add(line);

        line = new JPanel();
        line.add(btnUpdateUser);
        this.getContentPane().add(line);


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // TODO: logout
                Application.getInstance().applicationWillTerminate();
            }
        });

        btnManageUsers.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                new UserUI().run();
            }
        });

        btnUpdateUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //new UpdateCurrentUserUI().run(); TODO: implement this for admin/manager/cashier/customer
            }
        });

    }

    public void run() {
        this.pack();
        this.setVisible(true);
    }
}
