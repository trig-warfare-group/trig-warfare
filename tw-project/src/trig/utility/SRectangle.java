package trig.utility;

import java.awt.*;

/**
 * Created by brody on 20/07/14.
 */
public class SRectangle extends Rectangle
{

    public SRectangle(Dimension dimension)
    {
        this.x = 0;
        this.y = 0;
        this.width = dimension.width;
        this.height = dimension.height;
    }
    public SRectangle(int x, int y, int width, int height)
    {
        super(x, y, width, height);
    }

    /**
     * Creates new Rectangle half the original size.
     * @return -- return a new Dimension, half of the original.
     */
    public SRectangle getHalvedDimension()
    {
        return new SRectangle(x, y, width/2, height/2);
    }
}
