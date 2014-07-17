package trig.game.entity;

/**
 * Denotes a living-entity that requires constant attention via an update.
 * Although an entity may not always need to be updated if it is living,
 * it is quite a common trait.
 * Created by marcos on 11/07/2014.
 * @author brody
 */
public interface Living
{
    /**
     * An update method that will be called each game-tick.
     */
    public abstract void update();
}
