package trig.game.entity;

/**
 * Standard bullets fired by players etc?
 * Created by marcos on 8/07/2014.
 */
public class StBullet implements Entity, Movable, Visible, Destructible{
    @Override
    public boolean isTangible()
    {
        return false;
    }
    public void onCollision()
    {
        destroy();
    }

    //only exist spawned, right?
}
