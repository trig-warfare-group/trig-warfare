package trig.utility.vector;

import trig.utility.vector.forms.cartesian.FloatCartesian;
import trig.utility.vector.forms.polar.FloatPolar;

/**
 * @see trig.utility.vector.Vector
* Created by marcos on 14/07/2014.
*/
public class FloatVector extends Vector
{

    public FloatVector(FloatCartesian baseForm)
    {
        super(baseForm);
    }
    public FloatVector(FloatPolar baseForm)
    {
        super(baseForm);
    }
}
