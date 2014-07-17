package trig.utility;

/**
 * Stores some helper methods for physics-related math
 * Created by marcos on 11/07/2014.
 */
public class Methods
{
    /**
     * Normalises a randian angle to be between [-PI,PI]
     * Note: [-PI,PI] is the domain (or is it range?) I believe we will need most? May need to update, or overload with multiple domains (ranges?)
     * @param angle
     * Created by marcos on 11/07/2014.
     */
    public static float normalise(float angle)
    {
        while (angle <= -Math.PI)
        {
            angle += Math.PI*2;
        }

        while (angle > Math.PI)
        {
            angle -= Math.PI*2;
        }
        return angle;
    }
    public static Variables DummyVars = new Variables();

}
