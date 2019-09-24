import javax.swing.*;

public class MainMenuView extends JFrame {
    public static final int FRAME_HEIGHT = 1200, FRAME_WIDTH = 800;

    JButton addCustomerButton = new JButton("Add Customer");
    JButton addProductButton = new JButton("Add Product");

    public MainMenuView() {
        this.setTitle("Main Menu");
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

        JPanel button1 = new JPanel();
        button1.add(addCustomerButton);
        this.getContentPane().add(button1);

        JPanel button2 = new JPanel();
        button2.add(addProductButton);
        this.getContentPane().add(button2);
    }
}
