import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AdminUI extends JFrame {
    public static final int FRAME_HEIGHT = 1200, FRAME_WIDTH = 1000;

    JButton btnConfig = new JButton("System Configuration");
    JButton btnAddUser = new JButton("Add User");
    JButton btnRemoveUser = new JButton("Remove User");

    public AdminUI(UserModel user) {
        this.setTitle("Administration: " + user.username);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel line = new JPanel();
        line.add(btnConfig);
        this.getContentPane().add(line);

        line = new JPanel();
        line.add(btnAddUser);
        this.getContentPane().add(line);

        line = new JPanel();
        line.add(btnRemoveUser);
        this.getContentPane().add(line);


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // TODO: logout
                Application.getInstance().applicationWillTerminate();
            }
        });

        btnConfig.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                // new ConfigUI().run();;
                // TODO: configUI to change database file
            }
        });

        btnAddUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddUserUI().run();
            }
        });

        btnRemoveUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // new RemoveUserUI().run();
            }
        });
    }

    public void run() {
        this.pack();
        this.setVisible(true);
    }
}
