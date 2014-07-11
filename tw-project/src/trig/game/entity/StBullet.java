package trig.game.entity;

import trig.utility.vector.*;

import java.awt.*;

/**
 * Standard bullets fired by players etc?
 * Created by marcos on 8/07/2014.
 */
public class StBullet extends Projectile implements Destructible, Visible
{
    protected static final int ST_VEL = 1;

    public StBullet(int id, int x, int y, float hitRadius, float direction) {
        super(id, x, y, hitRadius, new PolarVector(direction, ST_VEL));
    }

    //note: one reason /all/ projectiles might be destructible is that we'd probably want to limit the number of them that exist for lagg-stopping purposes, we might instead just do that for StBullet or something though, idk
    public boolean isMapped()
    {
        return true;
    }


    @Override
    public void draw(Graphics2D g) {
        //not finished
    }
}
