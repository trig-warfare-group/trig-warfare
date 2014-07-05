package trig.listener;

import java.util.ArrayList;

import trig.view.GameView;

public class GameListener 
{
	private ListenHandler handler;
	
	public void initListening(GameView gameView)
	{
		handler = new ListenHandler(gameView);
		handler.init();
	}
	
	public ArrayList<Listener> getListenerList()
	{
		return handler.getControllerList();
	}
}	
	
