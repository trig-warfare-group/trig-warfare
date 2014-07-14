package trig.game.entity;
//NOTE: probably better to have some class with draw methods corresponding to each object or something rather than these imports in lots of places? idk.
import java.awt.*;

/**
 * Entities that are visible and can be drawn on the map.
 * Are sort of like draw-listeners?, (no event data though), should this be moved to a listener folder?!
 * Created by marcos on 8/07/2014.
 *
 * //TODO: CHANGE FROM HAVING A METHOD TO DEFINING SOME SHAPE OBJECT THAT CAN BE USED BY A SINGLE PAINT METHOD, ETC.?
 */
public interface Visible {

    /**
     * Method to draw the object on the screen
     * does not, for now rely on external image assets etc
     */
    //abstract public void draw(Graphics2D g);

    /**
     * Gets the Shape that represents the Entity visually
     * @return a Shape object for the game to draw
     */
    abstract public Shape getShape();
    /*
        NEEDS:
        public twPolygon getPolygon();
        public float getFacingDir();
        the gist is basically having a polygon class, which is a series of PolarVectors, which can then have their angle adjusted by the angle from getFacingDir(), and be converted to a CartesianVector, which can then be drawn.
    */

    /**
     * Gets the visual facing-direction of the entity.
     * @return an angle, expressed in radians, by which to rotate the entity's shape for drawing, defaults to 0 (west)
     */
    abstract public float getDirection();

    /**
     * Determines if the entity is currently visible. Allows entities to change visibility dynamically.
     * QUESTION: WOULD AN EMPTY SHAPE BE AS EFFICIENT/MORE?
     * @return
     */
    abstract public boolean isVisible();





}
