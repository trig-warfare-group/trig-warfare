package trig.utility.geometry;

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
     * draws the object using 0,0 as the origin.
     * @param g the canvas to draw on
     */
    abstract public void render(Graphics2D g);
    /**
     * Draws the object, with the rendered object transformed using the specified AffineTransform
     * @param g the canvas to draw on
     * @param aT an affineTransform to apply to the rendered objected
     * @see trig.utility.math.vector.Vector
     */
    abstract public void render(Graphics2D g, AffineTransform aT);

    abstract public void translate(float tX, float tY);

    public void rotate(float theta);
}
