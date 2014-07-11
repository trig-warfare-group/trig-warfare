package trig.game.entity;
//NOTE: probably better to have some class with draw methods corresponding to each object or something rather than these imports in lots of places? idk.
import java.awt.*;

/**
 * Entities that are visible and can be drawn on the map.
 * Are sort of like draw-listeners?, (no event data though), should this be moved to a listener folder?!
 * Created by marcos on 8/07/2014.
 */
public interface Visible {

    /**
     * Method to draw the object on the screen
     * does not, for now rely on external image assets etc
     */
    abstract public void draw(Graphics2D g);
}
