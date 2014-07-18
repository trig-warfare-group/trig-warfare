package trig.utility.geometry;

import trig.utility.math.vector.CartesianForm;
import trig.utility.math.vector.PolarForm;
import trig.utility.math.vector.Vector;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Custom path class designed to use my Vector library.
 * It is essentially a LinkedList of vector points, which are connected, in order
 * It is intended to be freely transformable, and drawable on a awt.Graphics2D object.
 * Notes:
 *  currently you can only translate and rotate the path.
 *  it is not garaunteed that
 * @see trig.utility.math.vector.Vector
 * @see T
 * Created by marcos on 17/07/2014.
 */
public class Path<T extends Vector> extends LinkedList<T> implements Renderable
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
            CartesianForm cartPoint = getFirst().inCartesian();
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
     * @see trig.utility.math.vector.Vector
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
        for(T each: this){
            each.translate(tX, tY);
        }
    }

    @Override
    public void rotate(float theta){
        for(T each: this){
            each.rotate(theta);
        }
    }

    /*
        misc
     */

    /**
     * Produces a completely new Path object with the same data
     * @return a deep clone of the object
     * @see trig.utility.math.vector.Vector
     * @see T
     */
    @Override
    public Path<T> clone(){
        Path<T> newPath = new Path<T>();
        newPath.addAll(this);
       return newPath;
    }
}
