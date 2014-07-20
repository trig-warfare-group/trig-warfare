package trig.utility.math;

/**
 * Created by marcos on 17/07/2014.
 */
public class Methods
{
    /**
     * Normalises a randian angle to be between [-PI,PI]
     * Note: [-PI,PI] is the domain (or is it range?) I believe we will need most? May need to update, or overload with multiple domains (ranges?)
     * @param theta
     * Created by marcos on 11/07/2014.
     */
    public static float normalise(float theta)
    {
        float dubPiEst = (float) Math.PI*2;

        while (theta <= -Math.PI)
        {
            theta += dubPiEst;
        }

        while (theta > Math.PI)
        {
            theta -= dubPiEst;
        }
        return theta;
    }
}
