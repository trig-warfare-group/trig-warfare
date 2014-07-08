package trig.game.entity;

/**
 * Created by marcos on 8/07/2014.
 */
public interface Solid
{

    /**
     * determines if spawning with the specified stuff can be done without a collision occuring
     * @return if the spawn would be possible
     */
    public boolean canSpawnAt(int x, int y);
}
