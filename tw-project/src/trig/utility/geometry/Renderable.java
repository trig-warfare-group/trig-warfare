package trig.utility.geometry;

import trig.utility.math.vector.FloatCartesian;

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
     * Note: this function guarantees that the returned Rectangles contains the shape, but not that it is the smallest possible containing box.
     * @return a Rectangle that completely contains the shape rendered by the object.
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

//    abstract public void translate(float tX, float tY);

    abstract public void translate(FloatCartesian tVector);

    /**
     * Rotates an object about the origin 0,0.
     * This is the default behaviour in java.
     * To render an object about its center, #rotateAbout(float, float, float) with the coordinates of the center of this object,
     * or if using a class that implements trig.utility.geometry.Shape: trig.utility.geometry.Shape#centeredRotate(float)
     * @see #rotateAbout(float, float, float)
     * @see trig.utility.geometry.Shape#centeredRotate(float)
     * @param theta an angle to rotate by, expressed in radians.
     */
    abstract public void rotate(float theta);


//    /**
//     * Rotates the object about the specified center, instead of it's normal origin
//     * @param theta the angle to rotate by, in radians.
//    * @param cX the x-coordinate of the center to rotate about.
//    * @param cY the y-coordinate of the center to rotate about.
//    */
//    abstract public void rotateAbout(float theta, float cX, float cY);

    /**
     * Rotates the object about the specified center, instead of it's normal origin
     * @param theta the angle to rotate by, in radians.
     * @param tVector the x and y coordinates of the rotation-center
     */
    abstract public void rotateAbout(float theta, FloatCartesian tVector);

    /**
     * Produces a deep-copy of the object.
     *
     * @return a deep-copy of the object and it's components.
     */
    abstract public Renderable clone();
}
