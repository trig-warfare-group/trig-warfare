package trig.game.entity;

/**
 * Indicates that the entity can move.
 *
 * Created by marcos on 8/07/2014.
 */
public interface Movable //may not stay an interface, might though
{
    //note actually sure what /data/ to put here anymore, it might just be a flag?

    /*
    not all movable objects need a speed /multiplier/!

    //movement speed multiplier factor? 1x == no multiplier effect
    //NOTE: This speed is NOT a velocity, we can have a velocity, but that is not the intention of the speed as defined by combatant?
    float speed = 1;
    */

    //Bullets would have a velocity and direction, but not all entities will move in simple straight lines, and those that don't should definitely use their own internal functions for updating their location.
    //In addition to this, it may be easier to implement collision events by having most objects responsible for their own movement?

    //QUESTION: WOULD IT ACTUALLY BE EASIER TO IMPLEMENT COLLISIONS THAT WAY OR NOT?

    //FURTHERMORE HOW SHOULD WE IMPLEMENT COLLISIONS? SHOULD THE GAME ENGINE DO IT OR ONLY MOVABLE ENTITIES?

    //NOTE: decided that it's unlikely that the Interface for movable objects would need direction, as it's unlikely that much else other than the actual entity itself will find the direction useful!

    //all movable entities have a collision event or handler? How should we construct this? //research

    /*
        if we want all entities to have a velocity, it implies that the server would handle latency by automatically updating the position as per current velocity,
        Which isn't a bad idea,
        but over what time period is velocity measured, etc?
     */
    //

    //since each object has their own draw method, which will draw the lines and stuff, do we actually need a direction for them all?

    int setX();
    int setY();

    //movable needs some superclass, or other abstract class above it.
}
