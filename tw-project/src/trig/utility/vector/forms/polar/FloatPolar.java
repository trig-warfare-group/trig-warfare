package trig.utility.vector.forms.polar;

import trig.utility.Methods;
import trig.utility.vector.forms.PolarForm;
import trig.utility.vector.forms.cartesian.FloatCartesian;

/**
* Created by marcos on 14/07/2014.
*/
public class FloatPolar extends PolarForm
{
    public final float radius;
    public final float angle;

    /**
     * @param radius the distance from origin
     * @param angle  expressed in radians, between [-PI, -PI]
     */
    public FloatPolar(float radius, float angle)
    {
        this.radius = radius;
        this.angle = Methods.normalise(angle);
    }

    /**
     * Produces a CartesianForm equivalent of the given data
     * @param radius the distance from origin
     * @param angle  expressed in radians, between [-PI, -PI]
     * @return a vector from given data, in cartesian form
     */
    public static FloatCartesian toCartesian(float radius, float angle)
    {
        //make sure to normalise
        angle = Methods.normalise(angle);
        return new FloatCartesian
                (
                        (float) (radius * Math.cos(angle)), //x
                        (float) (radius * Math.sin(angle)) //y
                );
    }

    public FloatCartesian toCartesian()
    {
        return toCartesian(radius, angle);
    }
}
