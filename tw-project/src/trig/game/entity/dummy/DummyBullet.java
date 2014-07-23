package trig.game.entity.dummy;

import trig.game.engine.GameEngine;
import trig.game.entity.Projectile;
import trig.game.entity.interfaces.Visible;
import trig.utility.Constants;
import trig.utility.geometry.ColoredPath;
import trig.utility.math.vector.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Random;

/**
 * (Dummy/test) temporary test implementation of Projectile, MAY CONTAIN UNWANTED HARD-CODED DATA/INEFFICIENCIES, REUSE WITH CAUTION
 * Created by marcos on 8/07/2014.
 */
public final class DummyBullet extends Projectile implements Visible
{
    protected ColoredPath<Cartesian> renderPath;

    protected void makePath() //most java.awt functions use minimum floating-point accuracy, it seems?
    {
        //absolute of the angle to rotate by against point A, to get points B and C
        //equilateral and isosceles triangle can be produced using +- this one angle, I think..
        float rotationBase = (float)1/2* (float) Math.PI;
        float rotationAngle =  ( ( (float) 5 / 7 ) * (float) Math.PI);
        renderPath = new ColoredPath<Cartesian>(color);

        float halfSize = (float)hitSize/2;
        Cartesian vA = new Polar(halfSize, rotationBase);
        Cartesian vB = new Polar(halfSize, (rotationBase + rotationAngle));
        Cartesian vC = new Polar(halfSize, (rotationBase - rotationAngle));

        renderPath.add(vA);
        renderPath.add(vB);
        renderPath.add(vC);
        //connect the last to the first
        renderPath.add(vA);

        //translate it to the correct position;
        renderPath.translate(halfSize, halfSize);
    }


    public Color getColor()
    {
        return color;
    }

    @Override
    public boolean isVisible()
    {
        return true;
    }

    public void destroy(GameEngine engine){
        //todo: spawn a harmless bullet destruction animation or something, maybe? Some may spawn cluster frags or something though lol
        engine.removeEntity(this);
    }

    private Random r = new Random();
    private String name; //only for debug output, should probably use a toString() in the future, etc
    private Color color;
    public DummyBullet(int x, int y, float direction) {

        super(/*id,*/ x, y,
                3, //hitSize,
                new Polar(7, direction) //velocity
        );
        name = "DBullet_" + id;
        color = new Color(
                r.nextInt((250 - 75) + 1) + 75,
                r.nextInt((250 - 75) + 1) + 75,
                r.nextInt((250 - 75) + 1) + 75
        ); //random
        makePath();
    }

    //note: one reason /all/ projectiles might be destructible is that we'd probably want to limit the number of them that exist for lagg-stopping purposes, we might instead just do that for StBullet or something though, idk
    public boolean isMapped()
    {
        return true;
    }

    protected void move(GameEngine engine)
    {
        super.move(); //todo: figure out a way that the engine can do the move and still allow the bullet to get destroyed, Apparently without collision events OR just leaving the move up to the entity???

        //if past allowed edge
        if (
                (x + hitSize > (Constants.WORLD_DIM.width - Constants.WORLD_COLLISION_PADDING - 1))
                || (x < (Constants.WORLD_COLLISION_PADDING - 1))
                || (y + hitSize > (Constants.WORLD_DIM.height - Constants.WORLD_COLLISION_PADDING - 1))
                || (y < (Constants.WORLD_COLLISION_PADDING - 1))
        ) //it's always -1 at the end, since the largest val is width-1, etc
        {
            destroy(engine);
        }
    }
    @Override
    public void update(GameEngine engine)
    {
        move(engine);
    }

    //TODO: WRITE DESTRUCTION LISTENER INTO ENGINE, ETC?

    @Override
    public void render(Graphics2D g)
    {
        AffineTransform renderTransform = new AffineTransform();
        renderTransform.translate(x, y);
        renderTransform.rotate(velocity.inPolar().angle);
        renderPath.render(g, renderTransform);
    }
}
