package trig.utility.vector.forms.cartesian;

import trig.utility.vector.forms.polar.PolarInt;

/**
* Created by marcos on 14/07/2014.
*/
public class CartesianInt extends CartesianForm
{
    //for now going to be a float for slightly better accuracy converting to and from PolarForm
    public final int x;
    public final int y;

    /**
     * @param x a distance from origin along the x-axis
     * @param y a distance from origin along the y-axis
     */
    public CartesianInt(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Produces a PolarForm equivalent of the given data
     * @param x a distance from origin along the x-axis
     * @param y a distance from origin along the y-axis
     * @return a vector from given data, in polar form
     */
    public static PolarInt toPolar(int x, int y)
    {
        return new PolarInt
                (
                        (float) Math.sqrt((x * x) + (y * y)), //radius
                        (float) Math.atan2(y, x)  //angle
                );
    }

    @Override
    public PolarInt toPolar()
    {
        return toPolar(x, y);
    }
}
