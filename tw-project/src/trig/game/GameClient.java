package trig.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

import trig.game.entity.DummyTriangle;
import trig.game.entity.Entity;
import trig.game.entity.UpdateListener;
import trig.game.entity.Visible;
import trig.listener.GameListener;
import trig.utility.Constants;
import trig.utility.Methods;
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

    public void tempEngineDemoRender(Graphics2D g)
    {
        /*
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Constants.WINDOW_DIMENSION.width, Constants.WINDOW_DIMENSION.height);
        */

        g.setFont(bigFont);
        g.setColor(Color.WHITE);
        g.drawString("Game-Mechanics", Constants.WINDOW_DIMENSION.width / 2 - 200, 325);
        g.setFont(lilFont);

        g.drawString("Entities Created: "+Long.toString(Methods.DummyVars.getLastEntityId())+" Max Entities living: ~"+Integer.toString(DummyTriangle.PROJECTILE_LIMIT*entities.size()+entities.size()), Constants.WORLD_COLLISION_PADDING-1, Constants.WINDOW_DIMENSION.height-Constants.WORLD_COLLISION_PADDING-1);
        for(Entity e: entities)
        {
            if(e instanceof Visible && e.isMapped())
            {
                ((Visible) e).draw(g);
            }
        }
    }

    public void tempEngineDemoUpdate()
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
            if(e instanceof UpdateListener)
            {
                ((UpdateListener) e).onTick();
            }
        }
    }


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
        /*        Random r = new Random();
		for(int i = 0; i < c.length; i++)
                    c[i] = new C((int) Math.abs(r.nextInt() % 800), (int) Math.abs(r.nextInt() % 640), 25);*/

        tempEngineDemoUpdate();
	}


	public void render(Graphics2D g)
	{
            /*
            Font f = new Font(Font.SANS_SERIF, Font.BOLD, 45);
            g.setFont(f);
            g.drawString("Game-Mechanics", Constants.WINDOW_DIMENSION.width/2 - 200, 325);
            */
            //g.setColor(new Color(135, 155, 175));
            
            //for(int i = 0; i < c.length; i++)
            //    g.drawOval(c[i].x, c[i].y, c[i].r, c[i].r);
            tempEngineDemoRender(g);
            
	}
    /*    //DELETE ME://///////////
        C [] c = new C[10]; 
	private class C { public C(int x, int y, int r){this.x = x; this.y = y; this.r = r;} public int x = 0; public int y = 0; public int r = 10;}
        
        //////////////////////*/
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
