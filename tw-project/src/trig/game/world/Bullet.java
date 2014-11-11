package trig.game.world;

import trig.utility.geometry.Polygon;
import trig.utility.math.vector.FloatCartesian;

import java.awt.*;
import java.util.Random;

/**
 * Created by marcos on 25/07/2014.
 */
public class Bullet extends StandardWorldObject implements Projectile, Automaton
{
    public static final float speed = 20;
    protected Color color;
    protected FloatCartesian velocity;
    protected int timer = 60*5;

    protected boolean spent;

    /**
     *
     * @param location
     * @param unitVelocity a unit vector representing the direction in which to travel.
     */
    public Bullet(FloatCartesian location, FloatCartesian unitVelocity)
    {
        super(location);
        velocity = unitVelocity;
        velocity.scale(speed);
        hitbox = BasicWorldObject.constructGenericTriangle(5);

        hitbox.rotate(unitVelocity.direction());

        hitbox.translate(location);


        Random r = new Random();
        color = new Color(
                r.nextInt((250 - 75) + 1) + 75,
                r.nextInt((250 - 75) + 1) + 75,
                r.nextInt((250 - 75) + 1) + 75
        );
        super.setDmg(1);
        spent = false;
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
//        //other entities handle spending the bullet, but the world edge doesn't
//        if(colliders.length > 0){
//            for(Collidable each : colliders){
//                if(each instanceof WorldEdge){
//                    this.spent = true;
//                }
//            }
//        }
    }

    /**
     * You know, like "spent ammunition"
     */
    public boolean isSpent()
    {
        return spent;
    }

    /**
     * Bullets can only collide with a maximum of 1 world!
     * Note: internally, we could give bullets penetration by using an integer instead of a boolean?
     * Note: currently bullets cannot be un-spent.
     */
    public void spend()
    {
        this.spent = true;
    }

    public boolean isTrash()
    {
        return isSpent();
    }



    /*
        Must override move() and setLocation() so the hitbox can be moved with the world!
     */
    @Override
    public void move(FloatCartesian shift)
    {
        super.move(shift);
        hitbox.translate(shift);
    }

    @Override
    public void render(Graphics2D g)
    {
        g.setColor(color);

        hitbox.render(g);
    }

    @Override
    public FloatCartesian getVelocity()
    {
        return velocity.clone();
    }

    @Override
    public void setVelocity(FloatCartesian velocity)
    {
        this.velocity = velocity.clone();
    }

    @Override
    public void update()
    {
        if(timer > 0)
        {
            timer--;
            move(velocity);
        }
        else
        {
            spend();
        }
    }
}
