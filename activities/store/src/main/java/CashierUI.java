import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CashierUI extends JFrame {
    public static final int FRAME_HEIGHT = 1200, FRAME_WIDTH = 1000;
    /* Constructor code to distinguish cashiers (0) from regular customers */
    public static final int CASHIER = 0;

    JButton btnPurchase = new JButton("Record Transaction");
    JButton btnPurchaseHist = new JButton("Purchase History");

    public CashierUI(UserModel user) {
        if (user.userType == UserModel.CASHIER) {
            this.setTitle("Cashier: " + user.username);
        }
        else {
            this.setTitle("Customer: " + user.username);
        }
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel line = new JPanel();
        line.add(btnPurchase);
        this.getContentPane().add(line);

        line = new JPanel();
        line.add(btnPurchaseHist);
        this.getContentPane().add(line);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Application.getInstance().applicationWillTerminate();
            }
        });

        btnPurchase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddPurchaseUI().run();
            }
        });

        btnPurchaseHist.addActionListener(new ActionListener() {
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
