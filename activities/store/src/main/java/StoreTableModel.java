import javax.swing.table.AbstractTableModel;


public class StoreTableModel extends AbstractTableModel {

    private String[] columnNames;
    private String[][] data;
    public boolean editable = true;

    public StoreTableModel(String[][] data, String[] colNames) {
        super();
        this.data = data;
        this.columnNames = colNames;
    }

    public void setData(String[][] newData) {
        data = newData;
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        if (data == null) return 0;
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public String getValueAt(int row, int col) {
        return data[row][col];
    }

    /* Quick fix for erroneous update behavior on id column is to make it uneditable
     * However a consequence of this is that usernames cannot be edited in users table, oh well
     */
    public boolean isCellEditable(int row, int col) {
        return col != 0 && editable;
    }

    public void setValueAt(Object value, int row, int col) {
        data[row][col] = (String) value;
        fireTableCellUpdated(row, col);
    }

    public void setEditable(boolean value) {
        editable = value;
    }

    /* Used to identify type of table */
    public String getFirstColumnName() {
        return columnNames[0];
    }

    public String[] getRowAt(int row) {
        return data[row];
    }
}
