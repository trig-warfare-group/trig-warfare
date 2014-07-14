package trig.game.entity;

import trig.utility.Constants;
import trig.utility.vector.*;

import java.awt.*;
import java.util.Random;

/**
 * (Dummy/test) temporary test implementation of Projectile, MAY CONTAIN UNWANTED HARD-CODED DATA/INEFFICIENCIES, REUSE WITH CAUTION
 * Created by marcos on 8/07/2014.
 */
public final class DummyBullet extends Projectile implements Destructible, Visible
{
    private Random r = new Random();
    private String name; //only for debug output, should probably use a toString() in the future, etc
    private Color color;
    public DummyBullet(int x, int y, float direction) {

        super(/*id,*/ x, y,
                3, //hitSize,
                new PolarVector(7, direction) //velocity = 4
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

    @Override
    protected void move()
    {
        super.move();

        //rough, circle based edge-detection, temporary, handle in collisions later!!!
        if( x + hitSize > (Constants.WORLD_DIM.width - Constants.WORLD_COLLISION_PADDING - 1) ) //it's always -1 at the end, since the largest val is width-1, etc
        {
            //forced-move along the relevant axis to the exact edge for non-collision, or back one further? (exact because we used the > comparison, not >=?
            x = Constants.WORLD_DIM.width - Constants.WORLD_COLLISION_PADDING - Math.round(hitSize) - 1;

            //destroy
        }
        else if(x - hitSize < ( Constants.WORLD_COLLISION_PADDING - 1 ) )
        {
            x = Constants.WORLD_COLLISION_PADDING + Math.round(hitSize) - 1;

            //destroy
        }

        //not else if for x/y, both could occur
        if( y + hitSize > (Constants.WORLD_DIM.height - Constants.WORLD_COLLISION_PADDING - 1) ) //it's always -1 at the end, since the largest val is width-1, etc
        {
            //forced-move along the relevant axis to the exact edge for non-collision, or back one further? (exact because we used the > comparison, not >=?
            y = Constants.WORLD_DIM.height - Constants.WORLD_COLLISION_PADDING - Math.round(hitSize) - 1;

            //destroy
        }
        else if(y - hitSize < ( Constants.WORLD_COLLISION_PADDING - 1 ) )
        {
            y = Constants.WORLD_COLLISION_PADDING + Math.round(hitSize) - 1;

            //destroy
        }
        //not finished
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
