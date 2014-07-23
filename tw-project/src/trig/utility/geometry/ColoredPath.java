package trig.utility.geometry;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Like a normal Path, but with a colour!
 * sorta a test run, we'll probably need to paste this code into a lot of classes?
 * @see trig.utility.math.vector.Cartesian
 * Created by marcos on 18/07/2014.
 */
public class ColoredPath extends Path implements Colored
{
    protected Color color;

    /*
        getters and setters
     */
    public Color getColor()
    {
        return color;
    }
    public void setColor(Color color)
    {
        this.color = color;
    }

    /*
        Constructors
     */
    public ColoredPath(Color color){
        this.color = color;
    }

    public ColoredPath()
    {
        this(Color.BLACK);
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
     * Produces a new, colourless version of the path
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
     * @see trig.utility.math.vector.Cartesian
     */
    @Override
    public ColoredPath clone(){
        ColoredPath newPath = new ColoredPath();
        newPath.addAll(this);
        return newPath;
    }
}
