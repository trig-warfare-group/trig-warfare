package trig.game.entity;

/**
 * Base class for objects that mostly move in a straight line, such as bullets
 * Created by marcos on 10/07/14.
 */
public abstract class Projectile extends BasicEntity implements Movable, Visible
{
    //note: some projectiles could still, concievably, refract or turn etc on collision?

    //should think about what properties all projectiles have, but that could maybe be done a bit later.
}
