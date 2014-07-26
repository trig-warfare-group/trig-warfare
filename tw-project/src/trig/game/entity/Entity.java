package trig.game.entity;

import trig.utility.math.vector.IntCartesian;

import java.awt.geom.Path2D;

/**
 * Interface class for all entities, where entities are anything that can interact with other entities in the game.
 * @author marcos
 * Created by marcos on 8/07/2014.
 */
public interface Entity
{
    /*
    these would be made Public by the interface!, interfaces only define things that can be interfaced?
    //cartesian coordinates
    int x = 0;
    int y = 0;
    */
    abstract public int getX();
    abstract public int getY();

    abstract public IntCartesian getLocation();

    /**
     * Indicates that the engine should delete the entity from it's list.
     * Note: sort of forces the engine to do garbage collection, but it's a well-encapsulated way of doing it? Keep? Y/n?
     */
    abstract public boolean isTrash();
}
