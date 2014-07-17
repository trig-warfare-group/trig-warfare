package trig.game.entity;

import trig.game.entity.interfaces.Entity;
import trig.utility.DummyMethods;
import trig.utility.math.vector.CartesianForm;

import java.awt.*;
import java.awt.geom.GeneralPath;

/**
 * Base class for in game entities, should we be directly implimenting some of the aspects of entity here, or delete this class and do it repeatedly in others? IMO: here, less code
 * Created by marcos on 10/07/14.
 */
public abstract class BasicEntity implements Entity
{
    /**
     * makes a shape from a series of points, dummy code for now, but could be used in the future
     * @param points a 2d array of x,y coordinates.
     * @return a Shape object, to be drawn
     */
    public static Shape makeDrawableShape(CartesianForm[] points){ //todo: make possible to add multiple shapes maybe? seems a simple task to do so.
        final GeneralPath path = new GeneralPath();
        path.moveTo(points[0].x, points[0].y);
        for(int i = 1; i < points.length; i++)
        {
            path.lineTo(points[i].x, points[i].y);
        }
        path.closePath();
        return path;
    }

    protected final long id;
    protected int x;
    protected int y;

    //size of the (most basic/draft) hitbox around the entity, for collisions, etc
    protected int hitSize; //TODO: THIS SHOULD BE AN INT UNLESS COORDS BECOME FLOATS TOO!

    public long getId()
    {
        return id;
    }

    public int getHitSize()
    {
        return hitSize;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    BasicEntity(/*int id,*/ int x, int y, int hitSize){ //TODO: fix where id comes from, etc, probably needs to be able to recycle, or refresh periodically?
        this.id = DummyMethods.DummyVars.getNextEntityId();
        this.x = x;
        this.y = y;
        this.hitSize = hitSize;
    }
}
