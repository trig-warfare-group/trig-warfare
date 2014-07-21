package trig.game.engine;

import trig.game.entity.dummy.DummyCircle;
import trig.game.entity.dummy.DummyTriangle;
import trig.game.entity.interfaces.Entity;
import trig.game.entity.interfaces.UpdateListener;
import trig.game.entity.interfaces.Visible;
import trig.utility.Constants;
import trig.utility.DummyMethods;
import trig.utility.math.QuadTree;
import trig.utility.math.vector.CartesianForm;
import trig.utility.math.vector.PolarForm;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Dummy/Demo/Draft engine for some testing, etc etc?
 * Created by marcos on 14/07/2014.
 */
public class GameEngine //may extend some GameState interface I think, not an exact name, but it's for the states that relate to rendering and updates and stuff.
{
     /*
        NOTE/TODO: THE PAUSED STATE CANNOT BY DEFAULT BE A SEPERATE STATE TO THE GAME ENGINE STUFF, IT MUST BE A SUBSTATE, PARTICULARLY IF THE GAME IS TO BE ONLINE.
        SINCE THE GAME'S SCREEN WILL NEED TO RENDER IN THE BACKGROUND, BUT EVENT BEHAVIOUR WILL CHANGE!
        MAYBE THIS COULD BE HANDLED BY HAVING A GAMEINPUTEVENTS AND PAUSEINPUTEVENTS FUNCTION OR SOMETHING?

    */

    private QuadTree quadTree;
    private ArrayList<Entity> entities;

    /*
        collisionPossible: whether or not the quadTree returned one or more entities with neighbours() for each entity in each frame
        collisionOccurred: whether or not a collision actually occurred for each entity in each frame

     */
    boolean[] collisionPossible, collisionOccurred;

    private Font bigFont, lilFont;

    /*
        note: perhaps we could implement destruction more often and generalise the process of entity death a bit if we gave players a new craft each time they died?
        player death kinda needs a animation, IMO
    */

    /**
     * Initialisation of the state/engine/w.e
     */
    public GameEngine()
    {
        quadTree = new QuadTree
                (
                        Constants.WORLD_DIM.width,
                        Constants.WORLD_DIM.height,
                        new float[]{0, 0}
                );

        entities = new ArrayList<Entity>();
        bigFont = new Font(Font.SANS_SERIF, Font.BOLD, 45);
        lilFont = new Font(Font.SANS_SERIF, Font.PLAIN, 12);

//        for(int i = 0; i < 5; i++)
//        {
//            addEntity(
//                    new DummyTriangle()
//            );
//        }
        for (int i = 0; i < 1; i++)
        {
            //quad-tree testing
            CartesianForm point;
            float angleAdjustment = ((float) 1 / 4) * (float) Math.PI;

            point = new CartesianForm(Constants.WORLD_DIM.width * 3 / 4, Constants.WORLD_DIM.height * 3 / 4);
            addEntity(
                    new DummyCircle(
                            (int) point.x - 50,
                            (int) point.y - 50,
                            new PolarForm(10, ((float) 9 / 8) * (float) Math.PI)
                    )
            );

            point = new CartesianForm(Constants.WORLD_DIM.width * 1 / 4, Constants.WORLD_DIM.height * 1 / 4);
            addEntity(
                    new DummyCircle(
                            (int) point.x - 50,
                            (int) point.y - 50,
                            new PolarForm(10, 0)
                    )
            );

            point = new CartesianForm(Constants.WORLD_DIM.width * 1 / 4, Constants.WORLD_DIM.height * 3 / 4);
            addEntity(
                    new DummyCircle(
                            (int) point.x - 50,
                            (int) point.y - 50,
                            new PolarForm(10, 0)
                    )
            );

            point = new CartesianForm(Constants.WORLD_DIM.width * 1 / 4, Constants.WORLD_DIM.height * 3 / 4);
            addEntity(
                    new DummyCircle(
                            (int) point.x - 50,
                            (int) point.y - 50,
                            new PolarForm(10, (float) 1.5 * (float) Math.PI)
                    )
            );

            point = new CartesianForm(Constants.WORLD_DIM.width * 3 / 4, Constants.WORLD_DIM.height * 3 / 4);
            addEntity(
                    new DummyCircle(
                            (int) point.x - 50,
                            (int) point.y - 50,
                            new PolarForm(0, 0)
                    )
            );
        }
    }

