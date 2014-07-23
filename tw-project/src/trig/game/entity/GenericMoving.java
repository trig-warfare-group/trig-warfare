package trig.game.entity;

import trig.game.entity.interfaces.Ambulant;
import trig.utility.math.vector.*;

/**
 * Bad naming I know, but not all moving entities need a directional velocity, some might just move in a fixed pattern, I guess?
 * Created by marcos on 11/07/2014.
 */
public abstract class GenericMoving extends BasicEntity implements Ambulant
{
    //the distance by which to move each tick
    protected FloatCartesian velocity;

    public FloatCartesian getVelocity()
    {
        return velocity;
    }

    public void setVelocity(FloatCartesian velocity)
    {
        this.velocity = velocity;
    }

    public GenericMoving(/*int id,*/ int x, int y, int hitSize, FloatCartesian vector)
    {
        //pipe these straight to the BasicEntity constructor.
        super(/*id,*/ x, y, hitSize);
        this.setVelocity(vector);
    }
    /**
     * Standard movement method for projectiles, just moves by velocity.
     */
    protected void move()
    {
        int newX = x+velocity.inCartesian().getX();
        int newY = y+velocity.inCartesian().getY();

        x = newX;
        y = newY;
    }
}
