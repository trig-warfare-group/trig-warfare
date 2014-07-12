package trig.game.entity;

/**
 * Interface for living entities, i.e. those ideally with more than one life, those that can die, those that can respawn, etc. Maybe not /all AI/ (idk)
 * Not all living entities can move, etc?
 * Created by marcos on 11/07/2014.
 */
public interface Living {

    /**
     * Gets the HP held by the player
     * @return amount of HP the entity has, possibility influenced by stats in the future
     */
    abstract public int getHp();
    /**
     * Deals a given amount of damage to the entity, possibility influenced by stats in the future
     */
    abstract public void damage(int points);

    /**
     * Heals a given amount of HP for the entity, possibility influenced by stats in the future
     * @param points
     */
    abstract public void heal(int points);

    abstract public boolean isAlive();
    abstract public void kill(); //should have a die() for responding to kill/death

    /**
     * (Default): Spawns the entity at a random location on the map (not touching other entities, if possible).
     * Relevant properties:
     *  Direction: Random, if applicable.
     *  Velocity: Stationary, if possible.
     * Note: may remove this from the interface
     */
    abstract void spawn();
}
