package trig.game.entity;

import trig.game.entity.Entity;
import trig.utility.geometry.Polygon;

/**
 * Base class for entities that move around
 *
 * Created by marcos on 8/07/2014.
 */
public interface Collidable //may not stay an interface, might though
{
    /**
     * Note that returned hitbox may not be a clone (should be static later?)
     * @return
     */
    abstract public Polygon getHitbox(); //TODO: RESTRICT THE TYPE TO CONVEXPOLYGON WHEN ITS APPLICABLE, OR EVEN HITBOX


    /**
     * Gives the entity a list of entities it has collided with, so that it can handle type-specific collision responses?
     * @param colliders
     */
    abstract public void onCollision(Collidable[] colliders);
}
