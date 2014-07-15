package trig.view;

import trig.game.GameClient;
import trig.utility.Constants;
import trig.view.login.LoginView;

public class GameView 
{
	private GameFrame frame;
	private GamePanel panel;
	private GameClient client;

	public GameView(GameClient gameClient)
	{

		frame = new GameFrame();
		frame.addModel(gameClient);
		panel = new GamePanel();
		panel.addModel(gameClient);
		this.client = client;
		this.init();
	}
	
	/**
	 * Initializes the components to display
	 */
	private void init() 
	{
		frame.setTitle(Constants.GAME_TITLE + " - " + Constants.AUTHOR);
		frame.setSize(Constants.WINDOW_DIMENSION);
		
		frame.getContentPane().add(panel);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	public GameFrame getGameFrame() 
	{
		return frame;
	}
	
	public GamePanel getGamePanel()
	{
		return panel;
	}

	public void render()
	{
		panel.repaint();
	}
	
	public LoginView constructLoginView()
	{
		return new LoginView(panel, client);
	}
	
}
