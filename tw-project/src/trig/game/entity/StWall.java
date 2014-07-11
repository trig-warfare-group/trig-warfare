package trig.game.entity;

import java.awt.*;

/**
 * Currently just a test class, for thinking about what immovable entities might need,
 * Created by marcos on 8/07/2014.
 */
public class StWall extends BasicEntity implements Destructible, Visible {
    protected float direction;
    StWall(int id, int x, int y, float direction, float hitRadius) {
        super(id, x, y, hitRadius);
        this.direction = direction;
    }

    //some standard size data?
    public boolean isTangible()
    {
        return true;
    }
    public boolean isMapped()
    {
        return true;
    }

    public void draw(Graphics2D g) {

    }
}
