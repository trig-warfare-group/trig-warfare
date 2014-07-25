package trig.utility.geometry;

import trig.utility.math.vector.FloatCartesian;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;

/**
 * Custom path class designed to use the FloatCartesian vector class from my vector library.
 * It is essentially a LinkedList of FloatCartesian points, which are connected, in order
 * It is intended to be freely transformable, and drawable on a awt.Graphics2D object.
 * Notes:
 *  currently you can only translate and rotate the path.
 *  it is not garaunteed that
 * @see trig.utility.math.vector.FloatCartesian
 * Created by marcos on 17/07/2014.
 */
public class Path extends LinkedList<FloatCartesian> implements Renderable
{
    /*
        Renderable implementation
     */

    /**
     * Returns a Path2D.Float representation of this path, which can be more easily drawn using Graphics2D
     */
    @Override
    public Path2D.Float getPath2D(){
        GeneralPath pen = new GeneralPath();
        if(this.size() > 0)
        {
            FloatCartesian first = getFirst();
            pen.moveTo(first.x, first.y);
            for (int i=0; i < this.size(); i++)
            {
                FloatCartesian each = get(i);
                pen.lineTo(each.x, each.y);
            }
        }
        return pen;
    }

    /**
     * Gets a Rectangle representing a box that completely contains the shape rendered by the object.
     * Note: in this case, "contains" just means that no portion of the shape lies outside of the box, there may be portions on the exact edge,
     *  these portions may technically render beyond the container, depending on the style of stroke used to render them.
     * Note: this function guarantees that the returned Rectangles contains the shape, but not that it is the smallest possible containing box.
     * @return a Rectangle that completely contains the shape rendered by the object.
     */
    @Override
    public Rectangle getBounds()
    {

        float lowX, highX, lowY, highY, width, height;

        FloatCartesian each = get(0);
        lowX = each.x;
        highX = each.x;
        lowY = each.y;
        highY = each.y;

        for(int i = 1; i < size(); i++)
        {
            each = get(i);

            if(each.x < lowX)
            {
                lowX = each.x;
            }
            else if (each.x > highX)
            {
                highX = each.x;
            }

            if(each.y < lowY)
            {
                lowY = each.y;
            }
            else if (each.y > highY)
            {
                highY = each.y;
            }
        }

        width = highX - lowX;
        height = highY - lowY;

        return new Rectangle( (int) Math.floor(lowX), (int) Math.floor(lowY), (int) Math.ceil(width), (int) Math.ceil(height) );
    }


    /**
     * Draws the path, with the rendered path transformed using the specified AffineTransform
     * @param g the canvas to draw on
     * @param aT an affineTransform to apply to the rendered path
     * @see trig.utility.math.vector.FloatCartesian
     */
    @Override
    public void render(Graphics2D g, AffineTransform aT)
    {
        Path2D.Float pen = getPath2D();
        pen.transform(aT);
        g.draw(pen);
    }

     /**
     * draws the path using 0,0 as the origin.
     * @param g the canvas to draw on
     */
    @Override
    public void render(Graphics2D g)
    {
       g.draw(getPath2D());
    }

    @Override
    public void translate(float tX, float tY){
        for(FloatCartesian each : this)
        {
            each.translate(tX, tY);
        }
        return;
    }

    @Override
    public void translate(FloatCartesian tVector)
    {
        translate(tVector.x, tVector.y);
    }

    @Override
    public void rotate(float theta){
        for(FloatCartesian each : this)
        {
            each.rotate(theta);
        }
    }

    /**
     * Rotates the path about the specified center, instead of it's normal origin
     * @param theta the angle to rotate by, in radians.
     * @param cX the x-coordinate of the center to rotate about.
     * @param cY the y-coordinate of the center to rotate about.
     */
    @Override
    public void rotateAbout(float theta, float cX, float cY){
        for(FloatCartesian each : this)
        {
            each.translate(cX, cY);
            each.rotate(theta);
            each.translate(-cX, -cY);
        }
    }

    /**
     * Rotates the object about the specified center, instead of it's normal origin
     *
     * @param theta   the angle to rotate by, in radians.
     * @param tVector the x and y coordinates of the rotation-center
     */
    @Override
    public void rotateAbout(float theta, FloatCartesian tVector)
    {
        rotateAbout(theta, tVector.x, tVector.y);
    }

    /*
        misc
     */

    /**
     * Produces a completely new Path object with the same data
     * @return a deep clone of the object
     * @see trig.utility.math.vector.FloatCartesian
     */
    @Override
    public Path clone(){
        Path newPath = new Path();
        for(FloatCartesian each : this)
        {
            newPath.add(each.clone());
        }
        return newPath;
    }
}