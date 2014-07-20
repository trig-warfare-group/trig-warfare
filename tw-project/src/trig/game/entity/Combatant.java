package trig.game.entity;

import trig.utility.Constants;
import trig.utility.vector.CartesianForm;
import trig.utility.vector.PolarForm;
import trig.utility.vector.Vector;

import java.util.Random;
import java.awt.*;

/**
 * A Combatant that shall be used as a basic NPC in the game.
 * Created by brody on 17/07/14.
 */
public class Combatant extends SEntity
{
    /* This hitbox should be upgraded for collision-detection */
    protected int hitPoints;
    protected boolean alive;
    ////////////////////
    public Shape hitbox = new Rectangle(0, 0, 50, 50);
    int u_id;
    Random r = new Random();
    Vector velocity;

    public Combatant(int x, int y, int hitPoints)
    {
        super(x,y);
        this.hitPoints = hitPoints;
        this.velocity = new PolarForm(5, (float) ((r.nextFloat()*2 - 1)*Math.PI));
        u_id = r.nextInt();
    }

    /**
     *  Increases the current position, by the parameters.
     * @param dX -- x-location to move to.
     * @param dY -- y-location to move to.
     */
    public void move(int dX, int dY)
    {
        this.x += dX;
        this.y += dY;
    }

    /**
     * Place to be localized(moved) to.
     * @param x - x-location
     * @param y - y-location
     */
    public void setLocation(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public void move()
    {
        CartesianForm cartesianVelocity = velocity.inCartesian();
        move(x+cartesianVelocity.getX(), y+cartesianVelocity.getY());
    }

    @Override
    public void update()
    {
        int x = r.nextInt(Constants.WINDOW_DIMENSION.width);
        int y = r.nextInt(Constants.WINDOW_DIMENSION.height);

        if(x < Constants.WINDOW_DIMENSION.width && x > 0
            && y < Constants.WINDOW_DIMENSION.height && y > 0)
        {
            ((Rectangle)hitbox).x = x;
            ((Rectangle)hitbox).y = y;
        }
    }

    @Override
    public void render(Graphics2D g)
    {
        g.draw(hitbox);
        g.drawString(Integer.toString(u_id), ((Rectangle)hitbox).x, ((Rectangle)hitbox).y + 60);
    }

    public boolean isAlive()
    {
        return alive;
    }

    public void setAlive(boolean alive)
    {
        this.alive = alive;
    }

    public int getHitPoints()
    {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints)
    {
        this.hitPoints = hitPoints;
    }

}
