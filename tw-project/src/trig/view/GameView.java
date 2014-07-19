package trig.view;

import trig.game.GameClient;
import trig.utility.Constants;
import trig.view.login.LoginView;
import java.awt.*;

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
        this.client = gameClient;
    }

    /**
     * Initializes the components to display
     */
    public void init()
    {
        frame.setTitle(Constants.GAME_TITLE + " - " + Constants.AUTHOR);
        panel.setBackground(new Color(7, 22, 0));

        //must be setPrefferedSize to be recognised by frame.pack();
        panel.setPreferredSize(Constants.WINDOW_DIMENSION);
        frame.getContentPane().add(panel);
        frame.setResizable(false);

        //setResizable(false); needs to be done before pack() for correct behaviour
        frame.pack();

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
