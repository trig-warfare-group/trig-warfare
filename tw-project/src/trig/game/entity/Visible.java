package trig.game.entity;

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
    public void draw();
}
