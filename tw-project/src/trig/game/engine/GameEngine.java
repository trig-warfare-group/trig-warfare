package trig.game.engine;

import trig.game.world.*;
import trig.utility.Constants;
import trig.utility.math.vector.FloatCartesian;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Dummy/Demo/Draft engine for some testing, etc etc?
 * Created by marcos on 14/07/2014.
 */
//NOTE: MORE LIKE A "MATCH" OR SOMETHING THAN AN ENGINE, THE HOLE CLIENT RUNS THROUGH THE ENGINE.
public class GameEngine //may extend some GameState interface I think, not an exact name, but it's for the states that relate to rendering and updates and stuff.
{
     /*
        NOTE/TODO: THE PAUSED STATE CANNOT BY DEFAULT BE A SEPERATE STATE TO THE GAME ENGINE STUFF, IT MUST BE A SUBSTATE, PARTICULARLY IF THE GAME IS TO BE ONLINE.
        SINCE THE GAME'S SCREEN WILL NEED TO RENDER IN THE BACKGROUND, BUT EVENT BEHAVIOUR WILL CHANGE!
        MAYBE THIS COULD BE HANDLED BY HAVING A GAMEINPUTEVENTS AND PAUSEINPUTEVENTS FUNCTION OR SOMETHING?

    */

    private int objectsCreated = 0;

    private QuadTree quadTree;
    private ArrayList<WorldObject> worldObjects;

    /*
        collisionPossible: whether or not the quadTree returned one or more worldObjects with neighbours() for each world in each frame
        collisionOccurred: whether or not a collision actually occurred for each world in each frame

     */
    boolean[] collisionPossible, collisionOccurred;

    private Font bigFont, lilFont;

    public static final WorldEdge worldsEdge = new WorldEdge();
    public static final Rectangle worldBounds = worldsEdge.getHitbox().getBounds();

    /*
        note: perhaps we could implement destruction more often and generalise the process of world death a bit if we gave players a new craft each time they died?
        KeyInputTracker death kinda needs a animation, IMO
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

        worldObjects = new ArrayList<WorldObject>();
        bigFont = new Font(Font.SANS_SERIF, Font.BOLD, 45);
        lilFont = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
    }

    /**
     * Remove "trash" worldObjects
     * Note: this only removes the reference to an world from it's list, nothing fancy.
     * Seemed a "clean" way to remove worldObjects, keeps encapsulation, etc.
     */
    private void clean()
    {
        for(int i = 0; i < worldObjects.size(); i++)
        {
            if( worldObjects.get(i).isTrash() )
            {
                remove(i);
                i--; //decrement i since worldObjects got re-indexed
            }
        }
    }

    private void processCollisions(ArrayList<Collidable> collidables)
    {
        //second pass, closer precision test
        collisionPossible = new boolean[worldObjects.size()];
        collisionOccurred = new boolean[worldObjects.size()];

        ArrayList<Collidable> actualCollisions;
        ArrayList<Collidable> neighboursOfEach;

        Rectangle eachBounds;

        Collidable[] collisionArray;
        Collidable each;
        int i;
        for(i = 0; i < collidables.size(); i++)
        {
            each = collidables.get(i);
            FloatCartesian pointOutside = each.getHitbox().getOverflow(worldBounds);

            if (Math.abs(pointOutside.x) > 0 || Math.abs(pointOutside.y) > 0)
            {
                if (each instanceof Movable)
                {
                    FloatCartesian temp = FloatCartesian.mirror(pointOutside);
                    ((Movable) each).move(temp); //this should move precisely be the required amount?
                }
            }
        }
        ArrayList<ArrayList<Collidable>> possibleCollisions = quadTree.processList(collidables);
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
                    if ( eachHitbox.intersects(neighbour.getHitbox()) )
                    {
                        collisionOccurred[i] = true;
                        actualCollisions.add(neighbour);
                    }
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
        WorldObject e;

        ArrayList<Collidable> collidables = new ArrayList<Collidable>();

        for (int i = 0; i < worldObjects.size(); i++)
        {
            e = worldObjects.get(i);
            if (e instanceof Automaton)
            {
                ((Automaton) e).update();
            }

            if(e instanceof Collidable)
            {
                collidables.add( (Collidable) e );
            }
        }
        processCollisions(collidables);

        clean();
    }


    private void renderObjects(Graphics2D g)
    {
        /*
            we'll possibly keep a drawable list in the worldObjects list
            (and handle this via the sum/removeObject functions) eventually?
         */
        WorldObject e;
        for (int i = 0; i < worldObjects.size(); i++)
        {
            e = worldObjects.get(i);

            if (e instanceof Visible)
            {
                ((Visible) e).render(g);
            }

//            if (e instanceof Collidable)
//            {
//                g.setColor(Color.RED);
//                g.draw(((Collidable) e).getHitbox().getBounds());
//            }
        }
    }



    public void render(Graphics2D g)
    {
        long start;
        long end;

        start = System.nanoTime();

        g.setColor(Color.YELLOW);
        quadTree.render(g);

        renderObjects(g);
        Random r = new Random();
        //important: we must always render worldObjects first, then the HUD over the top
        g.setFont(bigFont);
        g.setColor(Color.DARK_GRAY);

        g.drawString("Game-Engine", Constants.WINDOW_DIMENSION.width / 2 - 175, 325);

        g.setColor(new Color(74, 198, 36));
        g.setFont(lilFont);

        g.drawString
        (
                "Objects Created: "+ objectsCreated
                        + " Objects living: "+Long.toString(worldObjects.size()),
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
                IT NEEDS TO ADD TO ADD TO THE RENDERING OF BOTH Objects AND THE HUD
                SO PART OF IT MUST BE DONE AFTER renderObjects AND PART OF IT AFTER renderHUD
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
//        //render worldObjects normally
//        renderObjects(g);
//
//        //all worldObjects should have debug stuff maybe?
//        for (Object e : worldObjects)
//        {
//            if (e instanceof Visible)
//            {
//                ((Visible) e).renderDebug(g); //will render the world's debug info, but maybe the engine should handle that itself
//            }
//        }


    }

    public synchronized void add(WorldObject e)
    {
        worldObjects.add(e);
        objectsCreated++;
    }

    public synchronized void remove(WorldObject e){
        worldObjects.remove(e);
    }

    public synchronized void remove(int i){
        worldObjects.remove(i);
    }

    public synchronized int indexOf(WorldObject e)
    {
        return worldObjects.indexOf(e);
    }

    public synchronized WorldObject get(int i)
    {
        return worldObjects.get(i);
    }

    public boolean contains(WorldObject e){
        return worldObjects.contains(e);
    }
}
