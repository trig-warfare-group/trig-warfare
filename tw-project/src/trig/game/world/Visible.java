package trig.game.world;
//NOTE: probably better to have some class with draw methods corresponding to each object or something rather than these imports in lots of places? idk.
import java.awt.*;

/**
 * Entities that are visible and can be drawn on the map.
 * Created by marcos on 8/07/2014.
 */
public interface Visible extends WorldObject
{

    abstract public void render(Graphics2D g);
}
