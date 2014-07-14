package trig.utility.vector.forms;

/**
 * a vector in the form X, Y, explicitly starting at the origin
 * Created by marcos on 11/07/2014.
 */
public abstract class CartesianForm implements VectorForm
{
    /*
        e.g. an XYVector like (0,1) is a line from (0,0) to (0,1)

        Some reading on this math:
        http://books.google.com.au/books?id=n6heB7tU6SwC&pg=PA271&lpg=PA271&dq=general+formula+for+polar+vector+from+cartesian+java&source=bl&ots=xElApUfYfB&sig=YA-Z7JmYPKBY8f088kGLpBu3KiQ&hl=en&sa=X&ei=voK_U7jKKoHfkAWdmIC4Cw&ved=0CCwQ6AEwAg#v=onepage&q=general%20formula%20for%20polar%20vector%20from%20cartesian%20java&f=false
        */
    /*
        only defining this for the sake of the interface?
     */
    @Override
    public CartesianForm toCartesian()
    {
        return this;
    }

}
