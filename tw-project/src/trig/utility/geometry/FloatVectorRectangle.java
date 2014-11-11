package trig.utility.geometry;

import trig.utility.math.vector.FloatCartesian;

/**
 * bit of a hack really, needs improvement, helpful though.
 */
public class FloatVectorRectangle
{
    public FloatCartesian min, max;

    public FloatVectorRectangle(FloatCartesian min, FloatCartesian max)
    {
        this.min = min;
        this.max = max;
    }

    public FloatCartesian getMin()
    {
        return min;
    }

    public FloatCartesian getMax()
    {
        return max;
    }

    public FloatCartesian getCenter()
    {
        FloatCartesian result = FloatCartesian.sum(min, max);
        result.scale((float)0.5);
        return result;
    }
    public float getMinX()
    {
        return min.getX();
    }

    public float getMinY()
    {
        return min.getY();
    }

    public float getMaxX()
    {
        return max.getX();
    }

    public float getMaxY()
    {
        return max.getY();
    }

    public void translate(FloatCartesian shift)
    {
        min.add(shift);
        max.add(shift);
    }
    public void setLocation(FloatCartesian location)
    {
        translate(FloatCartesian.difference(location, min));
    }


    public float getHeight()
    {
        return getMaxY() - getMinY();
    }

    public float getWidth()
    {
        return getMaxX() - getMinX();
    }

    public void setHeight(float height)
    {
       max.setY(min.getY() + height);
    }

    public void setWidth(float width)
    {
        max.setX(min.getX()+width);
    }
}
