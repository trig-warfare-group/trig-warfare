package trig.game.world;

import trig.utility.math.vector.*;

/**
 * Bad naming I know, but not all moving entities need a directional velocity, some might just move in a fixed pattern, I guess?
 * Created by marcos on 11/07/2014.
 */
//public abstract class BasicMovable extends BasicWorldObject implements Movable
//{
////    MovableWorldObject(float x, float y)
////    {
////        super(x, y);
////    }
//    BasicMovable(FloatCartesian location){
//        super(location);
//    }
//
////    /**
////     * Moves the world by the specified amounts.
////     */
////    @Override
////    public void move(float dX, float dY)
////    {
////        x += dX;
////        y += dY;
////    }
//
//    /**
//     * Moves an world by the provided amount
//     *
//     * @param shift a vector containing the distance in x and y by which to travel.
//     */
//    @Override
//    public void move(FloatCartesian shift)
//    {
//        location.sum(shift);
//    }
//
////    /**
////     * Moves an world to the provided coordinates
////     *
////     * @param x the x-coordinate to travel to
////     * @param y the y-coordinate to travel to
////     */
////    @Override
////    public void setLocation(float x, float y)
////    {
////        this.x = x;
////        this.y = y;
////    }
//
//    /**
//     * Sets the location of the object to the provided location, does not implicitly invoke move.
//     */
//    @Override
//    public void setLocation(FloatCartesian location)
//    {
//        this.location = location.clone();
//    }
//
//}
