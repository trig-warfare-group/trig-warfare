package trig.game.entity;

import trig.utility.math.vector.IntCartesian;

/**
 * Indicates that the entity can have it's coordinates manually manipulated?
 * Note: not sure where we will actually use this?
 * @author marcos
 */
public interface Movable extends Entity //may not stay an interface, might though
{
    /**
     * Moves an entity by the provided amount
     * @param dX the distance to travel along the x-axis
     * @param dY the distance to travel along the y-axis.
     */
    abstract public void move(int dX, int dY);

    /**
     * Moves an entity by the provided amount
     * @param vector a vector containing the distance in x and y by which to travel.
     */
    abstract public void move(IntCartesian vector);

    /**
     * Moves an entity to the provided coordinates
     * @param x the x-coordinate to travel to
     * @param y the y-coordinate to travel to
     */
    abstract public void setLocation(int x, int y);

    /**
     * Moves an entity to the provided coordinates
     * @param vector a vector containing the x and y coordinates to travel to, in relation to 0,0
     */
    abstract public void setLocation(IntCartesian vector);
}
