package trig.game.world;

import trig.utility.Constants;
import trig.utility.geometry.Polygon;
import trig.utility.math.vector.FloatCartesian;

/**
 * Dummy class to be
 * Created by marcos on 25/07/2014.
 */
public class WorldEdge implements Collidable
{

    protected Polygon hitbox;

    public WorldEdge()
    {
        hitbox = new Polygon();
        hitbox.add(
                new FloatCartesian(Constants.WORLD_COLLISION_PADDING, Constants.WORLD_COLLISION_PADDING)
        );
        hitbox.add( new FloatCartesian(
                Constants.WORLD_DIM.width-Constants.WORLD_COLLISION_PADDING,
                Constants.WORLD_DIM.height-Constants.WORLD_COLLISION_PADDING)
        );
    }
    /**
     * Note that returned hitbox may not be a clone (should be static later?)
     *
     * @return
     */
    @Override
    public Polygon getHitbox()
    {
        return hitbox;
    }

    /**
     * Gives the world a list of entities it has collided with, so that it can handle type-specific collision responses?
     *
     * @param colliders
     */

    @Override
    public void onCollision(Collidable[] colliders)
    {

    }

    @Override
    public FloatCartesian getLocation()
    {
        return null;
    }

    /**
     * Indicates that the engine should delete the world from it's list.
     * Note: sort of forces the engine to do garbage collection, but it's a well-encapsulated way of doing it? Keep? Y/n?
     */
    @Override
    public boolean isTrash()
    {
        return false;
    }
}
