package trig.game.entity;

import trig.utility.Constants;
import trig.utility.SRectangle;

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
    Point b;
    Point c;
    ////////////////////

    public Shape hitbox = new Rectangle(0, 0, 50, 50);
    int u_id;
    Random r = new Random();

    public Combatant(int x, int y, int hitPoints)
    {
        super(x,y);
        b = new Point(x + 25, y);
        c = new Point(x, y + 25);
        this.hitPoints = hitPoints;
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

        b.x += dX;
        c.x += dX;

        b.y += dY;
        c.y += dY;
    }

    /**
     * Place to be localized(moved) to.
     * @param x - x-location
     * @param y - y-location
     */
    public void setLocation(int x, int y)
    {
        c.x = x;
        c.y = y + (c.y - this.y);

        b.x = x + (b.x - this.x);
        b.y = y;

        this.x = x;
        this.y = y;

    }

    @Override
    public void update()
    {
        int x = r.nextInt(51) - r.nextInt(50);
        int y = r.nextInt(51) - r.nextInt(50);

        if(!(this.x + x < 0 || this.x > Constants.WINDOW_DIMENSION.width
                || this.y < 0 || this.y > Constants.WINDOW_DIMENSION.height))
            move(x, y);
        else
            this.setLocation(600, 300);


    }

    @Override
    public void render(Graphics2D g)
    {
        g.drawLine(x, y, b.x, b.y);
        g.drawLine(b.x, b.y, c.x, c.y);
        g.drawLine(c.x, c.y, x, y);

        //g.drawString(Integer.toString(u_id), ((Rectangle)hitbox).x, ((Rectangle)hitbox).y + 60);
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


    @Override
    public SRectangle getCollisionRegion()
    {
        return new SRectangle(this.x, this.y, b.x - this.x, c.y - this.y);
    }

}
