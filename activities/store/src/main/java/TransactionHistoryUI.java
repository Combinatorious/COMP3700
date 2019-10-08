import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class TransactionHistoryUI extends JFrame {

    public static final int FRAME_HEIGHT = 1200, FRAME_WIDTH = 800, FIELD_WIDTH = 30;

    DataAdapter dataAccess;

    StoreTableModel tableModel;

    public TransactionHistoryUI() {
        this.setTitle("Transaction History");
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

        dataAccess = Application.getInstance().getDataAdapter();
        dataAccess.connect(Application.getInstance().dbFileName);

        tableModel = new StoreTableModel(dataAccess.loadAllPurchases(), PurchaseModel.COL_NAMES);

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        tableModel.addTableModelListener(new TableUpdateListener(dataAccess));

        JPanel tableFrame = new JPanel();
        tableFrame.add(scrollPane);
        this.getContentPane().add(tableFrame);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dataAccess.disconnect();
                TransactionHistoryUI.this.dispose();
            }
        });



    }

    public void run() {
        this.pack();
        this.setVisible(true);
    }

    public void updateTable() {
        tableModel.setData(dataAccess.loadAllPurchases());
        tableModel.fireTableDataChanged();
    }



}
