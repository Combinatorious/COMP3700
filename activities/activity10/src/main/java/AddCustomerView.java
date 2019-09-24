import javax.swing.*;

public class AddCustomerView extends JFrame {

    public static final int FRAME_HEIGHT = 1200, FRAME_WIDTH = 800;
    public static final int FIELD_WIDTH = 30;

    JTextField nameField = new JTextField(FIELD_WIDTH);
    JTextField customerIDField = new JTextField(FIELD_WIDTH);
    JTextField emailField = new JTextField(FIELD_WIDTH);
    JTextField phoneField = new JTextField(FIELD_WIDTH);
    JTextField addressField = new JTextField(FIELD_WIDTH);
    JTextField paymentInfoField = new JTextField(FIELD_WIDTH);

    JButton saveButton = new JButton("Save Customer");

    public AddCustomerView() {
        this.setTitle("Add Customer");
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

        JPanel field1 = new JPanel();
        field1.add(new JLabel("Name:"));
        field1.add(nameField);
        this.getContentPane().add(field1);

        JPanel field2 = new JPanel();
        field2.add(new JLabel("Customer ID:"));
        field2.add(customerIDField);
        this.getContentPane().add(field2);

        JPanel field3 = new JPanel();
        field3.add(new JLabel("Email:"));
        field3.add(emailField);
        this.getContentPane().add(field3);

        JPanel field4 = new JPanel();
        field4.add(new JLabel("Phone:"));
        field4.add(phoneField);
        this.getContentPane().add(field4);

        JPanel field5 = new JPanel();
        field5.add(new JLabel("Address:"));
        field5.add(addressField);
        this.getContentPane().add(field5);

        JPanel field6 = new JPanel();
        field6.add(new JLabel("Payment Info:"));
        field6.add(paymentInfoField);
        this.getContentPane().add(field6);

        JPanel button1 = new JPanel();
        button1.add(saveButton);
        this.getContentPane().add(button1);

    }
}
