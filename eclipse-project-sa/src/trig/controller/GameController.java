package trig.controller;

import trig.view.GameView;

public class GameController 
{
	private GameView gameView;
	private ControlHandler handler;
	
	public GameController()
	{
		handler = new ControlHandler(this);
		this.gameView = null;
	}
	
	public void init()
	{
		handler.init();
	}
	
	public GameController(GameView gameView)
	{
		handler = new ControlHandler(this);
		this.gameView = gameView;
	}
	
	public void setGameView(GameView gameView)
	{
		this.gameView = gameView;
	}
	
	public GameView getGameView()
	{
		return gameView;
	}
}
