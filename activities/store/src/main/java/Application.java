import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownServiceException;

/* 
	TODO: I want to move all local database functionality out of here and the subsequent UIs
	to do this I need to have a separate server application that runs the server and talks to
	the database, and it needs to provide the same front end that my DataAdapter does.

	The easiest way to go about doing this is to create a client/serverDataAdapter implementation 
*/

/*
 Main application of store management system.
 (Same function as StoreManager.java from class)
 */
public class Application {

    public static final String DEFAULT_DB = "store.db";
    public static final String RELATIVE_PATH = "src/main/resources/databases/";

    DataAdapter adapter = null;
    String dbFileName;

    StoreServer server;

    private static Application instance = null;


    public static Application getInstance() {
        if (instance == null) {
        	// TODO: move getUserFile to server app
            String dbFile = getUserFile();
            if (dbFile == null) {
                dbFile = DEFAULT_DB;
            }
            String dbType = "SQLite";
            // TODO: remove this Oracle stuff, its 2019 no one is migrating to Oracle and we
            // haven't actually implemented an Oracle access layer.
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
        else if (db.equals("Server")) {
            adapter = new SocketNetworkAdapter();
        }

	// connect here once and for all

        // TODO: if !db.equals("Server")
        try {
            adapter.connect(fileName);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        server = new StoreServer();
        server.start();

        // set up a default admin for initial access

        UserModel admin = new UserModel("admin","admin", UserModel.ADMIN);
        admin.username = "admin";
        admin.password = "admin";
        admin.userType = UserModel.ADMIN;
        adapter.saveUser(admin);

    }

    public void applicationWillTerminate() {
    	// TODO: only disconnect if using local storage
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

    // TODO: move this into the server application
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
