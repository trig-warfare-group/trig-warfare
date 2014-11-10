package trig.game.world;

import trig.utility.math.vector.FloatCartesian;

/**
 * Base class for objects that mostly move in a straight line, such as bullets
 * Created by marcos on 10/07/14.
 */
public abstract class Projectile extends MovableWorldObject implements Automaton
{
    protected FloatCartesian velocity;

    public FloatCartesian getVelocity()
    {
        return velocity.clone();
    }

    public void setVelocity(FloatCartesian velocity)
    {
        this.velocity = velocity.clone();
    }

    public Projectile(/*int id,*/ float x, float y, FloatCartesian velocity) {
        super(x, y);
        setVelocity(velocity);
    }

    public Projectile(FloatCartesian location, FloatCartesian velocity)
    {
        super(location);
        setVelocity(velocity);
    }
    /**
     * Usable template for how projectiles should behave onTick?
     */
    @Override
    public void update()
    {
        move(velocity);
    }

    //note: one reason /all/ projectiles might be destructible is that we'd probably want to limit the number of them that exist for lagg-stopping purposes, we might instead just do that for StBullet or something though, idk
    //note: some projectiles could still, concievably, refract or turn etc on collision?


    //should think about what properties all projectiles have, but that could maybe be done a bit later.
}
