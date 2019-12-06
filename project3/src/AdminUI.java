import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AdminUI extends JFrame {
    public static final int FRAME_WIDTH = 300, FRAME_HEIGHT = 100;

    JButton btnManageUsers = new JButton("Manage Users");
    JButton btnUpdateUser = new JButton("Change my username/password");

    public AdminUI(UserModel user) {
        this.setTitle("Administration: " + user.username);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel line = new JPanel();
        line.add(btnManageUsers);
        this.getContentPane().add(line, Component.CENTER_ALIGNMENT);

        line = new JPanel();
        line.add(btnUpdateUser);
        this.getContentPane().add(line, Component.CENTER_ALIGNMENT);


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
                new UpdateCurrentUserUI().run();
            }
        });

    }

    public void run() {
        this.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        Dimension screen = Application.getInstance().getScreenSize();
        this.setLocation(screen.width/2-this.getSize().width/2, screen.height/2-this.getSize().height/2);
        this.pack();
        this.setVisible(true);
    }
}
