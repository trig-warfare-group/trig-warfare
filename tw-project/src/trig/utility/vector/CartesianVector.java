package trig.utility.vector;

/**
 * Vector in the form X, Y, explicitly starting at the origin
 * Intended to be used only for transporting data, such as a return of a function,
 * like Python tuples!, or Java Double(the class)
 * Created by marcos on 11/07/2014.
 */
public class CartesianVector
{
    /*
        e.g. an XYVector like (0,1) is a line from (0,0) to (0,1)

        Some reading on this math:
        http://books.google.com.au/books?id=n6heB7tU6SwC&pg=PA271&lpg=PA271&dq=general+formula+for+polar+vector+from+cartesian+java&source=bl&ots=xElApUfYfB&sig=YA-Z7JmYPKBY8f088kGLpBu3KiQ&hl=en&sa=X&ei=voK_U7jKKoHfkAWdmIC4Cw&ved=0CCwQ6AEwAg#v=onepage&q=general%20formula%20for%20polar%20vector%20from%20cartesian%20java&f=false
    */
    //for now going to be a float for slightly better accuracy converting to and from PolarVector
    protected final float x;
    protected final float y;

    public CartesianVector(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public static PolarVector toPolar(float x, float y)
    {
        return new PolarVector
                (
                        (float) Math.sqrt((x*x)+(y*y)), //radius
                        (float) Math.atan2(y, x)  //angle, will get normalised by PolarVector's constructor
                );
    }
    public PolarVector toPolar()
    {
        return toPolar(x, y);
    }
}
