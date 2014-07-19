package trig.game.entity;

import trig.game.engine.GameEngine;
import trig.game.entity.interfaces.UpdateListener;
import trig.utility.math.vector.*;

/**
 * Base class for objects that mostly move in a straight line, such as bullets
 * Created by marcos on 10/07/14.
 */
public abstract class Projectile extends GenericMoving implements UpdateListener
{
    public Projectile(/*int id,*/ int x, int y, int hitSize, Vector vector) {
        super(/*id,*/ x, y, hitSize, vector);
    }
    /**
     * Usable template for how projectiles should behave onTick?
     */
    @Override
    public void update(GameEngine engine)
    {
        move();
    }

    //note: one reason /all/ projectiles might be destructible is that we'd probably want to limit the number of them that exist for lagg-stopping purposes, we might instead just do that for StBullet or something though, idk
    //note: some projectiles could still, concievably, refract or turn etc on collision?


    //should think about what properties all projectiles have, but that could maybe be done a bit later.
}
