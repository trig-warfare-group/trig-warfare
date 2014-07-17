package trig.view;

import javax.swing.*;

import trig.game.GameClient;
import trig.utility.Constants;

import java.awt.*;

public class GameView 
{
    //NOTE: should possibly use swing instead of awt??
	private GameFrame frame;
	private GamePanel panel;

	public GameView(GameClient gameModel)
	{

		frame = new GameFrame();
		frame.addModel(gameModel);
		panel = new GamePanel();
		panel.addModel(gameModel);
        panel.setBorder(BorderFactory.createEmptyBorder());
        panel.setPreferredSize(Constants.WINDOW_DIMENSION);
        panel.setSize(Constants.WINDOW_DIMENSION);
        panel.setBackground(Color.BLACK);

        this.init();
	}
	
	/**
	 * Initializes the components to display
	 */
	private void init() 
	{
		frame.setTitle(Constants.GAME_TITLE + " - " + Constants.AUTHOR);

        panel.setPreferredSize(Constants.WINDOW_DIMENSION);
		frame.getContentPane().add(panel);
        panel.setBorder(BorderFactory.createEmptyBorder());
		frame.setResizable(false);
        frame.pack(); //PACK AFTER MAKING RESIZABLE FALSE, OR GET UNWANTED BORDER-EDGE-THING
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
