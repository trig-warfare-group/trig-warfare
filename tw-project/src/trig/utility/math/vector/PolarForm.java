package trig.utility.math.vector;

/**
 * A vector in the form of Radius, Angle, explicitly starting at the origin
 * Created by marcos on 11/07/2014.
 */
public class PolarForm implements Vector
{
    public final float radius;
    public final float angle;

    //return self, for getting this form back from the vector interface!
    /**
     * A helper method used to allow casting to Vector
     * @return this object
     * @see trig.utility.math.vector.Vector
     */
    @Override
    public PolarForm inPolar()
    {
        return this;
    }

    /**
     * @param radius the distance from origin
     * @param angle  expressed in radians, between [-PI, -PI]
     */
    public PolarForm(float radius, float angle)
    {
        this.radius = radius;
        this.angle = trig.utility.math.Methods.normalise(angle);
    }

    /**
     * Produces a CartesianForm equivalent of the given data
     * @param radius the distance from origin
     * @param angle  expressed in radians, between [-PI, -PI]
     * @return a vector from given data, in cartesian form
     */
    public static CartesianForm toCartesian(float radius, float angle)
    {
        //make sure to normalise
        angle = trig.utility.math.Methods.normalise(angle);
        return new CartesianForm
                (
                        (float) (radius * Math.cos(angle)), //x
                        (float) (radius * Math.sin(angle)) //y
                );
    }

    /**
     * Produces a cartesian-form equivalent of the vector
     * @return this vector, represented in cartesian form
     * @see trig.utility.math.vector.CartesianForm
     */
    public CartesianForm inCartesian()
    {
        return toCartesian(radius, angle);
    }
}

