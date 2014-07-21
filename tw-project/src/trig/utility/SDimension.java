package trig.utility;

import java.awt.*;

/**
 * Created by brody on 20/07/14.
 */
public class SDimension implements Cloneable
{


    Dimension d;
    Point p;

    public SDimension(Dimension dimension)
    {
        this.p = new Point(0, 0);
        this.d = dimension;
    }

    public SDimension(Point point, Dimension dimension)
    {
        this.d = dimension;
        this.p = point;
    }

    public SDimension(int width, int height)
    {
        p = new Point(0, 0);
        d = new Dimension(width, height);
    }


    /**
     * Creates a new SDimension, based on the current dimensions, dimension (width-height).
     * and scales the new Dimension, and returns it.
     * @param widthScale -- to scale the width by.
     * @param heightScale -- to scale the width by.
     * @return -- returns the new Dimension.
     */
    public SDimension getScaledDimension(double widthScale, double heightScale)
    {
        return new SDimension((int) (d.width * widthScale), (int) (d.height * heightScale));
    }

    /**
     * Creates new SDimension half the original size.
     * @return -- return a new Dimension, half of the original.
     */
    public SDimension getHalvedDimension()
    {
        Point p = this.p;
        Dimension d = new Dimension(this.d.width/2, this.d.height/2);
        return new SDimension(p, d);
    }

    /**
     * Checks whether the dimensions intersects the other.
     * @note -- its data does not permit angled-comparisons.
     * @param checkDimension - the dimension to check against.
     * @return - returns whether intersected (true or false).
     */
    public boolean intersects(SDimension checkDimension)
    {
        Point p = new Point(checkDimension.getPoint().x, checkDimension.getPoint().y);
        if(this.pointWithin(p))
            return true;

        p.x = checkDimension.getDimension().width;
        if(this.pointWithin(p))
            return true;

        p.y = checkDimension.getDimension().height;
        if(this.pointWithin(p))
            return true;

        p.x = checkDimension.getPoint().x;
        if(this.pointWithin(p))
            return true;

        return false;
    }

    private boolean pointWithin(Point pc)
    {
        if(pc.x > p.x && pc.x < d.width
                && pc.y > p.y && pc.y < d.height)
            return true;
        else
            return false;
    }

    public Dimension getDimension()
    {
        return d;
    }

    public void setDimension(Dimension dimension)
    {
        this.d = dimension;
    }

    public Point getPoint()
    {
        return p;
    }

    public void setPoint(Point point)
    {
        this.p = point;
    }

    @Override
    public Object clone()
    {
        return new SDimension(p, d);
    }

}
