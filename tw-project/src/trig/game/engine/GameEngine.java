package trig.game.engine;

import trig.game.entity.*;
import trig.utility.Constants;
import trig.utility.Methods;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by marcos on 14/07/2014.
 * @author brody
 */
public class GameEngine
{
   // private ArrayList<Entity> entities = new ArrayList<Entity>();
    private Font bigFont = new Font(Font.SANS_SERIF, Font.BOLD, 45);
    private Font lilFont = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
    ArrayList<CollisionCheck> collisionChecks;

    /**
     * Initialisation of the engine
     */
    public GameEngine()
    {
        collisionChecks = new ArrayList<CollisionCheck>();
        Random r = new Random();
        for(int i = 0; i < 20; i ++)
        {
            collisionChecks.add(new CollisionCheck(new Combatant(r.nextInt(Constants.WORLD_DIM.width-Constants.WORLD_COLLISION_PADDING-1), r.nextInt(Constants.WORLD_DIM.height-Constants.WORLD_COLLISION_PADDING-1), 10)));
        }
    }

    /**
     * Update the game-engine
     */
    public void update()
    {
        for(CollisionCheck each : collisionChecks)
        {
            ((Living) each).update(); //cast to living redundant
        }



        //TODO Implement checking algorithm - which will be used in a collision-engine;
        boolean collided;
        for(CollisionCheck checker : collisionChecks)
        {
            collided = false;

            //Check against others.
            for(CollisionCheck other : collisionChecks)
            {
                //If not same entity
                if(checker != other)
                {
                    if(checker.c.hitbox.intersects((java.awt.geom.Rectangle2D) other.c.hitbox)) //you even put a ; at the end of the for loop and then had the loop-code on the next line? Unreadable..
                    {
                        collided = true;
                    }

                }
            }

            checker.collided = collided;
        }
    }

    public void render(Graphics2D g)
    {
        g.setFont(bigFont);
        g.setColor(Color.DARK_GRAY);

        g.drawString("Game-Engine", Constants.WINDOW_DIMENSION.width / 2 - 175, 325);

        g.setColor(new Color(74, 198, 36));
        g.setFont(lilFont);

        g.drawString(
                "Entities Created: "+Long.toString(Methods.DummyVars.getLastEntityId())
                + " Entities living: "+Long.toString(collisionChecks.size()),
                5, Constants.WINDOW_DIMENSION.height - 10);

        long start;
        long end;

        start = System.nanoTime();

        for(CollisionCheck each : collisionChecks)
        {
            g.setColor(each.collided ? Color.RED : Color.GREEN);
            ((Visible)each.c).render(g);
        }

        g.setColor(new Color(74, 198, 36));
        end = System.nanoTime();
        g.drawString("Render time: " + Long.toString((end - start) / 1000) + "ms",
                5,
                15);

    }

    public void addEntity(Entity e)
    {
       // entities.add(e);
    }

    public void removeEntity(Entity e){
        //entities.remove(e);
    }
}
