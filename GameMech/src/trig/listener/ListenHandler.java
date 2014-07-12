package trig.listener;

import java.util.ArrayList;

import trig.listener.frame.FrameListener;
import trig.view.GameView;

public class ListenHandler implements Listener
{
	private GameView gameView;
	private ArrayList<Listener> controllerList = null;
	
	public ListenHandler(GameView gameView)
	{
		this.gameView = gameView;
		controllerList = new ArrayList<Listener>();
	}
	
	/**
	 * Initializes all the view-controllers.
	 */
	public void init()
	{
		controllerList.add(new FrameListener(gameView));
	}
	
	public ArrayList<Listener>getControllerList()
	{
		return controllerList;
	}
}
