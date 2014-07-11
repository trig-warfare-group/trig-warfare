package trig.game.entity;

/**
 * Base class for in game entities, should we be directly implimenting some of the aspects of entity here, or delete this class and do it repeatedly in others? IMO: here, less code
 * Created by marcos on 10/07/14.
 */
public abstract class BasicEntity implements Entity
{
    public final int id;
    protected int x;
    protected int y;

    //size of the hitbox around the entity, for collisions, etc
    protected float hitRadius;

    @Override
    public float getHitRadius()
    {
        return hitRadius;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    BasicEntity(int id, int x, int y, float hitRadius){
        this.id = id;
        this.x = x;
        this.y = y;
        this.hitRadius = hitRadius;
    }
}
