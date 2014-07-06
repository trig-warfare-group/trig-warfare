/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package trig.game.entity;

import java.awt.Color;

/**
 * Base class for combatants, such as the player, AI.
 * @author marcos
 */
public abstract class Combatant
{
    //constants
    //is there any reason for these to be public?
    //a standard amount by which to turn a combatant by at a time? 1/36th of a circle at a time maybe? experimental value
    protected static final float STANDARD_TURN = (float) (((float)1/18)*Math.PI);
    
    //a standard amount by which to move at a time? not sure yet if will be expressed in px or something, but yeh. experimental value
    protected static final int STANDARD_DISTANCE = 5;
    
    protected int health;
    
    //No need to update name, ID, or color within a single match, so may aswell make them final and publically accessible?
    
    protected String name;  
    //likely to correspond to the Combatant's position in some list?
    public final int id;
    
    //color of the combatant
    //no setter by default, subclasses can make one if needed, most shouldn't though?
    public Color color;
    
    //cartesian coordinates
    protected int x;
    protected int y;
    
    //the direction in which the combatant is facing. Double unneccassary?
    //expressed in radians
    //if memory serves, we will need to use atan2 most often, so the angle will work like atan2 needs it to: 0 is 3 o'clock, must be between [-1pi, 1pi], moves anticlockwise, 12 o'clock is pi/2, 6 o'clock is -pi/2.
    protected float direction;
    
    //movement speed multiplier factor?
    protected float speed;
    
    //turn speed multiplier?
    //Note: possibly not using the agility factor, at least not for turning, we'll see, may be too annoying for mouse users etc.
    protected float agility;
    
    //could also have special attributes like speed or armour in the future.
    
    /*getters and setter*/
    
    public String getName(){
        return name;
    }
    
    //NOTE: not sure that every combatant should be allowed to get a name change yet, leaving it for now?
    
    public int getX()
    {
        return x;
    }
    
    //yes we want to be able to publically setX, so the server can forcedly update the position of enemies, possible teleportation, etc
    public void setX(int x)
    {
        this.x = x;
    }
    
    public int getY()
    {
        return y;
    }
    
    public void setY(int y)
    {
        this.y = y;
    }
    
    public float getDirection()
    {
        return direction;
    }
    
    public void setDirection(float direction)
    {
        this.direction = direction;
    }
    
    public float getSpeed()
    {
        return speed;
    }
    
    public void setSpeed(float speed)
    {
        this.speed = speed;
    }
    
    public float getAgility()
    {
        return agility;
    }
    
    public void setAgility(float agility)
    {
        this.agility = agility;
    }
    
    public Combatant(int health, String name, int  id, Color color, int x, int y, float direction, float speed, float agility)
    {
        this.health = health;
        this.name = name;
        this.id = id;
        this.color = color;
        
        //in the long run there will probably be a list of available spawn points for combatants provided by the map
        //possibly even segregated into specific types
        //so this code is temporary
        //note: is it worth using a float for x and y? probably not.
        this.x = x;
        this.y = y;
        this.direction = direction;

        this.speed = speed;        
        this.agility = agility;
    }
    
    /*
     Some 'helper' functions all of which may not be kept
    */
    
    public boolean isAlive()
    {
        return health > 0;
    }
    //NOTE: WHEN DOING LAGG MOVEMENT IT MIGHT BE USEFUL TO USE THE BASIC MOVE AND TURN FUNCTIONS FOR REMOTE ONES AS WELL, BUT IT MIGHT NOT, WE MIGHT JUST QUE EVENTS INSTEAD? WE'LL HAVE TO THINK ABOUT IT, TAKING THEM AWAY AND INTO THE FIGHTER CLASS FOR NOW THOUGH
}

/*
this is just some draftish notes right now?
**
 * Speed and direction in which combatant will move, possibly for use by the server for anti-lag purposes
 * @author marcos
 *
class Trajectory
{
   
   //Note: using a trajectory instead of just constantly updating current position may be useful for smoothing out lag?
   //there could be a standard rate of deceleration used so that remote clients can have your combatant moving between updates, possibly?
   
   //haven't decided to express angle, but I'm thinking thinking it should be done in a way that most simplifies the proccessing of each screen frame? 
   private float angle;
   
   
   //to be used a multiplier rather than a base px per second etc.
   //float/int not sure?
   private float speed;
   
}
*/