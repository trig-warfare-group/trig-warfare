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
        for(Entity e: entities)
        {
            if(e instanceof UpdateListener)
            {
                ((UpdateListener) e).update(this);
            }
        }
    }

    public void render(Graphics2D g) //we ordinarily won't be calling this, ourselves, a state machine will
    {
        /*
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Constants.WINDOW_DIMENSION.width, Constants.WINDOW_DIMENSION.height);
        */

        g.setFont(bigFont);
        g.setColor(Color.WHITE);
        g.drawString("Game-Mechanics", Constants.WINDOW_DIMENSION.width / 2 - 200, 325);
        g.setFont(lilFont);
        g.drawString(
                "Entities Created: "+Long.toString(Methods.DummyVars.getLastEntityId())
                + " Entities living: "+Long.toString(entities.size()),
                Constants.WORLD_COLLISION_PADDING-1,
                Constants.WINDOW_DIMENSION.height-Constants.WORLD_COLLISION_PADDING-1
        );

        /*

            Entity rendering

         */
        Visible v;
        /*
            we'll possibly keep a drawable list in the entities list
            (and handle this via the add/removeEntity functions) eventually?
         */
        for(Entity e: entities)         {
            if(e instanceof Visible)
            {
                v = (Visible) e;
                /*
                    some entities can hide?
                    or just not be mapped yet?
                    either way I guess it works?
                    Is there a better way?
                 */
                g.setColor(v.getColor());
                if(v.isVisible()){

                    AffineTransform oldTransform = g.getTransform(); //store old transformation

                    g.translate((double) v.getX(), (double) v.getY());
                    g.rotate((double) v.getDirection());
                    g.draw(
                            v.getShape()
                    );

                    g.transform(oldTransform); //revert transformation
                }
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
