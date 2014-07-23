package trig.utility.math.vector;

/**
 * a vector, A vector, that is: a quantity containing both magnitude and direction.
 * Note: this is a floating-point precision version, for the integer precision version, see IntCartesian
 * @see IntCartesian
 * Created by marcos on 11/07/2014.
 */
public class FloatCartesian extends IntCartesian
{
    //for now going to be a float for slightly better accuracy converting to and from Polar
    public float x, y;

    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }

    public FloatCartesian clone(){
        return new FloatCartesian(this.x, this.y);
    }
     /**
     * @param x a distance from origin along the x-axis
     * @param y a distance from origin along the y-axis
     */
    public FloatCartesian(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Produces a polar-form equivalent of the vector
     * @return this vector, represented in polar form
     * @see Polar
     */
    public Polar getPolarForm()
    {
        return new Polar
        (
                (float) Math.sqrt((x * x) + (y * y)), //radius
                (float) Math.atan2(y, x) //theta
        );
    }



    /**
     * Produces a new FloatCartesian of the same type, translated by the provided amounts
     * @return the FloatCartesian resulting from applying the rotation to this vector
     */
    public void translate(float tX, float tY){
        this.x += tX;
        this.y += tY;
    };

    /**
     * Produces a new FloatCartesian object, rotated by the provided amount
     * @return the FloatCartesian resulting from applying the rotation to this vector
     */
    public void rotate(float theta){

        float rx = (float) Math.cos(theta);
        float ry = (float) Math.sin(theta);

        x = x * rx - y * ry;
        y = x * ry + y * rx;
    }
}