package trig.game.entity;

/**
 * Currently just a test class, for thinking about what immovable entities might need,
 * Created by marcos on 8/07/2014.
 */
public class extends BasicEntity implements Destructible, Visible {
    int x;
    int y;



    @Override
    public boolean isTangible()
    {
        return true;
    }
    public boolean isMapped()
    {
        return true;
    }

    @Override
    public void draw() {

    }
}
