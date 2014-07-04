package trig.main.game;

import trig.controller.GameController;
import trig.view.GameView;

public class TrigWarfare 
{
	//private Player player;
	private GameView gameView;
	private GameController gameController;
	
	public TrigWarfare()
	{
		gameView = new GameView();
		gameController = new GameController();
		this.init();
	}

	private void init() 
	{
		gameController.setGameView(gameView);
		gameController.init();
	}
}
