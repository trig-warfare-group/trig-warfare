package trig.utility.math.vectorold.vector;


/**
 * A vector, that is: a quantity containing both magnitude and direction.
 */
public abstract class Vector
{
    /**
     * Produces a cartesian-form equivalent of the vector
     * @return this vector, represented in cartesian form
     * @see CartesianForm
     */
    abstract public CartesianForm inCartesian();

    /**
     * Produces a polar-form equivalent of the vector
     * @return this vector, represented in polar form
     * @see PolarForm
     */
    abstract public PolarForm inPolar();

    /**
     * Produces a new Cartesian of the same type, translated by the provided amounts
     * @return the Cartesian resulting from applying the rotation to this vector
     */
    public Vector translate(float tX, float tY){
        CartesianForm cartForm = this.inCartesian();
        return new CartesianForm(cartForm.x+tX, cartForm.y+tY);
    };

    /**
     * Produces a new Cartesian object, rotated by the provided amount
     * @return the Cartesian resulting from applying the rotation to this vector
     */
    public Vector rotate(float theta){
        PolarForm polarForm = this.inPolar();
        return new PolarForm(polarForm.radius, polarForm.radius+theta);
    }

    //todo: add some other math functions? (as they become used?) such as the dot and cross product
}