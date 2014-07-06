/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package trig.game.entity;

/**
 * Base class for combatants, such as the player, AI. NOTE: remote combatants need much less functions etc, perhaps Combatant should be a very barebones class, which only holds data, and use some subclass called Actors to hold the functions?
 * @author marcos
 */
public class Combatant
{
    //constants
    //is there any reason for these to be public?
    //a standard amount by which to turn a combatant by at a time? 1/36th of a circle at a time maybe? experimental value
    private static final float STANDARD_TURN = (float) (((float)1/18)*Math.PI);
    
    //a standard amount by which to move at a time? not sure yet if will be expressed in px or something, but yeh. experimental value
    private static final int STANDARD_DISTANCE = 5;
    
    private int life;
    
    //No need to update name or ID within a single math, so may aswell make them final and publically accessible?
    
    public final String name;  
    //likely to correspond to the Combatant's position in some list?
    public final int id;
    
    //cartesian coordinates
    private int x;
    private int y;
    
    //the direction in which the combatant is facing. Double unneccassary?
    //expressed in radians
    //if memory serves, we will need to use atan2 most often, so the angle will work like atan2 needs it to: 0 is 3 o'clock, must be between [-1pi, 1pi], moves anticlockwise, 12 o'clock is pi/2, 6 o'clock is -pi/2.
    private float direction;
    
    //movement speed multiplier factor?
    private float speed;
    
    //turn speed multiplier?
    //Note: possibly not using the agility factor, at least not for turning, we'll see, may be too annoying for mouse users etc.
    private float agility;
    
    //could also have special attributes like speed or armour in the future.
    
    /*getters and setter*/

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
    
    public Combatant(int life, String name,int  id)
    {
        this.life = life;
        this.name = name;
        this.id = id;
        
        //in the long run there will probably be a list of available spawn points for combatants provided by the map
        //possibly even segregated into specific types
        //so this code is temporary
        //note: is it worth using a float for x and y? probably not.
        this.x = (int) Math.random();
        this.y = (int) Math.random();
        this.direction = (float) Math.random();
        
        this.agility = 1;
        this.speed = 1;
    }
    
    /*
     Some 'helper' functions all of which may not be kept
     
    */
    public boolean isAlive()
    {
        return life > 0;
    }
    
    
    /* movement functions */
    /**
     * Rotates the combatant's facing direction.
     * @param delta number of radians to change the direction by
     */
    public void turn(float delta)
    {
        
        float newDirection = direction+delta;
        //normalise the new direction to be between -pi and pi on every update, so the value never gets to infinity (or out of bounds of the float type/NaN, whatever happens in java), which would reek havoc!
        while (newDirection <= -Math.PI){
            newDirection += Math.PI*2;
        }
        while (newDirection > Math.PI){
            newDirection -= Math.PI*2;
        }
        direction = newDirection;
                
        //maybe push a map update? but I think that the local map would just periodically look
        //push data up to the server, etc? or just do it periodically? (in server version ofc)
    }
    
    /**
     * Moves the character along the axis of the combatant's current facing direction
     * Note: we don't have strafing yet, but we could always add a strafe() function, so it's not a big deal.
     */
    public void move(int distance)
    {
        //how will we deal with map edges, collisions, etc? should that be handled here and raise the collision event here, or what?
        
        //I think this math is correct? Google unit circles and stuff if you wanna know more?
        int newX = x + (int) Math.round(distance*Math.cos(direction));
        int newY = y + (int) Math.round(distance*Math.sin(direction));
        
        //TODO: DETERMINE IF THERE IS A COLLISION OR AN EDGE OR SOMETHING, ADJUST POSITION ACCORDINGLY AND RAISE A COLLISION EVENT
        
        //pseudocode;
        if(newX > map.width){
            
            //we may need to determine or store the coords of the edges of the triangle, or just an algorithm to calculate it once we know shape and size etc.
            //this wouldn't be too hard to work out how to do, but still, saving it for later.
            newX = map.width-however much we need to move so that our triangle stays on screen or something? Not just from a straight width or height, what with combatants being trianglular etc.
        }
        this.x = newX;
        this.y = newY;
    }
    
    
    //QUESTION: Should these "standard" methods be put in the Player object instead of the base one maybe? though it could probably be used in combination with while loops etc by AI, so I'm leaning towards no. 
    
    /**
     * Rotates the combatant's facing direction by a standard amount, multiplied by agility 
     * @param clockwise boolean used to indicate clockwise-ness of turn?
     */
    public void turnStandard(boolean clockwise)
    { //to be used on keyboard etc events for the player, etc?
        //clockwise is - for atan2
        turn( ( clockwise ? -STANDARD_TURN : STANDARD_TURN ) * agility );
        
    }
    
    /**
     * Moves the character a by a standard amount, multiplied by speed
     * @param forward whether the movement is forward or backward
     */
    public void moveStandard(boolean forward)
    {
        move((int) (( forward ? STANDARD_DISTANCE : -STANDARD_DISTANCE ) * speed));
    }
}

/**
 * Speed and direction in which combatant will move, possibly for use by the server for anti-lag purposes
 * @author marcos
 */
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