    /**
     * Do stuff on frame update, or w/e
     */
    public void update()
    {

        Entity e;
        for (int i = 0; i < entities.size(); i++)
        {
            e = entities.get(i);
            if (e instanceof UpdateListener)
            {
                ((UpdateListener) e).update(this);
            }
        }
        ArrayList<ArrayList<Entity>> possibleCollisions = quadTree.processList(entities);

        //second pass, closer precision test
        collisionPossible = new boolean[entities.size()];
        collisionOccurred = new boolean[entities.size()];
        float distX, distY, distH;

        for (int i = 0; i < collisionPossible.length; i++)
        {
            e = entities.get(i);
            ArrayList<Entity> neighboursOfE = possibleCollisions.get(i);
            if(neighboursOfE.size() > 0)
            {
                collisionPossible[i] = true;
                for (Entity each : neighboursOfE)
                {

                    distX = Math.abs(e.getX() + each.getX());
                    distY = Math.abs(e.getY() + each.getY());
                    distH = Math.round(Math.sqrt((distX * distX) + (distY * distY)));
                    if (distH < e.getHitSize())
                    {
                        collisionOccurred[i] = true;
                    }
                }
            }
        }

        //normally do it here quadTree.clear();
    }


    public void renderEntities(Graphics2D g)
    {
        /*
            we'll possibly keep a drawable list in the entities list
            (and handle this via the add/removeEntity functions) eventually?
         */
        Entity e;
        for (int i = 0; i < entities.size(); i++)
        {
            e = entities.get(i);

            if (e instanceof Visible)
            {
                ((Visible) e).render(g);
                int size = e.getHitSize();

                g.setColor( collisionPossible[i] ? Color.RED : Color.GREEN );
                g.drawRect(e.getX(), e.getY(), size, size);

                g.setColor( collisionOccurred[i] ? Color.RED : Color.GREEN );
                g.drawOval(e.getX(), e.getY(), size, size);
            }
        }
    }
    public void render(Graphics2D g)
    {
        long start;
        long end;

        start = System.nanoTime();

        g.setColor(Color.YELLOW);
        quadTree.render(g);

        renderEntities(g);
        Random r = new Random();
        //important: we must always render entities first, then the HUD over the top
        g.setFont(bigFont);
        g.setColor(Color.DARK_GRAY);

        g.drawString("Game-Engine", Constants.WINDOW_DIMENSION.width / 2 - 175, 325);

        g.setColor(new Color(74, 198, 36));
        g.setFont(lilFont);

        g.drawString
        (
                "Entities Created: "+Long.toString(DummyMethods.DummyVars.getLastEntityId())
                        + " Entities living: "+Long.toString(entities.size()),
                5, Constants.WINDOW_DIMENSION.height - 10
        );

        end = System.nanoTime();
        g.drawString
        (
                "Render time: " + Long.toString((end - start) / 1000) + "µs",
                5,
                15
        );

        /*
            TODO: FIGURE OUT HOW THE DEBUG VIEW FITS INTO THIS:
                IT NEEDS TO ADD TO ADD TO THE RENDERING OF BOTH ENTITIES AND THE HUD
                SO PART OF IT MUST BE DONE AFTER renderEntities AND PART OF IT AFTER renderHUD
        */

        //renderHUD(g); //may not keep this hud rendering as a separate function, haven't decided.
    }

    /**
     * Renders the screen, like render(), but also includes debug outputs.
     * This might be an easy way to use a gameView object to enable debugging mode
     * @param g Graphics2D object that can be used to draw on the panel
     */
    public void debugRender(Graphics2D g)
    {
//        //render entities normally
//        renderEntities(g);
//
//        //all entities should have debug stuff maybe?
//        for (Entity e : entities)
//        {
//            if (e instanceof Visible)
//            {
//                ((Visible) e).renderDebug(g); //will render the entity's debug info, but maybe the engine should handle that itself
//            }
//        }


    }

    public synchronized void addEntity(Entity e)
    {
        entities.add(e);
    }

    public synchronized void removeEntity(Entity e){
        entities.remove(e);
    }
}
