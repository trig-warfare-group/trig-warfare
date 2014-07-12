package trig.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import trig.game.entity.DummyTriangle;
import trig.game.entity.Entity;
import trig.game.entity.TickActor;
import trig.game.entity.Visible;
import trig.listener.GameListener;
import trig.utility.Constants;
import trig.utility.Physics;
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

    //temp/cheap engine-demo stuff
    private ArrayList<Entity> entities = new ArrayList<Entity>(); //may use hashSet instead, idk;
    private Font bigFont = new Font(Font.SANS_SERIF, Font.BOLD, 45);
    private Font lilFont = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
    private int step = 0;

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
		if(step < 125){
		    if(step % 25 == 0)
		    {
		        entities.add(
		                new DummyTriangle() //++ should increment after use, right
		        );
		    }
		    step++;
		}
		for(Entity e: entities)
		{
		    if(e instanceof TickActor)
		    {
		        ((TickActor) e).onTick();
		    }
		}
	}


	public void render(Graphics2D g)
	{
        g.setColor(Color.green);
        g.setFont(lilFont);
        /*
         * Render time: get the current time taken before rendering the entities, 
         * and then get the time after...
         */
        long startTime = System.nanoTime();
        long endTime;
        
        g.drawString("Entities Created: "+Long.toString(Physics.DummyVars.getLastEntityId())+" Max Entities living: ~"+Integer.toString(DummyTriangle.PROJECTILE_LIMIT*entities.size()+entities.size()), Constants.WORLD_COLLISION_PADDING-1, Constants.WINDOW_DIMENSION.height-Constants.WORLD_COLLISION_PADDING-1);
        for(Entity e: entities)
        {	
            if(e instanceof Visible && e.isMapped())
            {
                ((Visible) e).draw(g);
            }
        }
        endTime = System.nanoTime();
        
        g.setColor(Color.green);// 1000 / 50 ms -- how many times per second = FPS
        g.drawString("Render time: " + ((double) (endTime - startTime) / 1000), 5, 30);
            
	}

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
