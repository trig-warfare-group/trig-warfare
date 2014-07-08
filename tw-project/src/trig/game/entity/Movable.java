package trig.game.entity;

/**
 * Indicates that the entity can move.
 *
 * Created by marcos on 8/07/2014.
 */
public interface Movable
{
    //note actually sure what /data/ to put here anymore, it might just be a flag?

    /*
    //movement speed multiplier factor? 1x == no multiplier effect
    //NOTE: This speed is NOT a velocity, we can have a velocity, but that is not the intention of the speed as defined by combatant?
    float speed = 1;
    */

    //Bullets would have a velocity and direction, but not all entities will move in simple straight lines, and those that don't should definitely use their own internal functions for updating their location.
    //In addition to this, it may be easier to impliment collision events by having most objects resposible for their own movement?

    //QUESTION: WOULD IT ACTUALLY BE EASIER TO IMPLIMENT COLLISIONS THAT WAY OR NOT?

    //NOTE: decided that it's unlikely that the Interface for movable objects would need direction, as it's unlikely that much else other than the actual entity itself will find the direction useful!

}
