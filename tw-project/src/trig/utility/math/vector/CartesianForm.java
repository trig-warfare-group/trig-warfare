package trig.utility.math.vector;
/**
 * a vector in the form X, Y, explicitly starting at the origin
 * @see trig.utility.math.vector.Vector
 * Created by marcos on 11/07/2014.
 */
public class CartesianForm extends Vector
{
    //for now going to be a float for slightly better accuracy converting to and from PolarForm
    public final float x, y;


    //going to also store the int approximation of the x and y for now, the way it's accessed means I can always changed this decision
    protected final int intX, intY;

    /**
     * @return an integer approximation of x
     */
    public int getX()
    {
        return intX;
    }

    /**
     * @return an integer approximation of y
     */
    public int getY()
    {
        return intY;
    }

    //return self, for getting this form back from the vector interface!

    /**
     * A helper method used to allow casting to Vector
     * @return this object
     * @see trig.utility.math.vector.Vector
     */
    @Override
    public CartesianForm inCartesian()
    {
        return this;
    }

    /**
     * @param x a distance from origin along the x-axis
     * @param y a distance from origin along the y-axis
     */
    public CartesianForm(float x, float y)
    {
        this.x = x;
        this.y = y;

        this.intX = Math.round(x);
        this.intY = Math.round(y);
    }

    /**
     * Produces a PolarForm equivalent of the given data
     * @param x a distance from origin along the x-axis
     * @param y a distance from origin along the y-axis
     * @return a vector from given data, in polar form
     *      * @see trig.utility.math.vector.PolarForm
     */
    public static PolarForm toPolar(float x, float y)
    {
        return new PolarForm
                (
                        (float) Math.sqrt((x * x) + (y * y)), //radius
                        (float) Math.atan2(y, x) //theta
                );
    }

    /**
     * Produces a polar-form equivalent of the vector
     * @return this vector, represented in polar form
     * @see trig.utility.math.vector.PolarForm
     */
    @Override
    public PolarForm inPolar()
    {
        return toPolar(x, y);
    }

    @Override
    public CartesianForm translate(float tX, float tY)
    {
        return super.translate(tX, tY).inCartesian();
    }

    @Override
    public CartesianForm rotate(float theta)
    {
        return super.rotate(theta).inCartesian();
    }
}