package trig.utility.vector;
import trig.utility.vector.forms.cartesian.FloatCartesian;

/**
 * Dummy Class for holding X and Y coordinates for use with entities, maybe?
 * Intended to be used only for transporting data, such as a return of a function,
 * like Python tuples!, or Java Double(the class)
 * NOTE: MAY JUST REMOVE THIS ONE
 * Created by marcos on 11/07/2014.
 */
public class Location
{
    //these are final to avoid unexpected changes occurring when one instance is held in multiple places
    //intended to be used
    public final int x;
    public final int y;
    public Location(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    /**
     * Rounds the floats in the provided vector using Math.round(), does not truncate them.
     */
    public Location(float x, float y)
    {
        this.x = Math.round(x);
        this.y = Math.round(y);
    }
    public Location(FloatCartesian vector)
    {
        this(vector.x, vector.y);
    }

}

