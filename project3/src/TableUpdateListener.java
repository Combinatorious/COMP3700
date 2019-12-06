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
            StoreTableModel model = (StoreTableModel) e.getSource();
            // TODO: note this doesn't get old ID it gets new ID if ID is changed
            String id = model.getValueAt(row, 0);
            dataAccess.updateValue(model.getValueAt(row, 0),
                                        model.getRowAt(row),
                                        model.getFirstColumnName());
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