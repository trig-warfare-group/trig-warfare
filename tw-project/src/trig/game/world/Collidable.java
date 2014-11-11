package trig.game.world;

import trig.utility.geometry.Polygon;

/**
 * Indicates that entities can collide with things and provides their hitbox for detecting these collisions.
 */
public interface Collidable extends WorldObject //may not stay an interface, might though
{
    /**
     * Note that returned hitbox may not be a clone (should be static later?)
     * @return
     */
    abstract public Polygon getHitbox(); //TODO: RESTRICT THE TYPE TO CONVEXPOLYGON WHEN ITS APPLICABLE, OR EVEN HITBOX

    @Deprecated //TODO: IMPLIMENT THE REPLACEMENT FOR THIS IN THE GAME ENGINE?
    /**
     * Gives the world a list of entities it has collided with, so that it can handle type-specific collision responses?
     * @param colliders
     */
    abstract public void onCollision(Collidable[] colliders);
}
