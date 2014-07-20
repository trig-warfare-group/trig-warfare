package trig.utility;

import java.awt.*;

/**
 * Created by brody on 20/07/14.
 */
public class SDimension extends Dimension
{
    public int x;
    public int y;

    public SDimension()
    {
        x = 0;
        y = 0;
    }

    /**
     * Creates a new Dimension, based on the current dimensions, dimension (width-height).
     * and scales the new Dimension, and returns it.
     * @param widthScale -- to scale the width by.
     * @param heightScale -- to scale the width by.
     * @return -- returns the new Dimension.
     */
    public Dimension getScaledDimension(double widthScale, double heightScale)
    {
        return new Dimension((int) (this.width * widthScale), (int) (this.height * heightScale));
    }

    /**
     * Creates new Dimension half the original size.
     * @return -- return a new Dimension, half of the original.
     */
    public Dimension getHalvedDimension()
    {
        return new Dimension(this.width / 2, this.width / 2);
    }

    /**
     * Checks whether the dimensions intersects the other.
     * @note -- its data does not permit angled-comparisons.
     * @param checkDimension - the dimension to check against.
     * @return - returns whether intersected (true or false).
     */
    public boolean intersects(SDimension checkDimension)
    {
        Point p = new Point(checkDimension.x, checkDimension.y);
        if(this.pointWithin(p))
            return true;

        p.x = checkDimension.width;
        if(this.pointWithin(p))
            return true;

        p.y = checkDimension.height;
        if(this.pointWithin(p))
            return true;

        p.x = checkDimension.x;
        if(this.pointWithin(p))
            return true;

        return false;
    }

    private boolean pointWithin(Point p)
    {
        if(p.x > this.x && p.x < this.width
                && p.y > this.y && p.y < this.height)
            return true;
        else
            return false;
    }


}
