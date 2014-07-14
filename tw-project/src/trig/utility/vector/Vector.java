package trig.utility.vector;

import trig.utility.vector.forms.*;

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

}