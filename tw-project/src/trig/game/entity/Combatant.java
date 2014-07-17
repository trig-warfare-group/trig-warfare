/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package trig.game.entity;
import trig.game.entity.interfaces.Living;
import trig.game.entity.interfaces.Visible;
import trig.utility.math.vector.CartesianForm;
import trig.utility.math.vector.PolarForm;

import java.awt.Color;

/**
 * Base class for combatants, such as the player, AI.
 * @author marcos
 */
public abstract class Combatant extends GenericMoving implements Living, Visible
{
    //constants/semi defaults? - NOT TO BE USED DEFAULTS, MAINLY
    //protected static final float ST_TURN = (float) (((float)1/18)*Math.PI);
    //protected static final int ST_DIST = 1;

    //random defaults?
    //public static final int DEF_HP = 10;
    //public static final Color DEF_COLOR = Color.WHITE;
    //public static final float DEF_HIT_RADIUS = 0; //circle of diameter = 50
    //public static final float DEF_SPEED = 1; //no multiplicative effect

    //vars
    protected String name;
    protected int hp;
    protected int maxHp;
    protected Color color;

    //movement speed multiplier factor? (not actual velocity)
    protected float speed;

    //turn speed multiplier?
    //Note: possibly not using the agility factor, at least not for turning, we'll see, may be too annoying for mouse users etc.
    protected float agility;

    //getters and setters
    @Override
    public int getHp()
    {
        return hp;
    }

    public void damage(int points){
        int newHp = getHp()-points;
        if(newHp < 0)
        {
            newHp = 0;
        }
        setHp(newHp);
    }

    public void heal(int points){
       int newHp = getHp()+points;
       if(newHp > maxHp){
           newHp = maxHp;
       }
       setHp(newHp);
    }

    protected void setHp(int hp)
    {
        this.hp = hp;
        if(hp <= 0)
        {
            //die(); // protected abstract void die();
        }
    }

    public String getName()
    {
        return name;
    }

    public Color getColor()
    {
        return color;
    }

    public float getSpeed()
    {
        return speed;
    }

    public void setSpeed(float speed)
    {
        this.speed = speed;
        //update the vector.

        //get the one form rather than potentially converting it repeatedly
        PolarForm tempVel = velocity.inPolar();
        setVelocity(
                new PolarForm(
                        tempVel.radius * this.speed,
                        tempVel.angle
                )
        );
    }

    public float getAgility()
    {
        return agility;
    }

    public void setAgility(float agility)
    {
        this.agility = agility;
    }

    //helpers

    @Override
    public boolean isAlive()
    {
        return hp > 0;
    }

    @Override
    public void kill(){
        setHp(0);
    }

    @Override
    public boolean isMapped()
    {
        return isAlive();
    }

    public Combatant(/*int id,*/ String name, int hitSize, int maxHp, Color color, float speed)
    {
        super(
                /*id,*/
                0, //x
                0, //y
                hitSize, //hitSize
                new CartesianForm(0,0) //vector
        );

        this.name = name; // "Dummy_"+id;
        this.hitSize = hitSize;
        this.maxHp = maxHp;
        this.color = color;

        this.speed = speed;
    }

    /**
     * Checks whether or not it is alive, then moves normally
     * @see protected void trig.game.entity.GenericMoving.move();
     */
    @Override
    protected void move()
    {
        if(isMapped()){
            super.move();
        }
    }

    //TODO: MAKE STANDARD TURN FUNCTION, MAKE DEMO KEYBOARD EVENT STUFF MAYBE?
}