import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuUI extends JFrame {
    public static final int FRAME_HEIGHT = 1200, FRAME_WIDTH = 1000;

    JButton addCustomerButton = new JButton("Add Customer");
    JButton addProductButton = new JButton("Add Product");

    public MainMenuUI() {
        this.setTitle("Main Menu");
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel button1 = new JPanel();
        button1.add(addCustomerButton);
        this.getContentPane().add(button1);

        JPanel button2 = new JPanel();
        button2.add(addProductButton);
        this.getContentPane().add(button2);

        addCustomerButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                new AddCustomerUI().run();;
            }
        });

        addProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddProductUI().run();
            }
        });
    }

    public void run() {
        this.pack();
        this.setVisible(true);
    }
}
