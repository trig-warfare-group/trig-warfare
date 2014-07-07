/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package trig.game.entity;

import java.awt.Color;

/**
 * Baseish class for Player or AI Combatants
 * @author marcos
 */
public abstract class Fighter extends Combatant{
    public Fighter(int health, String name, int  id){
        
        //the random values etc are temp for now;
        
        super
        (
            health,
            name,
            id,
            new Color((int) Math.random(), (int) Math.random(), (int) Math.random()), //color
            (int) Math.random(), //x
            (int) Math.random(), //y
            (float) Math.random(), //direction
            1, //speed
            1 //agility
        );
    }
    
    /* movement functions for active combatants (e.g. player or AI, but not remote ones */
    /**
     * Rotates the combatant's facing direction.
     * @param delta number of radians to change the direction by
     */
    public void turn(float delta)
    {
        
        float newDirection = getDirection()+delta;
        //normalise the new direction to be between -pi and pi on every update, so the value never gets to infinity (or out of bounds of the float type/NaN, whatever happens in java), which would reek havoc!
        while (newDirection <= -Math.PI)
        {
            newDirection += Math.PI*2;
        }
        
        while (newDirection > Math.PI)
        {
            newDirection -= Math.PI*2;
        }
        
        direction = newDirection;
                
        //TODO: maybe push a map update? but I think that the local map would just periodically perform this..
        //TODO: push data up to the server, etc? or just do it periodically? (in server version ofc)
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
        /*
        if(newX >= map.width-1)
        {
            
            //we may need to determine or store the coords of the edges of the triangle, or just an algorithm to calculate it once we know shape and size etc.
            //this wouldn't be too hard to work out how to do, but still, saving it for later.
            newX = map.width-1 - however much we need to move so that our triangle stays on screen or something? Not just from a straight width or height, what with combatants being trianglular etc.
        }
        
        if(newY >= map.height-1)
        {

           //we may need to determine or store the coords of the edges of the triangle, or just an algorithm to calculate it once we know shape and size etc.
           //this wouldn't be too hard to work out how to do, but still, saving it for later.
           newX = map.height-1 - however much we need to move so that our triangle stays on screen or something? Not just from a straight width or height, what with combatants being trianglular etc.
        }
        */
        this.x = newX;
        this.y = newY;
        
         //TODO: maybe push a map update? but I think that the local map would just periodically perform this..
        //TODO: push data up to the server, etc? or just do it periodically? (in server version ofc)
    }
    
    
    /*standard-increment movement*/
    
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
