package trig.game;

import java.awt.Graphics2D;
import trig.game.state.StateManager;
import trig.utility.Constants;
import trig.view.GameView;

/**
 * GameClient acts as a model for the client-data.
 * This is where all the major features of the game are present.
 * @author brody
 *
 */

public class GameClient 
{
	private StateManager stateManager;
	private GameView gameView;
	private GameThread thread;
	private boolean gameRunning = true;
	
	public GameClient()
	{
		gameView = new GameView(this);
		stateManager = new StateManager(gameView);
		thread = new GameThread();
		this.init();
	}
	
	/**
	 * Initializes the game.
	 */
	private void init() 
	{	
		thread.start();
	}

	public void updateGame()
	{
		//stateManager.update();
	}
	
	public void render(Graphics2D g)
	{
		stateManager.render(g);
	}
	
	public void delayGame()
	{
		try {
			thread.sleep(Constants.FPS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void login(String username)
	{
		stateManager.login(username);
	}
	
	private class GameThread extends Thread
	{
		@Override
		public void run() 
		{
			while(gameRunning)
			{
				updateGame();
				gameView.render();
				delayGame();
			}
		}
	}


}
