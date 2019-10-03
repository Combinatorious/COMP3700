import javax.swing.table.AbstractTableModel;


public class StoreTableModel extends AbstractTableModel {
    private String[] columnNames;
    private String[][] data;

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
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public String getValueAt(int row, int col) {
        return data[row][col];
    }

    public boolean isCellEditable(int row, int col) {
        return true;
    }
}
