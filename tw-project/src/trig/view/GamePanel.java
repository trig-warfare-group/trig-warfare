package trig.view;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import trig.client.GameClient;
import trig.client.Main;

public class GamePanel extends JPanel
{
	
	//private GameClient client = null;
	
	@Override
	public void paintComponent(Graphics g)
	{
        super.paintComponent(g);
        Main.MAIN.render((Graphics2D) g);
	}
	
//	public void addModel(GameClient gameModel)
//	{
//		this.client = gameModel;
//	}
}
