/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package trig.game.entity;
import trig.utility.vector.Vector;

import java.awt.Color;

/**
 * Base class for combatants, such as the player, AI.
 * @author marcos
 */
public abstract class Combatant extends GenericMoving implements Living, Visible
{
    //constants/semi defaults? - NOT TO BE USED DEFAULTS, MAINLY
    protected static final float ST_TURN = (float) (((float)1/18)*Math.PI);
    protected static final int ST_DIST = 1;

    //random defaults?
    public static final int DEF_HP = 10;
    public static final Color DEF_COLOR = Color.WHITE;
    public static final float DEF_HIT_RADIUS = 0; //circle of diameter = 50
    public static final float DEF_SPEED = 1; //no multiplicative effect

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
        int newhp = getHp()-points;
        if(newhp < 0)
        {
            newhp = 0;
        }
        setHp(newhp);
    }

    public void heal(int points){
       int newhp = getHp()+points;
       if(newhp > maxHp){
           newhp = maxHp;
       }
       setHp(newhp);
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
        setVel(
                new Vector.PolarForm(
                        polarVel.radius*this.speed,
                        polarVel.angle
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
                new Vector.PolarForm(0,0) //vector
        );

        this.name = name; // "Dummy_"+id;
        this.hitSize = hitSize;
        this.maxHp = maxHp;
        this.color = color;

        this.speed = speed;
    }


//    public Combatant(/*int id*/) //TODO: don't use this one in practice!, possibly remove most of the constants too.
//    {
//        this(/*id,*/ "", DEF_HIT_RADIUS, DEF_HP, DEF_COLOR, DEF_SPEED);
//        name = "Combatant_"+id;
//    }

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

    //TODO: BOUNDARY HIT MANAGEMENT IN GENERICMOVING

    //TODO: MAKE STANDARD TURN FUNCTION, MAKE DEMO KEYBOARD EVENT STUFF MAYBE?
}

////Old version, only good for code scraps, etc
//public abstract class Combatant implements Entity, Visible, Ambulant
//{
//    //constants
//    //is there any reason for these to be public?
//    //a standard amount by which to turn a combatant by at a time? 1/36th of a circle at a time maybe? experimental value
//    protected static final float STANDARD_TURN = (float) (((float)1/18)*Math.PI);
//
//    //a standard amount by which to move at a time? not sure yet if will be expressed in px or something, but yeh. experimental value
//    protected static final int STANDARD_DISTANCE = 5;
//
//    protected int hitPoints;
//
//    //No need to update name, ID, or color within a single match, so may aswell make them final and publically accessible?
//
//    protected String name;
//
//    //likely to correspond to the Combatant's position in some list?
//    public final int id;
//
//    //color of the combatant
//    //no setter by default, subclasses can make one if needed, most shouldn't though?
//    public Color color;
//
//    private boolean spawned;
//
//    //cartesian coordinates
//    protected int x;
//    protected int y;
//
//    //the direction in which the combatant is facing. Double unneccassary?
//    //expressed in radians
//    //if memory serves, we will need to use atan2 most often, so the angle will work like atan2 needs it to: 0 is 3 o'clock, must be between [-1pi, 1pi], moves anticlockwise, 12 o'clock is pi/2, 6 o'clock is -pi/2.
//    protected float direction;
//
//    //movement speed multiplier factor?
//    protected float speed;
//
//    //turn speed multiplier?
//    //Note: possibly not using the agility factor, at least not for turning, we'll see, may be too annoying for mouse users etc.
//    protected float agility;
//
//    //could also have special attributes like speed or armour in the future.
//
//    /*getters and setter*/
//
//    public String getName()
//    {
//        return name;
//    }
//
//    public int getHitPoints()
//    {
//        return hitPoints;
//    }
//
//    public void setHitPoints(int hitPoints)
//    {
//        this.hitPoints = hitPoints;
//    }
//    //NOTE: not sure that every combatant should be allowed to get a name change yet, leaving it for now?
//
//    public int getX()
//    {
//        return x;
//    }
//
//    public int getY()
//    {
//        return y;
//    }
//
//    /*
//    //do we want all combatants, including players, so have their x set? the server can forcedly update the position of enemies, but does that apply to players?, possible teleportation, etc
//    //no?
//    public void setX(int x)
//    {
//        this.x = x;
//    }
//
//    public void setY(int y)
//    {
//        this.y = y;
//    }
//    */
//
//    public float getDirection()
//    {
//        return direction;
//    }
//
//    public void setDirection(float direction)
//    {
//        this.direction = direction;
//    }
//
//    public float getSpeed()
//    {
//        return speed;
//    }
//
//    public void setSpeed(float speed)
//    {
//        this.speed = speed;
//    }
//
//    public float getAgility()
//    {
//        return agility;
//    }
//
//    public void setAgility(float agility)
//    {
//        this.agility = agility;
//    }
//
//    public Combatant(int hitPoints, String name, int id, Color color, int x, int y, float direction, float speed, float agility)
//    {
//        this.hitPoints = hitPoints;
//        this.name = name;
//        this.id = id;
//        this.color = color;
//
//        //in the long run there will probably be a list of available spawn points for combatants provided by the map
//        //possibly even segregated into specific types
//        //so this code is temporary
//        //note: is it worth using a float for x and y? probably not.
//        this.x = x;
//        this.y = y;
//        this.direction = MathUtils.normalise(direction);
//
//        this.speed = speed;
//        this.agility = agility;
//    }
//
//    /*
//     Some 'helper' functions all of which may not be kept
//    */
//
//    public boolean isAlive()
//    {
//        return hitPoints > 0;
//    }
//
//    //NOTE: WHEN DOING LAGG MOVEMENT IT MIGHT BE USEFUL TO USE THE BASIC MOVE AND TURN FUNCTIONS FOR REMOTE ONES AS WELL, BUT IT MIGHT NOT, WE MIGHT JUST QUE EVENTS INSTEAD? WE'LL HAVE TO THINK ABOUT IT, TAKING THEM AWAY AND INTO THE FIGHTER CLASS FOR NOW THOUGH
//
//    //other methods
//
//    public boolean isMapped()
//    {
//        //using isAlive explicitly would mean that
//    }
//
//    /**
//     * Spawn with the data specifiec
//     * @param x
//     * @param y
//     * @param direction
//     * @param hitPoints
//     */
//    public void spawn(int x, int y, float direction, int hitPoints)
//    {
//        this.x = x;
//        this.y = y;
//        this.direction = direction;
//        this.hitPoints = hitPoints;
//    }
//}