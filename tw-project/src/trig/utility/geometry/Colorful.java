package trig.utility.geometry;

/**
 * An interface for colourful editions of geometric class, which must all have the capacity to be stripped of their colour.
 * @see trig.utility.geometry.Renderable
 * Created by marcos on 18/07/2014.
 */
public interface Colorful
{
    /**
     * Produces a new colourless version of the object
     * @return a deep copy of the object, excluding any colour data
     */
    public Renderable stripColor();
}
