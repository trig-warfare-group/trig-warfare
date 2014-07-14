package trig.utility.vector.forms.polar;

import trig.utility.Methods;
import trig.utility.vector.forms.cartesian.CartesianInt;

/**
* Created by marcos on 14/07/2014.
*/
public class PolarInt extends PolarForm
{
    public final float radius;
    public final float angle;

    /**
     * @param radius the distance from origin
     * @param angle  expressed in radians, between [-PI, -PI]
     */
    public PolarInt(float radius, float angle)
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
    public static CartesianInt toCartesian(float radius, float angle)
    {
        //make sure to normalise
        angle = Methods.normalise(angle);
        return new CartesianInt
                (
                        (int) Math.round( radius * Math.cos(angle) ) , //x
                        (int) Math.round( radius * Math.sin(angle) )//y
                );
    }

    public CartesianInt toCartesian()
    {
        return toCartesian(radius, angle);
    }
}
