import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownServiceException;

/*
 Main application of store management system.
 (Same function as StoreManager.java from class)
 */
public class Application {

    public static final String DEFAULT_DB = "Activity11.db";
    public static final String RELATIVE_PATH = "src/main/resources/databases/";

    DataAdapter adapter = null;
    String dbFileName;

    StoreServer server;

    private static Application instance = null;


    public static Application getInstance() {
        if (instance == null) {
            String dbFile = getUserFile();
            if (dbFile == null) {
                dbFile = DEFAULT_DB;
            }
            String dbType = "SQLite";
            if (dbFile.matches(".*\\.ora")) {
                dbType = "Oracle";
            }

            instance = new Application(dbType, dbFile);
        }
        return instance;
    }

    private Application(String db, String fileName) {
        dbFileName = fileName;
        if (db.equals("Oracle")) {
            adapter = new OracleWrapper();
        }
        else if (db.equals("SQLite")) {
            adapter = new SQLiteWrapper();
        }
	// connect here once and for all
        try {
            adapter.connect(fileName);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        server = new StoreServer();
        server.start();

        // set up a default admin for initial access
        UserModel admin = new UserModel();
        admin.username = "admin";
        admin.password = "admin";
        admin.userType = UserModel.ADMIN;
        adapter.saveUser(admin);
//        if (adapter.loadUser(admin) == null) {
//            adapter.saveUser(admin);
//        }


    }

    public void applicationWillTerminate() {
        adapter.disconnect();
    }

    public DataAdapter getDataAdapter() {
        return adapter;
    }

    public void setDataAdapter(DataAdapter aDataAdapter) {
        adapter = aDataAdapter;
    }

    public static void main(String[] args) {

        getInstance();
//        MainMenuUI mainMenu = new MainMenuUI();
//        mainMenu.run();
        new LoginUI().run();

    }

    private static String getUserFile() {
        SaveFileChooser fc = new SaveFileChooser(RELATIVE_PATH);
        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return fc.getSelectedFile().getName();
        }
        else return null;
    }

    public static class SaveFileChooser extends JFileChooser {
        JButton createNew = new JButton("Create new file");

        public SaveFileChooser(String currentDirectoryPath) {
            super(currentDirectoryPath);
            add(createNew);
            createNew.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showSaveDialog(null);
                }
            });
        }
    }
}
