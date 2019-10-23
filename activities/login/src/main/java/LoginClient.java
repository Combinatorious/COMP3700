import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/* 
 * Activity 13 login client
 * Tripp Isbell 
 * cai0004@auburn.edu
 *
 * Interface with text field for username and password and
 * three buttons:
 * Login - the client attempts to login with credentials in fields
 * Logout - the client attempts to log out of the current user
 * Create - the client attempts to create an account with credentials
 */

public class LoginClient {
    public static final int FRAME_WIDTH = 600, FRAME_HEIGHT = 400;
    
    public JFrame view;

    public JButton btnLogin = new JButton("Login");
    public JButton btnLogout = new JButton("Logout");
    public JButton btnCreateAcct = new JButton("Create Account");
   

    public JTextField txtUsername = new JTextField(30);
    public JTextField txtPassword = new JTextField(30);

    Socket link;
    Scanner input;
    PrintWriter output;

    int accessToken;

    public LoginClient() {
	this.view = new JFrame();

	view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	view.setTitle("Login");
	view.setSize(FRAME_WIDTH, FRAME_HEIGHT);

	Container pane = view.getContentPane();
	pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));

	JPanel line = new JPanel();
	line.add(new JLabel("Username"));
	line.add(txtUsername);
	pane.add(line);

	line = new JPanel();
	line.add(new JLabel("Password"));
	line.add(txtPassword);
	pane.add(line);

	pane.add(btnLogin);
	pane.add(btnLogout);
	pane.add(btnCreateAcct);

	btnLogin.addActionListener(new LoginActionListener());

	btnLogout.addActionListener(new LogoutActionListener());

	btnCreateAcct.addActionListener(new CreateAcctActionListener());

    }

    public static void main(String[] args) {
	int port = 1000;
	LoginClient client = new LoginClient();
	client.view.pack();
	client.view.setVisible(true);
    }

    private class LoginActionListener implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {
	    String username = txtUsername.getText();
	    String password = txtPassword.getText();

	    if (username.length() == 0 || password.length() == 0) {
		JOptionPane.showMessageDialog(null, "Username or password cannot be empty");
		return;
	    }
	    
	    try {
		link = new Socket("localhost", 1000);
		input = new Scanner(link.getInputStream());
		output = new PrintWriter(link.getOutputStream(), true);

		output.println("LOGIN");
		output.println(username);
		output.println(password);
		accessToken = input.nextInt();

		System.out.println("Sent " + username + "/" + password + " received " + accessToken);
		if (accessToken == 0)
		    JOptionPane.showMessageDialog(null, "Invalid login credentials");
		else
		    JOptionPane.showMessageDialog(null, "Login successful, user is " + accessToken);
	    } catch (Exception ex) {
		ex.printStackTrace();
	    }
	}
    }

    private class LogoutActionListener implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {
	    try {
		link = new Socket("localhost", 1000);
		input = new Scanner(link.getInputStream());
		output = new PrintWriter(link.getOutputStream(), true);

		output.println("LOGOUT");
		output.println(accessToken);
		int res = input.nextInt();
		System.out.println("sent LOGOUT " + accessToken + " received " + res);

		if (res == 0)
		    JOptionPane.showMessageDialog(null, "Unable to logout");
		else
		    JOptionPane.showMessageDialog(null, "Logout successful");
	    } catch (Exception ex) {
		ex.printStackTrace();
	    }
	}
    }
    
    private class CreateAcctActionListener implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {
	    String username = txtUsername.getText();
	    String password = txtPassword.getText();

	    if (username.length() == 0 || password.length() == 0) {
		JOptionPane.showMessageDialog(null, "Username or password cannot be empty");
		return;
	    }
	    try {
		try {
		    link = new Socket("localhost", 1000);
		    input = new Scanner(link.getInputStream());
		    output = new PrintWriter(link.getOutputStream(), true);
		} catch (Exception ex) {
		    ex.printStackTrace();
		}

		output.println("CREATE");
		output.println(username);
		output.println(password);

		int res = input.nextInt();
		System.out.println("Sent " + username + "/" + password + " received " + res);

		if (res == 0)
		    JOptionPane.showMessageDialog(null, "Unable to create account");
		else
		    JOptionPane.showMessageDialog(null, "Account created, access code is " + res);
	    } catch (Exception ex) {
		ex.printStackTrace();
	    }
	}
    }
}
	
	
	
		
	
