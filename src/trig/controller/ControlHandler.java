package trig.controller;

import trig.controller.sub.FrameController;
import trig.view.GameView;

public class ControlHandler 
{
	private GameController controller;
	//The Actual Controllers:
	private FrameController frameController;
	
	public ControlHandler(GameController controller)
	{
		this.controller = controller;
	}
	
	/**
	 * Initializes all the view-controllers.
	 */
	public void init()
	{
		frameController = new FrameController(controller.getGameView());
	}
	
}
