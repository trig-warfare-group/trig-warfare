package trig.game;

import java.awt.Graphics2D;

import trig.game.input.InputHandler;
import trig.game.state.BasicStateMachine;
import trig.game.state.LoginMachine;
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
	private InputHandler inputHandler;
	private GameThread thread;
	private boolean gameRunning = true;
	
	public GameClient()
	{
		stateManager = new StateManager();
		gameView = new GameView(this);
		inputHandler = new InputHandler();
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
		
	}
	
	public void render(Graphics2D g)
	{
		g.drawString("A", 25, 20);
	}
	
	public void delayGame()
	{
		try {
			thread.sleep(Constants.FPS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
