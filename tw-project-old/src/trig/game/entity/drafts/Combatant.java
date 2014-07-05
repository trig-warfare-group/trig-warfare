/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package trig.entity.drafts;

/**
 * Base class for combatants, such as the player, AI or remote combatants
 * @author marcos
 */
public class Combatant {
    private int life;
    private final String name;  
    //likely to correspond to the Combatant's position in some list?
    private final int id;
    
    //cartesian coordinates
    private int x;
    private int y;
    
    //the direction in which the combatant is facing. Double unneccassary?
    //expressed in radians
    private float direction;
    
    //could also have special attributes like speed or armour in the future.
     
    public Combatant(int life, String name,int  id){
        this.life = life;
        this.name = name;
        this.id = id;
        
        //in the long run there will probably be a list of available spawn points for combatants provided by the map
        //possibly even segregated into specific types
        //so this code is temporary
        this.x = (int) Math.random();
        this.y = (int) Math.random();
        this.direction = (float) Math.random();
    }
    
}

/**
 * Speed and direction in which combatant will move
 * @author marcos
 */
class Trajectory{
   
   //Note: using a trajectory instead of just constantly updating current position may be useful for smoothing out lag?
   //there could be a standard rate of deceleration used so that remote clients can have your combatant moving between updates, possibly?
   
   //haven't decided to express angle, but I'm thinking thinking it should be done in a way that most simplifies the proccessing of each screen frame? 
   private float angle;
   
   
   //to be used a multiplier rather than a base px per second etc.
   //float/int not sure?
   private float speed;
   
}