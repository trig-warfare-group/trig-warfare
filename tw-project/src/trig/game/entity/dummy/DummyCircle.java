package trig.game.entity.dummy;

import trig.game.engine.GameEngine;
import trig.game.entity.GenericMoving;
import trig.game.entity.interfaces.UpdateListener;
import trig.game.entity.interfaces.Visible;
import trig.utility.Constants;
import trig.utility.math.vector.Cartesian;
import trig.utility.math.vector.Polar;

import java.awt.*;

/**
 * Created by marcos on 19/07/2014.
 */
public class DummyCircle extends GenericMoving implements Visible, UpdateListener
{
    /*
    tragectoryA = polarVel;
    tragejectoryB = Math.round(polarVel.angle+Math.PI)
    */
    public DummyCircle(int x, int y, Cartesian velocity)
    {
        super(x, y, 100, velocity);
    }

    @Override
    public void move(){
        super.move();

        if (
            (x + hitSize > (Constants.WORLD_DIM.width - Constants.WORLD_COLLISION_PADDING - 1))
            || (x < (Constants.WORLD_COLLISION_PADDING - 1))
            || (y + hitSize > (Constants.WORLD_DIM.height - Constants.WORLD_COLLISION_PADDING - 1))
            || (y < (Constants.WORLD_COLLISION_PADDING - 1))
        )
        {
            Polar polarVel = velocity.inPolar();
            velocity = new Polar(polarVel.radius,  polarVel.angle+ (float)Math.PI);
        }
    }

    @Override
    public boolean isMapped()
    {
        return true;
    }

    @Override
    public void render(Graphics2D g)
    {
        g.setColor(Color.GREEN);
        int halfSize = Math.round(hitSize/(float) 2);
        g.drawOval(x, y, hitSize, hitSize);

        g.drawString("" + x + ", " + y, x+halfSize/2, y-10);


    }

    @Override
    public boolean isVisible()
    {
        return true;
    }

    @Override
    public void update(GameEngine engine)
    {
        move();
    }
}
