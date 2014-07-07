package trig.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.Random;
import trig.listener.GameListener;
import trig.utility.Constants;
import trig.view.GameView;

/**
 * GameClient acts as a model for the client-data.
 * This is where all the major features of the game are present.
 * @author brody
 *
 */

public class GameClient 
{
	private GameView gameView;
	private GameListener inputHandler;
	private GameThread thread;
	private boolean gameRunning = true;
	
        
        
        
	public GameClient()
	{
            inputHandler = new GameListener();
            gameView = new GameView(this);
            thread = new GameThread();
            this.init();
        }
	
	/**
	 * Initializes the game.
	 */
	private void init() 
	{
            inputHandler.initListening(gameView);
            thread.start();
	}

	public void updateGame()
	{   
                Random r = new Random();
		for(int i = 0; i < c.length; i++)
                    c[i] = new C((int) Math.abs(r.nextInt() % 800), (int) Math.abs(r.nextInt() % 640), 25);
	}
	
	public void render(Graphics2D g)
	{
            Font f = new Font(Font.SANS_SERIF, Font.BOLD, 45);
            g.setFont(f);
            g.drawString("Game-Mechanics", Constants.WINDOW_DIMENSION.width/2 - 200, 325);
            
            g.setColor(new Color(135, 155, 175));
            
            for(int i = 0; i < c.length; i++)
                g.drawOval(c[i].x, c[i].y, c[i].r, c[i].r);
            
            
	}
        //DELETE ME://///////////
        C [] c = new C[10]; 
	private class C { public C(int x, int y, int r){this.x = x; this.y = y; this.r = r;} public int x = 0; public int y = 0; public int r = 10;}
        
        //////////////////////
	public void delayGame()
	{
		try {
			thread.sleep(Constants.FPS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private class GameThread extends Thread
	{
		@Override
		public void run() 
		{
			while(gameRunning)
			{
				updateGame();
				gameView.render();
				delayGame();
			}
		}
	}
}
