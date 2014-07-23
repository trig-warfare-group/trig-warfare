package trig.utility.math.vector;

/**
 * A vector in the form of Radius, Angle, explicitly starting at the origin
 * Created by marcos on 11/07/2014.
 */
public class Polar implements Vector2D
{
    public float radius, angle;

    /**
     * @param radius the distance from origin
     * @param theta an angle expressed in radians, between [-PI, -PI]
     */
    public Polar(float radius, float theta)
    {
        this.radius = radius;
        this.angle = trig.utility.math.Methods.normalise(theta);
    }

    /**
     * Produces a FloatCartesian equivalent of the given data
     * @param radius the distance from origin
     * @param theta an angle expressed in radians, between [-PI, -PI]
     * @return a vector from given data, in cartesian form
     */
    public static FloatCartesian getCartesianForm(float radius, float theta)
    {
        //make sure to normalise
        theta = trig.utility.math.Methods.normalise(theta);
        return new FloatCartesian
                (
                        (float) (radius * Math.cos(theta)), //x
                        (float) (radius * Math.sin(theta)) //y
                );
    }

    /**
     * Produces a cartesian-form equivalent of the vector
     * @return this vector, represented in cartesian form
     * @see FloatCartesian
     */
    public FloatCartesian inCartesian()
    {
        return toCartesian(radius, angle);
    }

    @Override
    public Polar translate(float tX, float tY)
    {
        return super.translate(tX, tY).inPolar();
    }

    @Override
    public Polar rotate(float theta)
    {
        return super.rotate(theta).inPolar();
    }
}

