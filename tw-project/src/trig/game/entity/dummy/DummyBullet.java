package trig.game.entity.dummy;

import trig.game.engine.GameEngine;
import trig.game.entity.Projectile;
import trig.game.entity.interfaces.Visible;
import trig.utility.Constants;
import trig.utility.math.vector.*;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.Random;

/**
 * (Dummy/test) temporary test implementation of Projectile, MAY CONTAIN UNWANTED HARD-CODED DATA/INEFFICIENCIES, REUSE WITH CAUTION
 * Created by marcos on 8/07/2014.
 */
public final class DummyBullet extends Projectile implements Visible
{
    protected Shape shape = new RoundRectangle2D.Float(hitSize, hitSize, hitSize, hitSize, 0, 0);

    @Override
    public Shape getShape(){
        return shape;
    }

    @Override
    public float getDirection()
    {
        /*
            not all entities will need or want to turn when drawn,
            a round bullet is a good example
         */
        return 0;
    }

    @Override
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
                new PolarForm(7, direction) //velocity
        );
        name = "DBullet_" + id;
        color = new Color(
                r.nextInt((250 - 75) + 1) + 75,
                r.nextInt((250 - 75) + 1) + 75,
                r.nextInt((250 - 75) + 1) + 75
        ); //random
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
                        || (x - hitSize < (Constants.WORLD_COLLISION_PADDING - 1))
                        || (y + hitSize > (Constants.WORLD_DIM.height - Constants.WORLD_COLLISION_PADDING - 1))
                        || (y - hitSize < (Constants.WORLD_COLLISION_PADDING - 1))
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

//    @Override
//    public void draw(Graphics2D g)
//    {
//        g.setColor(color);
//
//        int approxHitDiameter = Math.round(hitSize*2);
//        g.fillOval((int) (x-hitSize), (int) (y-hitSize), approxHitDiameter, approxHitDiameter); //inefficient re-do of math, only a demo
//        //get the color right
//        g.setColor(color);
//
//        //draw the name of the triangle above it
//        float textBaseline = (float) (y-hitSize*1.2);
//
//        /*
//        g.drawString(x+", "+y, Math.round(x-(hitSize/1.2)), (float) (textBaseline));
//        g.drawString(name, x-(hitSize), (float) (textBaseline-15));
//        */
//        g.setColor(Color.RED);
//        g.drawOval((int) (x-hitSize), (int) (y-hitSize), approxHitDiameter, approxHitDiameter); //inefficient re-do of math, only a demo
//    }

    //TODO: WRITE DESTRUCTION LISTENER INTO ENGINE, ETC?
}
