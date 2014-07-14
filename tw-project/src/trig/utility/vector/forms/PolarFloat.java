package trig.utility.vector.forms;

import trig.utility.Methods;

/**
* Created by marcos on 14/07/2014.
*/
public class PolarFloat extends PolarForm
{
    public final float radius;
    public final float angle;

    /**
     * @param radius the distance from origin
     * @param angle  expressed in radians, between [-PI, -PI]
     */
    public PolarFloat(float radius, float angle)
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
    public static CartesianFloat toCartesian(float radius, float angle)
    {
        //make sure to normalise
        angle = Methods.normalise(angle);
        return new CartesianFloat
                (
                        (float) (radius * Math.cos(angle)), //x
                        (float) (radius * Math.sin(angle)) //y
                );
    }

    public CartesianFloat toCartesian()
    {
        return toCartesian(radius, angle);
    }
}
