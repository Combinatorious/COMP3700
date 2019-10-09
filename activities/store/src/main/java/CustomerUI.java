import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CustomerUI extends JFrame {
    public static final int FRAME_HEIGHT = 1200, FRAME_WIDTH = 800, FIELD_WIDTH = 30;

    DataAdapter dataAccess;

    StoreTableModel tableModel;

    JButton addCustomerButton = new JButton("Add Customer");


    public CustomerUI() {
        this.setTitle("Customer");
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

        dataAccess = Application.getInstance().getDataAdapter();
        dataAccess.connect(Application.getInstance().dbFileName);

        JPanel addButton = new JPanel();
        addButton.add(addCustomerButton);
        this.getContentPane().add(addButton);

        tableModel = new StoreTableModel(dataAccess.loadAllCustomers(), CustomerModel.COL_NAMES);

        tableModel.addTableModelListener(new TableUpdateListener(dataAccess));

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        JPanel tableFrame = new JPanel();
        tableFrame.add(scrollPane);
        this.getContentPane().add(tableFrame);

        addCustomerButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                AddCustomerUI addCustomer = new AddCustomerUI();
                addCustomer.run();
                addCustomer.parent = CustomerUI.this;
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dataAccess.disconnect();
                CustomerUI.this.dispose();
            }
        });

    }

    public void run() {
        this.pack();
        this.setVisible(true);
    }

    public void updateTable() {
        tableModel.setData(dataAccess.loadAllCustomers());
        tableModel.fireTableDataChanged();
    }
}
