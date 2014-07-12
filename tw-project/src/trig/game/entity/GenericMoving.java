package trig.game.entity;

import trig.utility.vector.CartesianVector;
import trig.utility.vector.PolarVector;

/**
 * Bad naming I know, but not all moving entities need a directional velocity, some might just move in a fixed pattern, I guess?
 * Created by marcos on 11/07/2014.
 */
public abstract class GenericMoving extends BasicEntity implements Ambulant{
    //data used to calculate x and y velocities.

    protected PolarVector polarVel;

    //the distance by which to move each tick, stored here for processing speed enhancement.
    //int because if x and y are X, then we'll end up rounding every time anyway.
    protected int velX;
    protected int velY;

    GenericMoving(int id, int x, int y, float hitRadius) {
        super(id, x, y, hitRadius);
    }

    //getters & setters
    public PolarVector getPolarVel()
    {
        return polarVel;
    }

    /**
     * Gets the actual vector used when moving, since it can only move in integer amounts, etc..
     */
    public CartesianVector getRealCartVel(){
        return new CartesianVector((float) velX, (float) velY); //may end up changing things so we don't need to create a new one each time
    }

    /**
     * Updates velocity based on a PolarVector
     */
    public void setVel(CartesianVector vector)
    {
        polarVel = vector.toPolar();
        velX = Math.round(vector.x);
        velY = Math.round(vector.y);
    }

    /**
     * Updates velocity based on a CartesianVector
     */
    public void setVel(PolarVector vector)
    {
        polarVel = vector;
        CartesianVector cartVel = polarVel.toCartesian();
        velX = Math.round(cartVel.x);
        velY = Math.round(cartVel.y);
    }

    public GenericMoving(int id, int x, int y, float hitRadius, CartesianVector vector)
    {
        //pipe these straight to the BasicEntity constructor.
        super(id, x, y, hitRadius);
        this.setVel(vector);
    }

    public GenericMoving(int id, int x, int y, float hitRadius, PolarVector vector)
    {
        //pipe these straight to the BasicEntity constructor.
        super(id, x, y, hitRadius);
        this.setVel(vector);
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
