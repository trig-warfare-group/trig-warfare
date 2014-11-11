package trig.game.world;

import javafx.scene.transform.Rotate;
import trig.utility.geometry.Polygon;
import trig.utility.geometry.Renderable;
import trig.utility.geometry.RenderableList;
import trig.utility.math.vector.FloatCartesian;

import java.awt.*;

/**
 * Created by marcos on 10/11/2014.
 */
abstract public class StandardWorldObject implements WorldObject, Movable, Collidable, Harmful, Visible
{
    protected FloatCartesian location;
    protected Polygon hitbox;

    private RenderableList<Renderable> renderingComponents;

    private int dmg;

    StandardWorldObject(FloatCartesian location)
    {
        this.location = location.clone();
    }

    @Override
    public int getDmg()
    {
        return dmg;
    }

    protected void setDmg(int dmg)
    {
        this.dmg = dmg;
    }

    @Override
    public void render(Graphics2D g)
    {
        renderingComponents.render(g);
    }

    @Override
    public FloatCartesian getLocation()
    {
        return location.clone();
    }

    @Override
    public boolean isTrash()
    {
        return false;
    }

    public void move(FloatCartesian shift)
    {
        location.add(shift);
    }

    //by standard, hook into move, so that any components get moved in the same standard way.
    @Override
    public void setLocation(FloatCartesian location)
    {
        move(FloatCartesian.difference(location, this.location));
    }


}
