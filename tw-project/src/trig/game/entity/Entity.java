package trig.game.entity;

/**
 * Interface class for all entities, where entities are anything that can interact with other entities in the game.
 * @author marcos
 * Created by marcos on 8/07/2014.
 */
public interface Entity {
    public final int id = 0;

    //safety check to determine whether or not we can look at x or y
    public boolean isMapped();

    //cartesian coordinates
    int x = 0;
    int y = 0;
}
