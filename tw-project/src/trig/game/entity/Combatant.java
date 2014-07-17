package trig.game.entity;

import trig.utility.Constants;
import java.util.Random;
import java.awt.*;

/**
 * A Combatant that shall be used as a basic NPC in the game.
 * Created by brody on 17/07/14.
 */
public class Combatant extends BasicCombatant
{
    /* This hitbox should be upgraded for collision-detection */
    public Shape hitbox = new Rectangle(23, 253, 53, 53);
    int u_id;
    Random r = new Random();

    public Combatant(int x, int y, int hitPoints)
    {
        super(x, y, hitPoints);
        this.x = x;
        this.y = y;
        u_id = r.nextInt();
    }


    @Override
    public void move(int x, int y)
    {

    }

    @Override
    public void update()
    {
        y = r.nextInt(Constants.WINDOW_DIMENSION.height);
        x = r.nextInt(Constants.WINDOW_DIMENSION.width);

        if(x < Constants.WINDOW_DIMENSION.width && x > 0
            && y < Constants.WINDOW_DIMENSION.height && y > 0)
        {
            ((Rectangle) hitbox).x = x;
            ((Rectangle) hitbox).y = y;
        }
    }

    @Override
    public void render(Graphics2D g)
    {
        Rectangle r = (Rectangle) hitbox;
        g.draw(hitbox);
        g.drawString(Integer.toString(u_id), r.x, r.y + 10);
    }
}
