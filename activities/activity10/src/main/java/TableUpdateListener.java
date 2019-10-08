import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

class TableUpdateListener implements TableModelListener {

    DataAdapter dataAccess;

    public TableUpdateListener(DataAdapter dataAccess) {
        this.dataAccess = dataAccess;
    }
    @Override
    public void tableChanged(TableModelEvent e) {
        /* added second condition to shield against fireTableDataChanged() events */
        if (e.getType() == TableModelEvent.UPDATE && e.getColumn() != TableModelEvent.ALL_COLUMNS) {
            int row = e.getFirstRow();
            int col = e.getColumn();
            StoreTableModel model = (StoreTableModel) e.getSource();
            String id = model.getValueAt(row, 0);
            String update = model.getValueAt(row, col);
            dataAccess.updateValue(id, update, col, model.getFirstColumnName());
        }
        /* this case is when a table row is deleted, it only removes one row for now */
        else if (e.getType() == TableModelEvent.DELETE) {
            int row = e.getFirstRow();
            StoreTableModel model = (StoreTableModel) e.getSource();
            String id = model.getValueAt(row, 0);
            dataAccess.deleteRow(id, model.getFirstColumnName());
        }
        // else do nothing
    }
}