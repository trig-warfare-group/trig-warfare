package trig.utility.geometry;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

/**
 * An interface class designed to allow several classes,
 * including PathList and Path to be stored in the one object for the purpose of rendering,
 * as a function parameter, for example
 * Created by marcos on 18/07/2014.
 */
public interface Renderable
{
    /**
     * Returns a Path2D.Float representation of this object, which can be more easily drawn using Graphics2D
     */
    abstract public Path2D getPath2D();

    /**
     * Gets a Rectangle representing a box that completely contains the shape rendered by the object.
     * Note: in this case, "contains" just means that no portion of the shape lies outside of the box, there may be portions on the exact edge,
     *  these portions may technically render beyond the container, depending on the style of stroke used to render them.
     * @return a Rectangle that completely
     */
    abstract public Rectangle getBounds(); //TODO: NOT SURE IF TO USE RECTANGLE OR SRECTANGLE OR WHAT, DECIDE, DON'T DELETE ME UNTIL THEN

    /**
     * draws the object using 0,0 as the origin.
     * @param g the canvas to draw on
     */
    abstract public void render(Graphics2D g);
    /**
     * Draws the object, with the rendered object transformed using the specified AffineTransform
     * @param g the canvas to draw on
     * @param aT an affineTransform to apply to the rendered objected
     * @see trig.utility.math.vector.FloatCartesian
     */
    abstract public void render(Graphics2D g, AffineTransform aT);

    abstract public void translate(float tX, float tY);

    public void rotate(float theta);


    /**
     * Rotates the object about the specified center, instead of it's normal origin
     * @param theta the angle to rotate by, in radians.
     * @param cX the x-coordinate of the center to rotate about.
     * @param cY the y-coordinate of the center to rotate about.
     */
    public void rotateAbout(float theta, float cX, float cY);
}
