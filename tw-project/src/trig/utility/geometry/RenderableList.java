package trig.utility.geometry;

import org.w3c.dom.css.Rect;

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
     * Gets a Rectangle representing a box that completely contains the shape rendered by the object.
     * Note: in this case, "contains" just means that no portion of the shape lies outside of the box, there may be portions on the exact edge,
     *  these portions may technically render beyond the container, depending on the style of stroke used to render them.
     * @return a Rectangle that completely
     */
    @Override
    public Rectangle getBounds(){
        Rectangle[] boundaries = new Rectangle[size()];

        double lowX, highX, lowY, highY, width, height, eachLowX, eachHighX, eachLowY, eachHighY;
        Rectangle bound = get(0).getBounds();
        lowX = bound.getMinX();
        highX = bound.getMaxX();
        lowY = bound.getMinY();
        highY = bound.getMaxY();

        for(int i = 1; i < size(); i++){
            bound = get(i).getBounds();
            eachLowX = bound.getMinX();
            eachHighX = bound.getMaxX();
            eachLowY = bound.getMinY();
            eachHighY = bound.getMaxY();
            if(eachLowX < lowX)
            {
                lowX = eachLowX;
            }
            else if(eachHighX > highX)
            {
                highX = eachHighX;
            }

            if(eachLowY < lowY)
            {
                lowX = eachLowY;
            }
            else if(eachHighY > highX)
            {
                highX = eachHighY;
            }
        }

        width = highX - lowX;
        height = highY - lowY;

        return new Rectangle( (int) Math.floor(lowX), (int) Math.floor(lowY), (int) Math.ceil(width), (int) Math.ceil(height) );
    }

    /**
     * Draws the paths at the specified origin, and transforms it using the specified AffineTransform
     * @param g the canvas to draw on
     * @param aT an affineTransform to apply to the rendered path
     * @see trig.utility.math.vector.FloatCartesian
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
