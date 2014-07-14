package trig.utility.vector;

import trig.utility.vector.forms.cartesian.CartesianFloat;
import trig.utility.vector.forms.polar.PolarFloat;

/**
 * @see trig.utility.vector.Vector
* Created by marcos on 14/07/2014.
*/
public class FloatVector extends Vector
{

    public FloatVector(CartesianFloat baseForm)
    {
        super(baseForm);
    }
    public FloatVector(PolarFloat baseForm)
    {
        super(baseForm);
    }
}
