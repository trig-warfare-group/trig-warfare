package trig.utility;

import java.awt.*;

/**
 * Created by brody on 20/07/14.
 */
public class SDimension extends Dimension
{
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

}
