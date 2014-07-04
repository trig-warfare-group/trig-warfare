package trig.controller.sub;

import trig.controller.sub.frame.FrameListener;
import trig.view.GameFrame;
import trig.view.GameView;

public class FrameController 
{
	
	private GameFrame frame;
	
	public FrameController(GameView gameView)
	{
		this.frame = gameView.getGameFrame();
		this.init();
	}
	
	private void init()
	{
		frame.addWindowListener(new FrameListener());
	}
}
