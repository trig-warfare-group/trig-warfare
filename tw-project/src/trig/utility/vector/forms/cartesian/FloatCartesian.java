package trig.utility.vector.forms.cartesian;

import trig.utility.vector.forms.CartesianForm;
import trig.utility.vector.forms.polar.FloatPolar;

/**
 * Floating-point accuracy VectorForm.PolarForm
 * @see trig.utility.vector.forms.CartesianForm
 */
public class FloatCartesian extends CartesianForm
{
    //for now going to be a float for slightly better accuracy converting to and from PolarForm
    public final float x;
    public final float y;

    /**
     * @param x a distance from origin along the x-axis
     * @param y a distance from origin along the y-axis
     */
    public FloatCartesian(float x, float y)
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
    public static FloatPolar toPolar(float x, float y)
    {
        return new FloatPolar
                (
                        (float) Math.sqrt((x * x) + (y * y)), //radius
                        (float) Math.atan2(y, x) //angle
                );
    }

    @Override
    public FloatPolar toPolar()
    {
        return toPolar(x, y);
    }
}
