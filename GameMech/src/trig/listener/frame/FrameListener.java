package trig.listener.frame;

import trig.listener.Listener;
import trig.view.GameFrame;
import trig.view.GameView;

public class FrameListener implements Listener
{
	
	private GameFrame frame;
	
	public FrameListener(GameView gameView)
	{
		this.frame = gameView.getGameFrame();
		this.init();
	}
	
	private void init()
	{
		frame.addWindowListener(new CloseListener());
	}
}
