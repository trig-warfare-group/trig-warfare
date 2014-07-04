package trig.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import trig.utility.Constants;

public class GameView 
{
	private GameFrame frame;
	private GamePanel panel;

	public GameView()
	{

		frame = new GameFrame();
		panel = new GamePanel();
		
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
		frame.setVisible(true);
	}

	public GameFrame getGameFrame() 
	{
		return frame;
	}
}
