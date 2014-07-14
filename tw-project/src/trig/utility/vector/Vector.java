package trig.utility.vector;

import trig.utility.vector.forms.CartesianForm;
import trig.utility.vector.forms.PolarForm;


/**
 * A vector, that is: a quantity containing both magnitude and direction.
 * Contains both a cartesianForm and polarForm, which have helper methods for conversion etc.
 */
public abstract class Vector
{
    protected final CartesianForm cartesianForm;
    protected final PolarForm polarForm;

    public CartesianForm getCartesianForm()
    {
        return cartesianForm;
    }

    public PolarForm getPolarForm()
    {
        return polarForm;
    }

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