package trig.game.engine;

import trig.game.entity.*;
import trig.utility.Constants;
import trig.utility.Methods;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

/**
 * Created by marcos on 14/07/2014.
 * @author brody
 */
public class GameEngine
{
   // private ArrayList<Entity> entities = new ArrayList<Entity>();
    private Font bigFont = new Font(Font.SANS_SERIF, Font.BOLD, 45);
    private Font lilFont = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
    CollisionCheck col[];

    /**
     * Initialisation of the engine
     */
    public GameEngine()
    {
        //for(int i = 0; i < 50; i++)
          //  entities.add(new Combatant(0, 0, 10));
        col = new CollisionCheck[50];

        for(int i = 0; i < 50; i ++)
        {
            col[i] = new CollisionCheck(new Combatant(0, 0, 10));
        }
    }

    /**
     * Update the game-engine
     */
    public void update()
    {

        for(int i = 0; i < col.length; i++)
            ((Living)col[i]).update();


        //TODO Implement checking algorithm - which will be used in a collision-engine;

        for(int i = 0; i < col.length; i++)
        {
            Combatant cur = (Combatant) col[i].c;
            boolean check = false;

            //Check against others.
            for(int o = 0; o < col.length; o++)
            {
                Combatant l = col[o].c;
                //If not same entity
                if(cur != l)
                {
                    if(cur.hitbox.intersects((Rectangle)l.hitbox));
                        check = true;
                }
            }
            if(check == false)
                col[i].collided = false;
            else
                col[i].collided = true;
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
                + " Entities living: "+Long.toString(col.length),
                5, Constants.WINDOW_DIMENSION.height - 10);

        long start;
        long end;

        start = System.nanoTime();

        for(int i = 0; i < col.length; i++)
        {
            g.setColor(new Color(74, 198, 36));
            if(col[i].collided == true)
                g.setColor(new Color(174, 38, 36));
            ((Visible)col[i].c).render(g);
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
