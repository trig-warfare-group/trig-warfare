package trig.game.world;

import trig.utility.math.vector.*;

/**
 * Bad naming I know, but not all moving entities need a directional velocity, some might just move in a fixed pattern, I guess?
 * Created by marcos on 11/07/2014.
 */
public abstract class MovableWorldObject extends SWorldObject implements Movable
{
    MovableWorldObject(float x, float y)
    {
        super(x, y);
    }
    MovableWorldObject(FloatCartesian location){
        super(location);
    }

    /**
     * Moves the world by the specified amounts.
     */
    @Override
    public void move(float dX, float dY)
    {
        x += dX;
        y += dY;
    }

    /**
     * Moves an world by the provided amount
     *
     * @param vector a vector containing the distance in x and y by which to travel.
     */
    @Override
    public void move(FloatCartesian vector)
    {
        move(vector.x, vector.y);
    }

    /**
     * Moves an world to the provided coordinates
     *
     * @param x the x-coordinate to travel to
     * @param y the y-coordinate to travel to
     */
    @Override
    public void setLocation(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Moves an world to the provided coordinates
     *
     * @param vector a vector containing the x and y coordinates to travel to, in relation to 0,0
     */
    @Override
    public void setLocation(FloatCartesian vector)
    {
        setLocation(vector.x, vector.y);
    }

}
