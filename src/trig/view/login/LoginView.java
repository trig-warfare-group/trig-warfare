package trig.view.login;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import trig.view.GamePanel;

/**
 * Used to gain login-information from the user.
 * @author brody
 *
 */
public class LoginView
{
	private GamePanel panel;
	private JTextField usernameField;
	private JButton loginButton;
	
	public LoginView(GamePanel panel)
	{
		int h = trig.utility.Constants.WINDOW_DIMENSION.height / 2;
		int w = trig.utility.Constants.WINDOW_DIMENSION.width / 2;
		
		this.panel = panel;
		usernameField = new JTextField("Username");
		loginButton = new JButton("Login");
		
		panel.setLayout(null);
		usernameField.setBounds(w, h, 120, 35);
		loginButton.setBounds(w+130, h, 50, 35);
		panel.add(usernameField);
		panel.add(loginButton);
	}
	
	public JTextField getUsernameField()
	{
		return usernameField;
	}
	
	public JButton getLoginButton()
	{
		return loginButton;
	}
	
}
