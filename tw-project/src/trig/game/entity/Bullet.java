package trig.game.entity;

import trig.utility.geometry.Polygon;
import trig.utility.math.vector.IntCartesian;

import java.awt.*;
import java.util.Random;

/**
 * Created by marcos on 25/07/2014.
 */
public class Bullet extends Projectile implements Harmful, Collidable, Visible
{
    public static final IntCartesian BASE_VELOCITY = new IntCartesian(4, 0); //velocity: 4 in whatever direction: (4,0) is right

    protected int dmg = 4;
    protected boolean spent = false;
    protected Color color;

    protected Polygon hitbox;
    public Bullet(int x, int y, float direction)
    {
        super(x, y, BASE_VELOCITY);


        //rotate the velocity to the right direction.
        velocity.rotate(direction);

        hitbox = SEntity.constructGenericTriangle(5); //size: 50
        hitbox.rotate(direction);
        hitbox.translate(x, y);



        Random r = new Random();
        color = new Color(
                r.nextInt((250 - 75) + 1) + 75,
                r.nextInt((250 - 75) + 1) + 75,
                r.nextInt((250 - 75) + 1) + 75
        );

    }

    public Bullet(IntCartesian location, float direction)
    {
        this(location.x, location.y, direction);
    }

    @Override
    public int getDmg()
    {
        return dmg;
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
     * Gives the entity a list of entities it has collided with, so that it can handle type-specific collision responses?
     *
     * @param colliders
     */
    @Override
    public void onCollision(Collidable[] colliders)
    {
        //other entities handle spending the bullet, but the world edge doesn't
        if(colliders.length > 0){
            for(Collidable each : colliders){
                if(each instanceof WorldEdge){
                    this.spent = true;
                }
            }
        }
    }

    /**
     * You know, like "spent ammunition"
     */
    public boolean isSpent()
    {
        return spent;
    }

    /**
     * Bullets can only collide with a maximum of 1 entity!
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
        Must override move() and setLocation() so the hitbox can be moved with the entity!
     */
    @Override
    public void move(int dX, int dY)
    {
        super.move(dX, dY);

        hitbox.translate(dX, dY);
    }

    @Override
    public void setLocation(int x, int y)
    {
        super.setLocation(x, y);

        hitbox.translate(
                x-this.x,
                y-this.y
        );
    }
    @Override
    public void render(Graphics2D g)
    {
        g.setColor(color);

        hitbox.render(g);
    }
}
