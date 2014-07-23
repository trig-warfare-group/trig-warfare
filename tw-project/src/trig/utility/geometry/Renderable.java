package trig.utility.geometry;

import trig.utility.math.vector.Cartesian;

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

    abstract public Rectangle getBounds(); //could SRectangle be used instead? (decide later?)

    /**
     * draws the object using 0,0 as the origin.
     * @param g the canvas to draw on
     */
    abstract public void render(Graphics2D g);
    /**
     * Draws the object, with the rendered object transformed using the specified AffineTransform
     * @param g the canvas to draw on
     * @param aT an affineTransform to apply to the rendered objected
     * @see trig.utility.math.vector.Cartesian
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
