package trig.utility;

import java.awt.*;

/**
 * Created by brody on 20/07/14.
 */
public class SRectangle implements Cloneable
{
    Dimension dimension;
    Point point;

    public SRectangle(Dimension dimension)
    {
        this.point = new Point(0, 0);
        this.dimension = dimension;
    }

    public SRectangle(Point point, Dimension dimension)
    {
        this.dimension = dimension;
        this.point = point;
    }

    public SRectangle(int width, int height)
    {
        point = new Point(0, 0);
        dimension = new Dimension(width, height);
    }


    /**
     * Creates a new SDimension, based on the current dimensions, dimension (width-height).
     * and scales the new Dimension, and returns it.
     * @param widthScale -- to scale the width by.
     * @param heightScale -- to scale the width by.
     * @return -- returns the new Dimension.
     */
    public SRectangle getScaledDimension(double widthScale, double heightScale)
    {
        return new SRectangle((int) (dimension.width * widthScale), (int) (dimension.height * heightScale));
    }

    /**
     * Creates new SDimension half the original size.
     * @return -- return a new Dimension, half of the original.
     */
    public SRectangle getHalvedDimension()
    {
        Point p = this.point;
        Dimension d = new Dimension(this.dimension.width/2, this.dimension.height/2);
        return new SRectangle(p, d);
    }

    /**
     * Checks whether the dimensions intersects the other.
     * @note -- its data does not permit angled-comparisons.
     * @param checkDimension - the dimension to check against.
     * @return - returns whether intersected (true or false).
     */
    public boolean intersects(SRectangle checkDimension)
    {
        Point checkPoint = new Point(checkDimension.getPoint().x, checkDimension.getPoint().y);
        if(this.pointOutside(checkPoint))
            return false;

        checkPoint.x = checkPoint.x + checkDimension.getDimension().width;
        if(this.pointOutside(checkPoint))
            return false;

        checkPoint.y = checkPoint.y + checkDimension.getDimension().height;
        if(this.pointOutside(checkPoint))
            return false;

        checkPoint.x = checkDimension.getPoint().x;
        if(this.pointOutside(checkPoint))
            return false;

        return true;
    }

    /**
     * returns true, if the given parameter it outside this SDimension.
     * @param pointCheck -- point to checkOutside
     */
    private boolean pointOutside(Point pointCheck)
    {
        if(pointCheck.x < point.x || pointCheck.x > (point.x + dimension.width)
                || pointCheck.y < point.y || pointCheck.y > (point.y + dimension.height))
            return true;
        else
            return false;
    }

    public Dimension getDimension()
    {
        return dimension;
    }

    public void setDimension(Dimension dimension)
    {
        this.dimension = dimension;
    }

    public Point getPoint()
    {
        return point;
    }

    public void setPoint(Point point)
    {
        this.point = point;
    }

    @Override
    public Object clone()
    {
        return new SRectangle(point, dimension);
    }

}
