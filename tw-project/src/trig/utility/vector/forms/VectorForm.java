package trig.utility.vector.forms;

/**
 * Vector data storage forms
 * @see trig.utility.vector.Vector
 * Created by marcos on 14/07/2014.
 */
public interface VectorForm
{
    abstract PolarForm toPolar();
    abstract CartesianForm toCartesian();
}
