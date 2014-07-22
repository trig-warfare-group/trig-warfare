package trig.utility.math.vector;
/**
 * a vector, A vector, that is: a quantity containing both magnitude and direction.
 * @see Cartesian
 * Created by marcos on 11/07/2014.
 */
public class Cartesian implements Vector2D
{
    //for now going to be a float for slightly better accuracy converting to and from Polar
    protected float x, y;

    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }

    /**
     * @param x a distance from origin along the x-axis
     * @param y a distance from origin along the y-axis
     */
    public Cartesian(float x, float y)
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
     * Produces a new Cartesian of the same type, translated by the provided amounts
     * @return the Cartesian resulting from applying the rotation to this vector
     */
    public void translate(float tX, float tY){
        this.x += tX;
        this.y += tY;
    };

    /**
     * Produces a new Cartesian object, rotated by the provided amount
     * @return the Cartesian resulting from applying the rotation to this vector
     */
    public void rotate(float theta){

        float rx = (float) Math.cos(theta);
        float ry = (float) Math.sin(theta);

        x = x * rx - y * ry;
        y = x * ry + y * rx;
    }
}