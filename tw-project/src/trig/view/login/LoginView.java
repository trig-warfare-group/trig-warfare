package trig.view.login;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JTextField;

import trig.utility.Constants;
import trig.view.GamePanel;

/**
 * Used to gain login-information from the user.
 * 
 * @author brody
 * 
 */

//public class LoginView
//{
//	private GamePanel panel;
//    @Deprecated
//	private GameClient client;
//	private JTextField usernameField;
//	private JButton loginButton;
//	private ArrayList<Circle> circles;
//	Random r = new Random();
//
//	public LoginView(GamePanel panel, GameClient client)
//	{
//		int h = trig.utility.Constants.WINDOW_DIMENSION.height / 2;
//		int w = trig.utility.Constants.WINDOW_DIMENSION.width / 2;
//
//		this.panel = panel;
//		this.client = client;
//		usernameField = new JTextField("Username");
//		loginButton = new JButton("Login");
//
//		panel.setLayout(null);
//		usernameField.setBounds(w, h, 120, 35);
//		loginButton.setBounds(w + 130, h, 50, 35);
//		panel.add(usernameField);
//		panel.add(loginButton);
//		loginButton.addActionListener(new LoginListener());
//		circles = new ArrayList<Circle>();
//		init();
//	}
//
//	private void init()
//	{
//
//		for(int i = 0; i < 10; i++)
//			circles.add(new Circle((int) Math.abs(r.nextInt() % Constants.WINDOW_DIMENSION.width),
//					(int) Math.abs(r.nextInt() % Constants.WINDOW_DIMENSION.height),
//					15));
//	}
//
//	public JTextField getUsernameField()
//	{
//		return usernameField;
//	}
//
//	public JButton getLoginButton()
//	{
//		return loginButton;
//	}
//
//	/**
//	 * Built-in Login Functionality. Checks if the username has more than 3
//	 * letters, at least, and less than 10. Then attempts to login.
//	 */
//	private class LoginListener implements ActionListener
//	{
//		@Override
//		public void actionPerformed(ActionEvent e)
//		{
//			String username = usernameField.getText();
//			if (username.length() >= 3 && username.length() <= 10)
//			{
//				// Request the model to log in with the data.
//				client.login(username);
//
//			} else
//			{
//				// Otherwise, move focus to field;
//				usernameField.requestFocus();
//			}
//		}
//	}
//
//	public void update()
//	{
//		for(Circle c : circles)
//			c = new Circle((int) Math.abs(r.nextInt() % Constants.WINDOW_DIMENSION.width),
//					(int) Math.abs(r.nextInt() % Constants.WINDOW_DIMENSION.height),
//					15);
//	}
//
//	public void render(Graphics2D g)
//	{
//		//g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_RENDER_QUALITY);
//
//		for(Circle c : circles)
//			g.drawOval(c.x, c.y, c.r, c.r);
//	}
//
//	private class Circle
//	{
//		public Circle(int x, int y, int r)
//		{
//			this.x = x;
//			this.y = y;
//			this.r = r;
//		}
//
//		public int x = 0;
//		public int y = 0;
//		public int r = 10;
//	}
//
//}
