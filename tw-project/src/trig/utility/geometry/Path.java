package trig.utility.geometry;

import trig.utility.math.vector.CartesianForm;
import trig.utility.math.vector.PolarForm;
import trig.utility.math.vector.Vector;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.util.ArrayList;

/**
 * Custom path class designed to use my Vector library.
 * It is essentially a list of vector points, a color, and a list of subpaths.
 * It is intended to be freely transformable, and drawable on a awt.Graphics2D object.
 * Note: currently you can only translate and rotate the path.
 * @see trig.utility.math.vector.Vector
 * Created by marcos on 17/07/2014.
 */
public class Path
{
    /*notes on custompath:
    needs a better class name
    have a color object!
    Support subpaths (for extensibilty?)
    use Vectors for nodes/points (makes transforms such as rotation and translation easy?)
    May not be enclosed:
        (this could however be achieved: by referencing the first Vector as the last Vector, for example)
        */
    Color color;
    ArrayList<Vector> points;
    ArrayList<Path> subPaths;

    public Color getColor()
    {
        return color;
    }

    public void setColor(Color color)
    {
        this.color = color;
    }

    public ArrayList<Vector> getPoints()
    {
        return points;
    }

    public void setPoints(ArrayList<Vector> points)
    {
        this.points = points;
    }

    public ArrayList<Path> getSubPaths()
    {
        return subPaths;
    }

    public void setSubPaths(ArrayList<Path> subPaths)
    {
        this.subPaths = subPaths;
    }

    public Path(Color color, ArrayList<Vector> points, ArrayList<Path> subPaths){
        this.color = color;
        this.points = points;
        this.subPaths = subPaths;
    }

    public Path(Color color, ArrayList<Vector> points){
        this(
                color,
                points,
                new ArrayList<Path>() //subPaths
        );
    }

    public Path(Color color){
        this(
                color,
                new ArrayList<Vector>() //points
        );
    }

    public Path(){
        this(Color.BLACK);
    }

    /**
     * Produces a deep clone of the object (no cross-mutation)
     * @return a completely new Path object with the same data, that can be edited independently.
     */
    public Path clone()
    {
        ArrayList<Path> clonedSubPaths = new ArrayList<Path>();
        for (Path each : subPaths)
        {
            clonedSubPaths.add(each.clone()); //we need each of these to return new versions of themselves!
        }


        return new Path(
                color, //Colors are immutable, no need to clone.
                new ArrayList<Vector>(points), //Vectors are immutable; also apparently casting points to collection is redundant, at least in jdk8
                clonedSubPaths
        );
    }

    /**
     * Returns a Path2D.Float representation of this path, which can be more easily drawn using Graphics2D
     */
    public Path2D.Float getPath2D(){
        GeneralPath pen = new GeneralPath();
        CartesianForm cartPoint = points.get(0).inCartesian();
        pen.moveTo(cartPoint.x, cartPoint.y);
        for(Vector each : points){
            cartPoint = each.inCartesian();
            pen.lineTo(cartPoint.x, cartPoint.y);
        }
        return pen;
    }

    /**
     * Draws the path at the specified origin, and transforms it using the specified AffineTransform
     * @param g the canvas to draw on
     * @param aT an affineTransform to apply to the rendered path
     * @see trig.utility.math.vector.Vector
     */
    public void render(Graphics2D g, AffineTransform aT)
    {
        Path2D.Float pen = getPath2D();
        pen.transform(aT);

        g.setColor(color);
        g.draw(pen);

        for(Path each : subPaths)
        {
            each.render(g, aT);
        }
    }

     /**
     * draws the path using 0,0 as the origin.
     * @param g the canvas to draw on
     */
    public void render(Graphics2D g)
    {
       g.setColor(color);
       g.draw(getPath2D());

        for(Path each : subPaths)
        {
            each.render(g);
        }
    }

    public void translate(float tX, float tY){
        CartesianForm cartEach;
        for(int i = 0; i < points.size(); i++){
            cartEach = points.get(i).inCartesian();
            points.set(i, new CartesianForm(cartEach.x + tX, cartEach.y + tY) );
        }
    }

    public void rotate(float theta){
        PolarForm polarEach;
        for(int i = 0; i < points.size(); i++){
            polarEach = points.get(i).inPolar();
            points.set(i, new PolarForm(polarEach.radius, polarEach.angle + theta) );
        }
    }
}
