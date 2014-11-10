package trig.game.world;

import trig.utility.math.vector.FloatCartesian;

/**
 * Interface class for all entities, where entities are anything that can interact with other entities in the game.
 * @author marcos
 * Created by marcos on 8/07/2014.
 */
public interface WorldObject
{
    /*
    these would be made Public by the interface!, interfaces only define things that can be interfaced?
    //cartesian coordinates
    int x = 0;
    int y = 0;
    */
    abstract public float getX();
    abstract public float getY();

    abstract public FloatCartesian getLocation();

    /**
     * Indicates that the engine should delete the world from it's list.
     * Note: sort of forces the engine to do garbage collection, but it's a well-encapsulated way of doing it? Keep? Y/n?
     */
    abstract public boolean isTrash();
}
