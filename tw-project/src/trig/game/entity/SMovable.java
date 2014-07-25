package trig.game.entity;

import trig.utility.math.vector.*;

/**
 * Bad naming I know, but not all moving entities need a directional velocity, some might just move in a fixed pattern, I guess?
 * Created by marcos on 11/07/2014.
 */
public abstract class SMovable extends SEntity implements Movable
{
    SMovable(int x, int y)
    {
        super(x, y);
    }

    /**
     * Moves the entity by the specified amounts.
     */
    @Override
    public void move(int dX, int dY)
    {
        x += dX;
        y += dY;
    }

    /**
     * Moves an entity by the provided amount
     *
     * @param vector a vector containing the distance in x and y by which to travel.
     */
    @Override
    public void move(IntCartesian vector)
    {
        move(vector.x, vector.y);
    }

    /**
     * Moves an entity to the provided coordinates
     *
     * @param x the x-coordinate to travel to
     * @param y the y-coordinate to travel to
     */
    @Override
    public void setLocation(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Moves an entity to the provided coordinates
     *
     * @param vector a vector containing the x and y coordinates to travel to, in relation to 0,0
     */
    @Override
    public void setLocation(IntCartesian vector)
    {
        setLocation(vector.x, vector.y);
    }

}
