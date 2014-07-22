package trig.utility.math.vector;


/**
 * A vector, that is: a quantity containing both magnitude and direction.
 */
public interface Vector2D
{

//    /**
//     * Produces a cartesian-form equivalent of the vector
//     * @return this vector, represented in cartesian form
//     * @see Cartesian
//     */
//    abstract public Cartesian inCartesian();
//
//    /**
//     * Produces a polar-form equivalent of the vector
//     * @return this vector, represented in polar form
//     * @see trig.utility.math.vector.Polar
//     */
//    abstract public Polar inPolar();

    abstract public Vector2D translate(float tX, float tY);


    abstract public Vector2D rotate(float theta);

    //todo: add some other math functions? (as they become used?) such as the dot and cross product
}