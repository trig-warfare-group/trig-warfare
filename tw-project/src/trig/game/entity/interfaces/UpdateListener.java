package trig.game.entity.interfaces;

/**
 * For classes that take action periodically on game ticks or frames or something? Such as maybe projectiles/bullets or AI.
 * Sort of a listener-like class
 * Should probably be renamed, possibly to TickAI?
 * May not keep, idk, seemed like a reasonable way to go about it though.
 * @author marcos
 */
public interface UpdateListener
{
    abstract public void update(GameData );
}
