package trig.game.entity;

/**
 * A BasicEntity definition from where most Entities will descend.
 * Created by marcos on 10/07/14
 * @author brody .
 */
public abstract class SEntity implements Entity, AStatic, Visible, Collidable
{
    protected int x;
    protected int y;

    public SEntity(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX()
    {
        return x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
    }

}
