package trig.view;

import javax.swing.JFrame;

import trig.game.GameClient;

public class GameFrame extends JFrame
{
	private GameClient gameModel = null;
	
	public void addModel(GameClient gameModel) 
	{
		this.gameModel = gameModel;
	}

}
