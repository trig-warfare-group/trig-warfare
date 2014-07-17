package trig.utility;

/**
 * Created by marcos on 12/07/2014.
 */
public class Variables
{
    //stuff, like last entity id, ect?
    private long lastEntityId = 0; //TODO: THIS IS MOST LIKELY KINDA BAD PRACTICE, THE STRUCTURE THAT IS, NEED TO GET THE ENGINE UP AND PUT IT THERE, ETC?
    public synchronized long getNextEntityId()
    {
        return lastEntityId++;
    } //TODO: DESPITE BEING A LONG, WE PROBABLY NEED SOME WAY TO RECYCLE THESE. QUERY, DO STANDARD OBJECTS CONTAIN THEIR OWN UNIQUE ID WE COULD USE FOR THIS? MAYBE HASH (HASH SEEMS INEFFICIENT...)

    public synchronized long getLastEntityId()
    {
        return lastEntityId;
    }
}
