package trig.utility.geometry;

import trig.utility.math.vector.Vector;

import java.awt.*;
import java.util.ArrayList;

/**
 * Custom path class designed to use my Vector library
 * They are slightly more versatile than awt structures in that they aren't locked to a position on a graphics object,
 * And can be translated and rotated on their own,
 * They do only have floating-point accuracy, though
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
    Color color = Color.BLACK;
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

    public ArrayList<Path> getSubPaths()
    {
        return subPaths;
    }

    public void addPoint(Vector point){
        points.add(point);
    }
}
