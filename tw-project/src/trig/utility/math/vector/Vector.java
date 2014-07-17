package trig.utility.math.vector;


/**
 * A vector, that is: a quantity containing both magnitude and direction.
 */
public interface Vector
{
    /**
     * Produces a cartesian-form equivalent of the vector
     * @return this vector, represented in cartesian form
     * @see trig.utility.math.vector.CartesianForm
     */
    public CartesianForm inCartesian();

    /**
     * Produces a polar-form equivalent of the vector
     * @return this vector, represented in polar form
     * @see trig.utility.math.vector.PolarForm
     */
    public PolarForm inPolar();


    //todo: add some other math functions? (as they become used?) such as the dot and cross product
}