package trig.utility.vector;

import trig.utility.Methods;

/**
 * Vector in the form of Radius, Angle, explicitly starting at the origin
 * Intended to be used only for transporting data, such as a return of a function,
 * like Python tuples!, or Java Double(the class)
 * Created by marcos on 11/07/2014.
 */
public class PolarVector implements Vector
{
    public final float radius;
    public final float angle;

    public PolarVector(float radius, float angle)
    {
        this.radius = radius;
        this.angle = Methods.normalise(angle);
    }

    /**
     * Produces an XYVector conversion
     * @param radius the distance from origin
     * @param angle expressed in radians, between [-PI, -PI]
     */
    public static CartesianVector toCartesian(float radius, float angle)
    {
        //make sure to normalise
        angle = Methods.normalise(angle);
        return new CartesianVector
        (
                (float) (radius*Math.cos(angle)), //x
                (float) (radius*Math.sin(angle)) //y
        );
    }
    /**
     * Produces an XYVector conversion
     * QUESTION: IS IT MORE EFFICIENT OVERALL TO DUPLICATE THE CODE INSTEAD OF USING THE STATIC ONE?
     */
    public CartesianVector toCartesian()
    {
        return toCartesian(radius, angle);
    }
}
