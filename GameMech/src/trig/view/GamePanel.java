package trig.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import trig.game.GameClient;

public class GamePanel extends JPanel
{
	
	private GameClient gameModel = null;
	
	public GamePanel()
	{
		this.setBackground(new Color(13, 46, 24));
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		gameModel.render((Graphics2D) g);
	}
	
	public void addModel(GameClient gameModel) 
	{
		this.gameModel = gameModel;
	}

}
