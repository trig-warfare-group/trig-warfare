package trig.game.entity;

import trig.utility.vector.Vector;

/**
 * Bad naming I know, but not all moving entities need a directional velocity, some might just move in a fixed pattern, I guess?
 * Created by marcos on 11/07/2014.
 */
public abstract class GenericMoving extends BasicEntity implements Ambulant{
    //data used to calculate x and y velocities.

    protected Vector velocity;

    //the distance by which to move each tick, stored here for processing speed enhancement.
    //int because if x and y are X, then we'll end up rounding every time anyway.
    protected int velX;
    protected int velY;

    GenericMoving(/*int id,*/ int x, int y, int hitSize) {
        super(/*id,*/ x, y, hitSize);
    }

    public GenericMoving(/*int id,*/ int x, int y, int hitSize, Vector.CartesianForm vector)
    {
        //pipe these straight to the BasicEntity constructor.
        super(/*id,*/ x, y, hitSize);
        this.setVel(vector);
    }

    public GenericMoving(/*int id,*/ int x, int y, int hitSize, Vector.PolarForm vector)
    {
        //pipe these straight to the BasicEntity constructor.
        super(/*id,*/ x, y, hitSize);
        this.setVel(vector);
    }
    //getters & setters
    public Vector.PolarForm getPolarVel()
    {
        return polarVel;
    }

    /**
     * Gets the actual vector used when moving, since it can only move in integer amounts, etc..
     */
    public Vector.CartesianForm getRealCartVel(){
        return new Vector.CartesianForm((float) velX, (float) velY); //may end up changing things so we don't need to create a new one each time
    }

    /**
     * Updates velocity based on a PolarForm
     */
    public void setVel(Vector.CartesianForm vector)
    {
        polarVel = vector.toPolar();
        velX = Math.round(vector.x);
        velY = Math.round(vector.y);
    }

    /**
     * Updates velocity based on a CartesianForm
     */
    public void setVel(Vector.PolarForm vector)
    {
        polarVel = vector;
        polarVel.Cart
        Vector.CartesianForm cartVel = polarVel.toCartesian();
        velX = Math.round(cartVel.x);
        velY = Math.round(cartVel.y);
    }



    /**
     * Standard movement method for projectiles, just moves by velocity.
     */
    protected void move()
    {
        int newX = x+velX;
        int newY = y+velY;

        x = newX;
        y = newY;
    }
}
