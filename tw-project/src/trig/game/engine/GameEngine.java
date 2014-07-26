package trig.game.engine;

import trig.game.Player;
import trig.game.entity.*;
import trig.utility.Constants;
import trig.utility.DummyMethods;
import trig.utility.math.vector.FloatCartesian;
import trig.utility.math.vector.IntCartesian;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
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

    private int entitiesCreated = 0;

    private QuadTree quadTree;
    private ArrayList<Entity> entities;

    /*
        collisionPossible: whether or not the quadTree returned one or more entities with neighbours() for each entity in each frame
        collisionOccurred: whether or not a collision actually occurred for each entity in each frame

     */
    boolean[] collisionPossible, collisionOccurred;

    private Font bigFont, lilFont;

    public static final WorldEdge worldsEdge = new WorldEdge();
    public static final Rectangle worldBounds = worldsEdge.getHitbox().getBounds();

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
    }

    /**
     * Remove "trash" entities
     * Note: this only removes the reference to an entity from it's list, nothing fancy.
     * Seemed a "clean" way to remove entities, keeps encapsulation, etc.
     */
    public void clean()
    {
        for(int i = 0; i < entities.size(); i++)
        {
            if( entities.get(i).isTrash() )
            {
                removeEntity(i);
                i--; //decrement i since entities got re-indexed
            }
        }
    }

    public void processCollisions(ArrayList<Collidable> collidables)
    {
        Collidable each;
        ArrayList<ArrayList<Collidable>> possibleCollisions = quadTree.processList(collidables);

        //second pass, closer precision test
        collisionPossible = new boolean[entities.size()];
        collisionOccurred = new boolean[entities.size()];

        ArrayList<Collidable> actualCollisions;
        ArrayList<Collidable> neighboursOfEach;

        Rectangle eachBounds;

        Collidable[] collisionArray;

        int i;
        for (i = 0; i < possibleCollisions.size(); i++)
        {
            each = collidables.get(i);
            trig.utility.geometry.Polygon eachHitbox = each.getHitbox();
            neighboursOfEach = possibleCollisions.get(i);
            actualCollisions = new ArrayList<Collidable>();

            if (neighboursOfEach.size() > 0)
            {
                collisionPossible[i] = true;
                for (Collidable neighbour : neighboursOfEach)
                {
                    if (neighbour.getHitbox().intersects(eachHitbox))
                    {
                        collisionOccurred[i] = true;
                        actualCollisions.add(neighbour);
                    }
                }
            }

            FloatCartesian pointOutside = eachHitbox.getOverflowDistance(worldBounds);

            if (Math.abs(pointOutside.x) > 0 || Math.abs(pointOutside.y) > 0)
            {
                actualCollisions.add(worldsEdge);
                if (each instanceof Movable)
                {
                    int shiftX, shiftY;
                    if(pointOutside.x < 0)
                    {
                        shiftX = (int) Math.floor(pointOutside.x);
                    }
                    else
                    {
                        shiftX = (int) Math.ceil(pointOutside.x);
                    }

                    if(pointOutside.y < 0)
                    {
                        shiftY = (int) Math.floor(pointOutside.y);
                    }
                    else
                    {
                        shiftY = (int) Math.ceil(pointOutside.y);
                    }
                   ((Movable) each).move(shiftX, shiftY);
                }
            }

            collisionArray = new Collidable[actualCollisions.size()];
            actualCollisions.toArray(collisionArray);
            each.onCollision(collisionArray);
        }
    }

    /**
     * Do stuff on frame update, or w/e
     */
    public void update()
    {
        Entity e;

        ArrayList<Collidable> collidables = new ArrayList<Collidable>();

        for (int i = 0; i < entities.size(); i++)
        {
            e = entities.get(i);
            if (e instanceof Automata)
            {
                ((Automata) e).update(this);
            }

            if(e instanceof Collidable)
            {
                collidables.add( (Collidable) e );
            }
        }
        processCollisions(collidables);

        clean();
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
            }

            if (e instanceof Collidable)
            {
                g.setColor(Color.RED);
                g.draw(((Collidable) e).getHitbox().getBounds());
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
                "Entities Created: "+entitiesCreated
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
        entitiesCreated++;
    }

    public synchronized void removeEntity(Entity e){
        entities.remove(e);
    }

    public synchronized void removeEntity(int i){
        entities.remove(i);
    }

    public synchronized int indexOfEntity(Entity e)
    {
        return entities.indexOf(e);
    }

    public synchronized Entity getEntity(int i)
    {
        return entities.get(i);
    }

    public boolean containsEntity(Entity e){
        return entities.contains(e);
    }
}
