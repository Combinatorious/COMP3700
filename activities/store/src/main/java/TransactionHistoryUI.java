import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TransactionHistoryUI extends JFrame {

    public static final int FRAME_HEIGHT = 1200, FRAME_WIDTH = 800, FIELD_WIDTH = 30;

    DataAdapter dataAccess;

    StoreTableModel tableModel;

    JTable table; // ReceiptTriggerListener needs access to this

    public TransactionHistoryUI() {
        this.setTitle("Transaction History");
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

        dataAccess = Application.getInstance().getDataAdapter();

        tableModel = new StoreTableModel(dataAccess.loadAllPurchases(), PurchaseModel.COL_NAMES);

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        tableModel.addTableModelListener(new TableUpdateListener(dataAccess));

        JPanel tableFrame = new JPanel();
        tableFrame.add(scrollPane);
        this.getContentPane().add(tableFrame);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                TransactionHistoryUI.this.dispose();
            }
        });

        table.addMouseListener(new ReceiptTriggerListener());

    }

    class ReceiptTriggerListener extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            if (table == null)
                return;
            int r = table.rowAtPoint(e.getPoint());
            if (r >= 0 && r < table.getRowCount()) {
                table.setRowSelectionInterval(r, r);
            } else {
                table.clearSelection();
            }
            int rowIndex = table.getSelectedRow();
            if (rowIndex < 0)
                return;
            if (e.isPopupTrigger() && e.getComponent() instanceof JTable ) {
                PurchaseModel purchase = PurchaseModel.getPurchaseFromStringArray(tableModel.getRowAt(rowIndex));
                PurchaseDisplayUI receipt = new PurchaseDisplayUI(purchase, PurchaseDisplayUI.DELETE_TYPE);
                receipt.parent = TransactionHistoryUI.this;
                receipt.run();
            }
        }
        public void mouseReleased(MouseEvent e) {
        // do nothing
        }
        public void mouseClicked(MouseEvent e) {
        // nop
        }
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
