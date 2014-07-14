package trig.utility.vector.forms.cartesian;

import trig.utility.vector.forms.polar.PolarFloat;

/**
 * Floating-point accuracy VectorForm.PolarForm
 * @see CartesianForm
 */
public class CartesianFloat extends CartesianForm
{
    //for now going to be a float for slightly better accuracy converting to and from PolarForm
    public final float x;
    public final float y;

    /**
     * @param x a distance from origin along the x-axis
     * @param y a distance from origin along the y-axis
     */
    public CartesianFloat(float x, float y)
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
    public static PolarFloat toPolar(float x, float y)
    {
        return new PolarFloat
                (
                        (float) Math.sqrt((x * x) + (y * y)), //radius
                        (float) Math.atan2(y, x) //angle
                );
    }

    @Override
    public PolarFloat toPolar()
    {
        return toPolar(x, y);
    }
}
