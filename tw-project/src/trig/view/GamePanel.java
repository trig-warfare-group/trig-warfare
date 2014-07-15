package trig.view;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import trig.game.GameClient;
import trig.view.login.LoginView;

public class GamePanel extends JPanel
{
	
	private GameClient client = null;
	
	@Override
	public void paintComponent(Graphics g)
	{
        super.paintComponent(g);
		client.render((Graphics2D) g);
	}
	
	public void addModel(GameClient gameModel) 
	{
		this.client = gameModel;
	}
}
