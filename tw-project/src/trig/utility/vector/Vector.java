package trig.utility.vector;

import trig.utility.Methods;

/**
 * A vector, that is a quantity containing both magnitude and direction.
 * Contains both a cartesianForm and polarForm
 */
public abstract class Vector
{
    public final CartesianForm cartesianForm;
    public final PolarForm polarForm;
    public Vector(CartesianForm baseForm)
    {
        cartesianForm = baseForm;
        polarForm = baseForm.toPolar();
    }

    public Vector(PolarForm baseForm)
    {
        polarForm = baseForm;
        cartesianForm = baseForm.toCartesian();
    }

    public static class Float extends Vector
    {

        public Float(CartesianForm.Float baseForm)
        {
            super(baseForm);
        }
    }
    public static class Int extends Vector
    {
        public Int(CartesianForm.Int baseForm)
        {
            super(baseForm);
        }
    }
}