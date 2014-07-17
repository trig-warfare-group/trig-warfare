package trig.game.engine;

import trig.game.entity.dummy.DummyTriangle;
import trig.game.entity.interfaces.Entity;
import trig.game.entity.interfaces.UpdateListener;
import trig.game.entity.interfaces.Visible;

import java.awt.*;
import java.util.ArrayList;

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


    //vector/cheap engine-demo stuff
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


    public void renderEntities(Graphics2D g)
    {
        /*
            we'll possibly keep a drawable list in the entities list
            (and handle this via the add/removeEntity functions) eventually?
         */
        for (Entity e : entities)
        {
            if (e instanceof Visible)
            {
                ((Visible) e).render(g);
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

        //all entities should have debug stuff maybe?
        for (Entity e : entities)
        {
            if (e instanceof Visible)
            {
                ((Visible) e).renderDebug(g); //will render the entity's debug info, but maybe the engine should handle that itself
            }
        }


    }

    public synchronized void addEntity(Entity e)
    {
        entities.add(e);
    }

    public synchronized void removeEntity(Entity e){
        entities.remove(e);
    }
}
