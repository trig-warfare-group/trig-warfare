package trig.utility.geometry;

import trig.utility.math.vector.FloatCartesian;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Created by marcos on 26/07/2014.
 */
public class ColoredPolygon extends Polygon implements Colored
{
    protected Color color;

    /*
        getters and setters
     */
    @Override
    public Color getColor()
    {
        return color;
    }

    @Override
    public void setColor(Color color)
    {
        this.color = color;
    }

    /*
        Constructors
     */
    public ColoredPolygon(Color color){
        this.color = color;
    }

    public ColoredPolygon()
    {
        this(Color.BLACK);
    }

    public ColoredPolygon(Color color, Polygon polygon)
    {
        this(color);
        addAll(polygon.clone());
    }

    /**
     * Creates a new ColoredPath with the same values as the provided path.
     * Note: the resulting object is a deep copy, and does not share points.
     * @param polygon a Polygon object to construct the ColoredPath from
     */
    public ColoredPolygon(Polygon polygon)
    {
        this();
        addAll(polygon.clone());
    }
    /*
        Renderable implementation
     */

    /**
     * Draws the path, with the rendered path transformed using the specified AffineTransform
     * @param g the canvas to draw on
     * @param aT an affineTransform to apply to the rendered path
     */
    @Override
    public void render(Graphics2D g, AffineTransform aT)
    {
        g.setColor(color);
        super.render(g, aT);
    }
    /**
     * draws the path using 0,0 as the origin.
     * @param g the canvas to draw on
     */
    @Override
    public void render(Graphics2D g)
    {
        g.setColor(color);
        super.render(g);
    }

    /*
        Colored implementation
     */

    /**
     * Produces a new, colourless version of the polygon
     * @return a deep copy of this object, without the colour
     */
    @Override
    public Path stripColor(){
        return super.clone();
    }

    /*
        misc
     */

    /**
     * Produces a completely new Path object with the same data
     * @return a deep clone of the object (no cross-mutation), that can be edited independently.
     * @see trig.utility.math.vector.FloatCartesian
     */
    @Override
    public ColoredPolygon clone(){
        ColoredPolygon newPolygon = new ColoredPolygon();
        for(FloatCartesian each : this)
        {
            newPolygon.add(each.clone());
        }
        return newPolygon;
    }
}
