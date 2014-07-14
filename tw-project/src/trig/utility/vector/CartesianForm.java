package trig.utility.vector;

/**
 * a vector in the form X, Y, explicitly starting at the origin
 * Created by marcos on 11/07/2014.
 */
public abstract class CartesianForm implements VectorForm{
    /*
        only defining this for the sake of the interface?
     */
    @Override
    public CartesianForm toCartesian()
    {
        return this;
    }

    /**
     * Floating-point accuracy VectorForm.PolarForm
     * @see CartesianForm
     */
    public static class Float extends CartesianForm
    {
        //for now going to be a float for slightly better accuracy converting to and from PolarForm
        public final float x;
        public final float y;

        /**
         * @param x a distance from origin along the x-axis
         * @param y a distance from origin along the y-axis
         */
        public Float(float x, float y)
        {
            this.x = x;
            this.y = y;
        }

        /**
         * Produces a PolarForm equivalent of the given data
         * @param x a distance from origin along the x-axis
         * @param y a distance from origin along the y-axis
         * @return a vector from given data, in polar form
         */
        public static PolarForm.Float toPolar(float x, float y)
        {
            return new PolarForm.Float
                    (
                            (float) Math.sqrt((x * x) + (y * y)), //radius
                            (float) Math.atan2(y, x)  //angle, will get normalised by PolarForm's constructor
                    );
        }

        @Override
        public PolarForm.Float toPolar()
        {
            return toPolar(x, y);
        }
    }

    public static class Int extends CartesianForm
    {
        //for now going to be a float for slightly better accuracy converting to and from PolarForm
        public final int x;
        public final int y;

        /**
         * @param x a distance from origin along the x-axis
         * @param y a distance from origin along the y-axis
         */
        public Int(int x, int y)
        {
            this.x = x;
            this.y = y;
        }

        /**
         * Produces a PolarForm equivalent of the given data
         * @param x a distance from origin along the x-axis
         * @param y a distance from origin along the y-axis
         * @return a vector from given data, in polar form
         */
        public static PolarForm.Int toPolar(int x, int y)
        {
            return new PolarForm.Int
                    (
                            (float) Math.sqrt((x * x) + (y * y)), //radius
                            (float) Math.atan2(y, x)  //angle, will get normalised by PolarForm's constructor
                    );
        }

        @Override
        public PolarForm.Int toPolar()
        {
            return toPolar(x, y);
        }
    }
/*
    e.g. an XYVector like (0,1) is a line from (0,0) to (0,1)

    Some reading on this math:
    http://books.google.com.au/books?id=n6heB7tU6SwC&pg=PA271&lpg=PA271&dq=general+formula+for+polar+vector+from+cartesian+java&source=bl&ots=xElApUfYfB&sig=YA-Z7JmYPKBY8f088kGLpBu3KiQ&hl=en&sa=X&ei=voK_U7jKKoHfkAWdmIC4Cw&ved=0CCwQ6AEwAg#v=onepage&q=general%20formula%20for%20polar%20vector%20from%20cartesian%20java&f=false
*/
}
