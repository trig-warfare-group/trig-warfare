package trig.game.entity;

import trig.utility.SRectangle;

/**
 * Requests a class to respect the Quad-Tree-format, for collision-detection.
 * Created by brody on 21/07/14.
 */
public interface QTCollision extends Collidable
{
    /**
     * A Collidable object must specify how it is to be collided with in a SDimension structure,
     * so that the Quad-Tree, can easily manipulate the object.
     * @return
     */
    public abstract SRectangle getCollisionRegion();
}
