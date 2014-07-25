package trig.utility.geometry;

import trig.utility.math.vector.FloatCartesian;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

/**
 * Similar to a path, but that the last point implicitly connects to the first, and when rendered.
 * Note: I have NOT coded to actually make the next/prev nodes link up properly with the first and last nodes.
 * Created by marcos on 24/07/2014.
 */
public class Polygon extends Path
{
    @Override
    public Path2D.Float getPath2D()
    {
        Path2D.Float pen = super.getPath2D();

        FloatCartesian first = getFirst();

        pen.lineTo(first.x, first.y);
        return pen;
    }

    @Override
    public Polygon clone(){
        Polygon newPolygon = new Polygon();
        for(FloatCartesian each : this)
        {
            newPolygon.add(each.clone());
        }
        return newPolygon;
    }
}
