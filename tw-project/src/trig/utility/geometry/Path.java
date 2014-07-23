package trig.utility.geometry;

import trig.utility.math.vector.Cartesian;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.util.LinkedList;

/**
 * Custom path class designed to use the Cartesian vector class from my vector library.
 * It is essentially a LinkedList of Cartesian points, which are connected, in order
 * It is intended to be freely transformable, and drawable on a awt.Graphics2D object.
 * Notes:
 *  currently you can only translate and rotate the path.
 *  it is not garaunteed that
 * @see trig.utility.math.vector.Cartesian
 * Created by marcos on 17/07/2014.
 */
public class Path extends LinkedList<Cartesian> implements Renderable
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
            Cartesian first = getFirst();
            pen.moveTo(first.x, first.y);
            for (int i=0; i < this.size(); i++)
            {
                Cartesian each = get(i);
                pen.lineTo(each.x, each.y);
            }
        }
        return pen;
    }



    /**
     * Draws the path, with the rendered path transformed using the specified AffineTransform
     * @param g the canvas to draw on
     * @param aT an affineTransform to apply to the rendered path
     * @see trig.utility.math.vector.Cartesian
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
            get(i).translate(tX, tY);
        }
        return;
    }

    @Override
    public void rotate(float theta){
        for (int i = 0; i < size(); i++)
        {
            get(i).rotate(theta);
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
        for (int i = 0; i < size(); i++)
        {
            Cartesian each = get(i);
            each.translate(cX, cY);
            each.rotate(theta);
            each.translate(-cX, -cY);
        }
    }

    /*
        misc
     */

    /**
     * Produces a completely new Path object with the same data
     * @return a deep clone of the object
     * @see trig.utility.math.vector.Cartesian
     */
    @Override
    public Path clone(){
        Path newPath = new Path();
        newPath.addAll(this);
       return newPath;
    }



}