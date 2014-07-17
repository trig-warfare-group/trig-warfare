package trig.game.engine;

import trig.game.entity.dummy.DummyTriangle;
import trig.game.entity.interfaces.Entity;
import trig.game.entity.interfaces.UpdateListener;
import trig.game.entity.interfaces.Visible;
import trig.utility.Constants;
import trig.utility.Methods;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Iterator;

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


    //temp/cheap engine-demo stuff
    private ArrayList<Entity> entities = new ArrayList<Entity>(); //may use hashSet instead, idk;
    private Font bigFont = new Font(Font.SANS_SERIF, Font.BOLD, 45);
    private Font lilFont = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
    private int step = 0;
    /*
    todo: probably put the move and draw functions in here, tbh, and player control reaction. Not sure about the hp/stats functions, though?
    todo: HOWEVER, before putting movement here, we need to think about how to facilitate special movement patterns, such as arcs!
    */

    /*
        note: perhaps we could implement destruction more often and generalise the process of entity death a bit if we gave players a new craft each time they died?
        player death kinda needs a animation, IMO
    */

    /**
     * Initialisation of the state/engine/w.e
     */
    public GameEngine()
    {
        for(int i = 0; i < 5; i++)
            addEntity(
                    new DummyTriangle()
            );
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
    }

    /**
     * Performs the part of the rendering that deals with individual entities (not the HUD)
     * @param g Graphics2D object that can be used to draw on the panel
     * Full description:
     *      Each entity that can be drawn will pass forward an object containing data needed to draw it, including:
     *          a CustomPath as the basic polygon/image asset of the object
     *          The x and y coordinates to which to translate the RenderPaths
     *          The angle, in radians within the domain [-pi,pi] by which to rotate the points in the RenderPaths
     *          Possibly more data in the future:
     *              Possible a stat-rendering object, for things like text (e.g. name), something on the health or sheild, etc.
     *      Note that the path used for rendering etc is not explicitly the same as the data used for collisions, that will be a separate object
     * @see trig.utility.CustomPath
     *
     */
    public void renderEntities(Graphics2D g)
    {
        /*
            notes on custompath:
            needs a better name
            have a color object!
            Support subpaths (for colour object and ther data inheritance purposes)
            use Vectors for nodes/points (makes transforms such as rotation and translation easy?)
            May not be enclosed:
                (this could however be achieved: by referencing the first Vector as the last Vector, for example)
         */

        /*
            Note on stat-rendering:
                health etc should be a bar
         */
        Visible v;
        /*
            we'll possibly keep a drawable list in the entities list
            (and handle this via the add/removeEntity functions) eventually?
         */
        for (Entity e : entities)
        {
            if (e instanceof Visible)
            {
                v = (Visible) e;
            /*
                some entities can hide?
                or just not be mapped yet?
                either way I guess it works?
                Is there a better way?
             */

            //old code

                g.setColor(v.getColor());
                if (v.isVisible())
                {

                    g.translate((double) v.getX(), (double) v.getY());
                    g.rotate((double) v.getDirection());

                    g.draw(
                            v.getShape()
                    );

                /*
                    this reverse-transform results in too much rounding error to actually use
                    We need another way to do the draw that doesn't involve a full canvas transform, such as creating the shape in this function based on data from the entity
                    or
                    Having the entity create a shape that is already at the correct location and rotated.
                 */

                    g.rotate(-(double) v.getDirection());
                    g.translate(-(double) v.getX(), -(double) v.getY());
                }
            }
        }
    }
    public void render(Graphics2D g)
    {
        //important: we must always render entities first, then the HUD over the top

        renderEntities(g);

        /*
            TODO: FIGURE OUT HOW THE DEBUG VIEW FITS INTO THIS:
                IT NEEDS TO ADD TO ADD TO THE RENDERING OF BOTH ENTITIES AND THE HUD
                SO PART OF IT MUST BE DONE AFTER renderEntities AND PART OF IT AFTER renderHUD
        */

        renderHUD(g); //may not keep this hud rendering as a separate function, haven't decided.
    }

    /**
     * Renders the screen, like render(), but also includes debug outputs.
     * This might be an easy way to use a gameView object to enable debugging mode
     * @param g Graphics2D object that can be used to draw on the panel
     */
    public void debugRender(Graphics2D g)
    {
        //render entities normally
        renderEntities(g);


    }

    public synchronized void addEntity(Entity e)
    {
        entities.add(e);
    }

    public synchronized void removeEntity(Entity e){
        entities.remove(e);
    }
}
