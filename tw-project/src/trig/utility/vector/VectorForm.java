package trig.utility.vector;

import trig.utility.Methods;

/**
 * Vector data storage forms
 * Created by marcos on 14/07/2014.
 */
public interface VectorForm
{
    abstract PolarForm toPolar();
    abstract CartesianForm toCartesian();
}
