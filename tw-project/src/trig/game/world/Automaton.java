package trig.game.world;

/**
 * For classes that take action periodically on game ticks or frames or something?
 * @author marcos
 */
public interface Automaton extends WorldObject
{
    abstract public void update();
}
