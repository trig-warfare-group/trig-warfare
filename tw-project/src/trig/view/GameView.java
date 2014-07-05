package trig.view;

import javax.swing.JFrame;
import javax.swing.JPanel;

import trig.game.GameClient;
import trig.utility.Constants;

public class GameView 
{
	private GameFrame frame;
	private GamePanel panel;

	public GameView(GameClient gameModel)
	{

		frame = new GameFrame();
		frame.addModel(gameModel);
		panel = new GamePanel();
		panel.addModel(gameModel);
		
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
	
}
