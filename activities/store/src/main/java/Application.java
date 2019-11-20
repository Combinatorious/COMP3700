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

    private static Application instance = null;


    public static Application getInstance() {
        if (instance == null) {
        	// TODO: move getUserFile to server app
            //String dbFile = getUserFile();
            //if (dbFile == null) {
            //    dbFile = DEFAULT_DB;
            //}
            //String dbType = "SQLite";
            // TODO: remove this Oracle stuff, its 2019 no one is migrating to Oracle
            //if (dbFile.matches(".*\\.ora")) {
            //    dbType = "Oracle";
            //}

            instance = new Application("Server", "");
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

        // don't connect for now if we're using server
        if (!db.equals("Server")) {
            try {
                adapter.connect(fileName);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        // set up a default admin for initial access

        UserModel admin = new UserModel("admin","admin", UserModel.ADMIN);
        admin.username = "admin";
        admin.password = "admin";
        admin.userType = UserModel.ADMIN;
        adapter.saveUser(admin);

    }

    public void applicationWillTerminate() {
    	// TODO: only disconnect if using local storage
        // right now it won't matter since connect/disconnect not implemented for server
        adapter.disconnect();
    }

    public DataAdapter getDataAdapter() {
        return adapter;
    }

    public void setDataAdapter(DataAdapter aDataAdapter) {
        adapter = aDataAdapter;
    }

    public INetworkAdapter getNetworkAdapter() {
        if (adapter != null && adapter.getClass() == SocketNetworkAdapter.class) {
            return (INetworkAdapter) adapter;
        }
        else return null;
    }

    public static void main(String[] args) {

        getInstance();
        // I don't currently have login functioning with local database so only do it if we're using server
        if (getInstance().getNetworkAdapter() != null) {
            new LoginUI().run();
        }
        else {
            new MainMenuUI().run();
        }

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
