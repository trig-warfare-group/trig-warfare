package trig.utility;

import java.awt.*;

/**
 * Created by brody on 20/07/14.
 */
public class SRectangle extends Rectangle
{

    public SRectangle(){super();}
    public SRectangle(Dimension dimension)
    {
        this.x = 0;
        this.y = 0;
        this.width = dimension.width;
        this.height = dimension.height;
    }
    public SRectangle(int x, int y, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Creates new Rectangle half the original size.
     * @return -- return a new Dimension, half of the original.
     */
    public SRectangle getHalvedDimension()
    {
        return new SRectangle(x, y, width/2, height/2);
    }

    @Override
    public String toString()
    {
        return "X:" + x + " \tY:" + y + " \tWidth:" + width + " \tHeight: " + height;
    }

}
