package trig.utility.geometry;

import trig.utility.math.vector.Cartesian;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.util.LinkedList;

/**
 * Custom path class designed to use my Cartesian library.
 * It is essentially a LinkedList of vector points, which are connected, in order
 * It is intended to be freely transformable, and drawable on a awt.Graphics2D object.
 * Notes:
 *  currently you can only translate and rotate the path.
 *  it is not garaunteed that
 * @see trig.utility.math.vector.Cartesian
 * @see T
 * Created by marcos on 17/07/2014.
 */
public class Path<T extends Cartesian> extends LinkedList<T> implements Renderable
{
    private float height;
    private float width;

    /**
     * A vector used for correcting the rotation axis, assumed to be 0 if not specified
     **/
    private Cartesian center = new Cartesian(0,0);

    /**
     * Calculates the region of the actual
     */
    public void autoCrop(){

        //note that this works similarly to what get bounds would, and should perhaps be extracted etc if we end up having a getbounds.
        float lowX, highX, lowY, highY, width, height;

        /*
            For reference with getbounds:
            newX = lowX;
            newY = lowY;
            width = highX-lowX;
            height = highY-lowY;
         */

        Cartesian each = get(0).inCartesian();
        lowX = each.x;
        highX = each.x;
        lowY = each.y;
        highY = each.y;

        for(int i = 1; i < size(); i++)
        {
            each = get(i).inCartesian();

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
                lowX = each.x;
            }
            else if (each.x > highX)
            {
                highX = each.x;
            }
        }

        width = highX - lowX;
        height = highY - lowY;

    }

    /**
     * Rotates the path about the specified center, instead of the origin
     * @param theta the angle to rotate by, in radians.
     * @param rotationCenter the center to rotate about.
     */
    public void rotateAbout(float theta, Cartesian rotationCenter){
        for (int i = 0; i < size(); i++)
        {
            set(i, (T) get(i).translate(rotationCenter.x, rotationCenter.y).rotate(theta).translate(-rotationCenter.x, -rotationCenter.y));
        }
    }

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
            Cartesian cartPoint = getFirst().inCartesian();
            pen.moveTo(cartPoint.x, cartPoint.y);
            for (int i=0; i < this.size(); i++)
            {
                cartPoint = get(i).inCartesian();
                pen.lineTo(cartPoint.x, cartPoint.y);
            }
        }
        return pen;
    }

    /**
     * Draws the path, with the rendered path transformed using the specified AffineTransform
     * @param g the canvas to draw on
     * @param aT an affineTransform to apply to the rendered path
     * @see trig.utility.math.vector.Cartesian
     * @see T
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
        for(int i = 0; i < size(); i++)
        {
            //this should be guaranteed by the vector interface, btw, maybe I should figure out a way to use generics for vector? making cartesian and polar forms extend vectorform and making it private might work.
            set(i, (T) get(i).translate(tX, tY));
        }
        return;
    }

    @Override
    public void rotate(float theta){
        for (int i = 0; i < size(); i++)
        {
            set(i, (T) get(i).rotate(theta) );
        }
    }

    /*
        misc
     */

    /**
     * Produces a completely new Path object with the same data
     * @return a deep clone of the object
     * @see trig.utility.math.vector.Cartesian
     * @see T
     */
    @Override
    public Path<T> clone(){
        Path<T> newPath = new Path<T>();
        newPath.addAll(this);
       return newPath;
    }



}