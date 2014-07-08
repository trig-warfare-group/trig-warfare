package trig.game.entity;

/**
 * Objects that can be removed from the game entirely, (does NOT include the player, though may include remote players)
 * @author marcos
 */
public interface Destructible {
    public void destroy(); //could be used when a player leaves a game for example?
}
