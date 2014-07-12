package trig.game.entity;

import trig.utility.Physics;
import trig.utility.Variables;

/**
 * Base class for in game entities, should we be directly implimenting some of the aspects of entity here, or delete this class and do it repeatedly in others? IMO: here, less code
 * Created by marcos on 10/07/14.
 */
public abstract class BasicEntity implements Entity
{
    protected final long id;
    protected int x;
    protected int y;

    //size of the hitbox around the entity, for collisions, etc
    protected float hitRadius; //TODO: THIS SHOULD BE AN INT UNLESS COORDS BECOME FLOATS TOO!

    public long getId()
    {
        return id;
    }

    public float getHitRadius()
    {
        return hitRadius;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    BasicEntity(/*int id,*/ int x, int y, float hitRadius){ //TODO: fix where id comes from, etc.
        this.id = Physics.DummyVars.getNextEntityId();
        this.x = x;
        this.y = y;
        this.hitRadius = hitRadius;
    }
}
