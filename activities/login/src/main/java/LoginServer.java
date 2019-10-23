import java.io.PrintWriter;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Scanner;


/*
 * Activity 13 login server
 * Tripp Isbell
 * cai0004@auburn.edu
 *
 * Listens for a client to send actions:
 * LOGIN - log in to an existing account
 * 		returning access token if success, 0 if fail
 * LOGOUT - log out of a logged in account
 * 		returning 1 if success, 0 if fail
 * CREATE - create a new account with given credentials
 * 		returning access token if success, 0 if fail
 */

public class LoginServer {
    
    // Create activity13.db in the working directory
    public static final String url = "jdbc:sqlite:./activity13.db";
    
    public static void main(String[] args) {
		HashMap<Integer, String> loginUsers = new HashMap<Integer, String>();

		int port = 1000;
		try {
			ServerSocket server = new ServerSocket(port);

			while (true) {
				Socket pipe = server.accept();
				PrintWriter out = new PrintWriter(pipe.getOutputStream(), true);
				Scanner in = new Scanner(pipe.getInputStream());

				String command = in.nextLine();
				if (command.equals("LOGIN")) {
					String username = in.nextLine();
					String password = in.nextLine();

					System.out.println("Login with " + username + "/" + password);

					if (checkUser(username, password)) {
						int accessToken = username.hashCode();
						out.println(accessToken);
						loginUsers.put(accessToken, username);
					}
					else {
						out.println(0);
					}
				}

				else if (command.equals("LOGOUT")) {
					int token = in.nextInt();

					System.out.println("Logout with access token " + token);

					if (loginUsers.containsKey(token)) {
						loginUsers.remove(token);
						out.println(1); // logout successful
					}
					else {
						out.println(0); // logout unsuccessful
					}
				}

				else if (command.equals("CREATE")) {
					String username = in.nextLine();
					String password = in.nextLine();

					System.out.println("Create user " + username + "/" + password);

					if (addUser(username, password)) {
						int accessToken = username.hashCode();
						out.println(accessToken);
					}
					else {
						out.println(0); // error creating
					}
				}
				else {
					out.println(0);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
    }

    private static boolean checkUser(String username, String password) {
		Connection conn = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection(url);

			String sql = "SELECT * FROM Users WHERE Username = " + "\"" + username + "\""
			+ " AND Password = " + "\"" + password + "\"";

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {
			conn.close();
			return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
    }

    private static boolean addUser(String username, String password) {
	Connection conn = null;
	try {
		Class.forName("org.sqlite.JDBC");
	    conn = DriverManager.getConnection(url);

	    String create = "CREATE TABLE IF NOT EXISTS Users (\n"
		+ " Username text,\n"
		+ " Password text\n);";
	    
	    Statement stmt = conn.createStatement();
	    stmt.execute(create);

	    String insert = "INSERT INTO Users(Username, Password) VALUES ("
		+ "\'" + username + "\',\'" + password + "\')";
	    stmt.execute(insert);
	} catch (Exception ex) {
	    ex.printStackTrace();
	    return false;
	}
	return true;
    }

}
