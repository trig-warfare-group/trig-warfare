package trig.utility.geometry;

import trig.utility.math.vector.FloatCartesian;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
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
        GeneralPath pen = new GeneralPath();
        if(this.size() > 0)
        {
            FloatCartesian first = getFirst();
            pen.moveTo(first.x, first.y);
            for (int i=0; i < this.size(); i++)
            {
                FloatCartesian each = get(i);
                pen.lineTo(each.x, each.y);
            }
            pen.lineTo(first.x, first.y);
        }
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
    public boolean intersects(Rectangle target)
    {
        return getBounds().intersects(target.getBounds());
    }

    public boolean intersects(Polygon target)
    {
        return intersects(target.getBounds());
    }

    /**
     * Determines how far out the furthest point outside of the provided box is
     * @return a FloatCartesian vector representing how far this polygon protudes from the target, and in which direction (- if left or above, etc)
     */
    public FloatCartesian getOverflow(Rectangle target){
        float furthestX, furthestY, furthestDistance, eachDistance;
        furthestX = 0;
        furthestY = 0;
        furthestDistance = 0;

        double distX, distY, overX, underX, overY, underY;

        for (FloatCartesian each : this)
        {
            overX = each.x - target.getMaxX();
            if(overX < 0)
            {
                overX = 0;
            }

            underX = each.x - target.getMinX();
            if(underX > 0)
            {
                underX = 0;
            }

            overY = each.y - target.getMaxY();
            if(overY < 0)
            {
                overY = 0;
            }

            underY = each.y - target.getMinY();
            if(underY > 0)
            {
                underY = 0;
            }

            distX = Math.abs(underX) > overX ? underX : overX;

            distY = Math.abs(underY) > overY ? underY : overY;



            eachDistance = (float) Math.sqrt( Math.abs(distX*distX) + Math.abs(distY*distY) );

            if(eachDistance > furthestDistance)
            {
                furthestDistance = eachDistance;
                furthestX = (float) distX;
                furthestY = (float) distY;
            }
        }

        return new FloatCartesian(furthestX, furthestY);
    }
}
