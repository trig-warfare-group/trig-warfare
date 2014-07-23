package trig.utility.geometry;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.util.ArrayList;

/**
 * base class for collections of Renderables
 * Created by marcos on 18/07/2014.
 */
public class RenderableList<T extends Renderable> extends ArrayList<T> implements Renderable
{
    /*
        Renderable implementation
        Note: these should be fine to leave, as the internal paths should draw themselves in colour if they have one anyway?
     */

    /**
     * Produces a Path2D from paths
     * @return A Path2D object which contains the PathIterators of all paths
     */
    @Override
    public Path2D getPath2D()
    {
        GeneralPath pen = new GeneralPath();
        for(T each : this)
        {
            pen.append(each.getPath2D().getPathIterator(new AffineTransform()), false);
        }
        return pen;
    }

    /**
     * Draws the paths at the specified origin, and transforms it using the specified AffineTransform
     * @param g the canvas to draw on
     * @param aT an affineTransform to apply to the rendered path
     * @see trig.utility.math.vector.Cartesian
     */
    @Override
    public void render(Graphics2D g, AffineTransform aT)
    {
        for(T each : this)
        {
            each.render(g, aT);
        }
    }

    /**
     * draws the paths using 0,0 as the origin.
     * @param g the canvas to draw on
     */
    @Override
    public void render(Graphics2D g)
    {
        for(T each : this)
        {
            each.render(g);
        }
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

    public void rotateAbout(float theta, float cX, float cY){
        for(T each: this){
            each.rotateAbout(theta, cX, cY);
        }
    }
}
