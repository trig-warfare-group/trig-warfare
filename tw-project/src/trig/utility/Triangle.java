package trig.utility;

import java.awt.*;

/**
 * Created by brody on 21/07/14.
 */
public class Triangle
{
    public Point a;
    public Point b;
    public Point c;

    public Triangle(Point a, Point b, Point c)
    {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public void draw(Graphics2D g)
    {
        g.drawLine(a.x, a.y, b.x, b.y);
        g.drawLine(b.x, b.y, c.x, c.y);
        g.drawLine(c.x, c.y, a.x, a.y);
    }

    /**
     * Increase the points by an amount.
     */
    public void move(int x, int y)
    {
        a.x += x;
        b.x += x;
        c.x += x;

        a.y += y;
        b.y += y;
        c.y += y;
    }

    /**
     * Translates the triangle to the new location, specified as the parameters
     * @param x - x-location to translate to.
     * @param y - y-location to translate to.
     */
    public void setLocation(int x, int y)
    {
        c.x = x;
        c.y = y + (c.y - a.y);

        b.x = x + (b.x - a.x);
        b.y = y;

        a.x = x;
        a.y = y;
    }


}
