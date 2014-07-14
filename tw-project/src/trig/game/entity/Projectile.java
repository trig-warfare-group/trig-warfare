package trig.game.entity;

import trig.utility.vector.*;

/**
 * Base class for objects that mostly move in a straight line, such as bullets
 * Created by marcos on 10/07/14.
 */
public abstract class Projectile extends GenericMoving implements updateListener
{
    public Projectile(/*int id,*/ int x, int y, int hitSize, Vector.CartesianForm vector) {
        super(/*id,*/ x, y, hitSize, vector);
    }

    public Projectile(/*int id,*/ int x, int y, int hitSize, Vector.PolarForm vector) {
        super(/*id,*/ x, y, hitSize, vector);
    }

    /**
     * Usable template for how projectiles should behave onTick
     */
    @Override
    public void onTick()
    {
        move();
    }

    //note: one reason /all/ projectiles might be destructible is that we'd probably want to limit the number of them that exist for lagg-stopping purposes, we might instead just do that for StBullet or something though, idk
    //note: some projectiles could still, concievably, refract or turn etc on collision?


    //should think about what properties all projectiles have, but that could maybe be done a bit later.
}
