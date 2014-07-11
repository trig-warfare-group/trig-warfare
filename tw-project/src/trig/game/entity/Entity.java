package trig.game.entity;

/**
 * Interface class for all entities, where entities are anything that can interact with other entities in the game.
 * @author marcos
 * Created by marcos on 8/07/2014.
 */
public interface Entity
{
    final int id = 0;

    //IDEA: TANGIBILITY CAN BE CONTROLLED BY HITBOX SIZE TO A DEGREE, 0 = NOT TANGLIBLE

    abstract public float getHitRadius();

    //safety check to determine whether or not we can look at the x or y of the object, also, objects that aren't mapped shouldn't be drawn, or collided with
    abstract public boolean isMapped();

    //QUESTION: Could be is-mapped be unneccasary since we will have a draw()-event like listener? Or would there be reasons other than visibility or collisions, where external objects may look at x or y?




    /*
    these would be made Public by the interface!, interfaces only define things that can be interfaced?
    //cartesian coordinates
    int x = 0;
    int y = 0;
    */
    abstract public int getX();
    abstract public int getY();

    /*

    //not sure how to describe this yet, may even remove it from here, but for example, spawning a combatant puts them on the map?
    public void spawn(); //NOTE: TECHNICALLY, NOT ALL ENTITIES WOULD HAVE A SPAWN METHOD WITH NO PARAMS,

    */

    //note: onCollision is only triggered if tangible, as such not all entities neccessarily need a collision event, also we know of ones that won't: indesctructable walls. We'll need to put it somewhere else I think.

    /*
    //each entity should handle there own responseto collision depending on the type of the other entity, and the game engine will trigger the onCollision for each entity in a pair?

    //As such, entities aren't really Collision-listeners of their own, they may need to be thread-safe still, we should look into that.

    onCollision(CollisionEvent e); //there is a need for better naming here

    */
}
