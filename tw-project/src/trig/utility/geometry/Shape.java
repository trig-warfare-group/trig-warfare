package trig.utility.geometry;

import trig.utility.math.vector.FloatCartesian;

import java.awt.*;

/**
 * A shape, etc, intended to be used with renderable
 * Note: The inner renderable components that make up a shape must become static once entering the shape,
 * As such, and
 * Created by marcos on 22/07/2014.
 */
public interface Shape
{
    abstract public float getHeight();
    abstract public float getWidth();

    abstract public float getX();
    abstract public float getY();

    abstract public void setX();
    abstract public void setY();

    /**
     * gets the coordinate of the top-left corner of the shape
     * @return the top-left corner of
     */
    abstract FloatCartesian getLocation();

    /**
     * Sets the coordinate of the location of the shape
     * @param TLCorner a vector in FloatCartesian form, representing the coordinates of the new top left corner of the shape
     */
    abstract void setLocation(FloatCartesian TLCorner);

    /**
     * Sets the coordinate of the location of the shape
     * @param x the x-coordinate of the new top-left corner for the shape
     * @param y the y-coordinate of the new top-left corner for the shape
     */
    abstract void setLocation(float x, float y);

    /**
     * Sets the coordinate of the location of the shape
     * @param TLCorner a Point, representing the coordinates of the new top left corner of the shape
     */
    abstract void setLocation(Point TLCorner);


}
