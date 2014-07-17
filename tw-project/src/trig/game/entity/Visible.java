package trig.game.entity;
//NOTE: probably better to have some class with draw methods corresponding to each object or something rather than these imports in lots of places? idk.
import java.awt.*;
import java.awt.geom.GeneralPath;

/**
 * A Declaration for something to be Visible, is to be drawn.
 * Created by marcos on 8/07/2014.
 * @author brody
 */
public interface Visible
{
    public abstract void render(Graphics2D g);
}
