package trig.utility.math.vector;

/**
 * a vector, A vector, that is: a quantity containing both magnitude and direction.
 * Note: this is a floating-point precision version, for the integer precision version, see IntCartesian
 * @see IntCartesian
 * Created by marcos on 11/07/2014.
 */
public class FloatCartesian implements Vector2D
{
    public static FloatCartesian sum(FloatCartesian a, FloatCartesian b)
    {
        FloatCartesian result = a.clone();
        result.add(b);
        return result;
    }

    public static FloatCartesian difference(FloatCartesian a, FloatCartesian b)
    {
        FloatCartesian result = a.clone();
        result.subtract(b);
        return result;
    }

    public static float dotProduct(FloatCartesian a, FloatCartesian b)
    {
        return a.x*b.x+a.y*b.y;
    }

    public static float angleBetween(FloatCartesian a, FloatCartesian b)
    {
        return (float) Math.acos(dotProduct(a,b)/(a.magnitude()*b.magnitude()));
    }

    /**
     * returns a new copy mirrored on both axis.
     * @return
     */
    public static FloatCartesian mirror(FloatCartesian a) {
        return new FloatCartesian(-a.x, -a.y);
    }

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

    public void setX(float x)
    {
        this.x = x;
    }

    public void setY(float y)
    {
        this.y = y;
    }

    //constructs a new vector at the origin
    public FloatCartesian()
    {
        this(0, 0);
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
     * Translates the vector by the provided amounts
     */
    public void translate(float tX, float tY){
        this.x += tX;
        this.y += tY;
    };

    public void add(FloatCartesian b)
    {
       x += b.x;
       y += b.y;
    }

    public void subtract(FloatCartesian b)
    {
        x = x - b.x;
        y = y - b.y;
    }

    /**
     * Rotates the vector by the provided amount
     */
    public void rotate(float theta){
        float rx = (float) Math.cos(theta);
        float ry = (float) Math.sin(theta);
        float tempX = x * rx - y * ry;

        y = x * ry + y * rx;
        x = tempX;
    }


    public float magnitude()
    {
        return (float) Math.sqrt(x*x + y*y);
    }

    public float direction()
    {
        return (float)Math.atan2(y, x);
    }

    /**
     * Scalar multiplication
     * @param magnitude a scalar amount to multiply each component of the vector by.
     */
    public void scale(float magnitude){
        x = x*magnitude; y = y*magnitude;
    }

    public FloatCartesian clone(){
        return new FloatCartesian(this.x, this.y);
    }

}