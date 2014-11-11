package trig.game.world;

import trig.utility.math.vector.FloatCartesian;

/**
 * Indicates that the world can have it's coordinates manually manipulated?
 * Note: not sure where we will actually use this?
 * @author marcos
 */
public interface Movable extends WorldObject //may not stay an interface, might though
{
//    /**
//     * Moves an world by the provided amount
//     * @param dX the distance to travel along the x-axis
//     * @param dY the distance to travel along the y-axis.
//     */
//    abstract public void move(float dX, float dY);

    /**
     * Moves an world by the provided amount
     * @param vector a vector containing the distance in x and y by which to travel.
     */
    abstract public void move(FloatCartesian vector);

//    /**
//     * Moves an world to the provided coordinates
//     * @param x the x-coordinate to travel to
//     * @param y the y-coordinate to travel to
//     */
//    abstract public void setLocation(float x, float y);

    /**
     * Moves an world to the provided coordinates
     * @param vector a vector containing the x and y coordinates to travel to, in relation to 0,0
     */
    abstract public void setLocation(FloatCartesian vector);
}
