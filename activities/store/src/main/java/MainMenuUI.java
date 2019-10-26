import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainMenuUI extends JFrame {
    public static final int FRAME_HEIGHT = 1200, FRAME_WIDTH = 1000;

    JButton customerButton = new JButton("Customers");
    JButton productButton = new JButton("Products");
    JButton purchaseButton = new JButton("Record Transaction");
    JButton purchaseHistButton = new JButton("View Transaction History");

    public MainMenuUI() {
        this.setTitle("Main Menu");
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel button1 = new JPanel();
        button1.add(customerButton);
        this.getContentPane().add(button1);

        JPanel button2 = new JPanel();
        button2.add(productButton);
        this.getContentPane().add(button2);

        JPanel button3 = new JPanel();
        button3.add(purchaseButton);
        this.getContentPane().add(button3);

        JPanel button4 = new JPanel();
        button4.add(purchaseHistButton);
        this.getContentPane().add(button4);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Application.getInstance().applicationWillTerminate();
            }
        });

        customerButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                new CustomerUI().run();;
            }
        });

        productButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProductUI().run();
            }
        });

        purchaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddPurchaseUI().run();
            }
        });

        purchaseHistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TransactionHistoryUI().run();
            }
        });
    }

    public void run() {
        this.pack();
        this.setVisible(true);
    }
}
