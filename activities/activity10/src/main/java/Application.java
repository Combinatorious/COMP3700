import javax.swing.*;

/*
 Main application of store management system.
 (Same function as StoreManager.java from class)
 */
public class Application {
    DataAdapter adapter = null;
    private static Application instance = null;

    public static Application getInstance() {
        if (instance == null) {
            instance = new Application("SQLite");
        }
        return instance;
    }

    private Application(String db) {
        if (db.equals("Oracle")) {
            //adapter = new OracleDataAdapter();
        }
        else if (db.equals("SQLite")) {
            adapter = new SQLiteWrapper();
        }
        try {
            adapter.connect();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DataAdapter getDataAdapter() {
        return adapter;
    }

    public void setDataAdapter(DataAdapter aDataAdapter) {
        adapter = aDataAdapter;
    }

    public static void main(String[] args) {

        MainMenuUI mainMenu = new MainMenuUI();
        mainMenu.run();

    }
}
