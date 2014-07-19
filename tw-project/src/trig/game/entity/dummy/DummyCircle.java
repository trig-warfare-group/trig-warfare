package trig.game.entity.dummy;

import trig.game.entity.GenericMoving;
import trig.game.entity.interfaces.Visible;
import trig.utility.Constants;
import trig.utility.math.vector.PolarForm;
import trig.utility.math.vector.Vector;

import java.awt.*;

/**
 * Created by marcos on 19/07/2014.
 */
public class DummyCircle extends GenericMoving implements Visible
{
    int x;
    int y;

    public DummyCircle(int x, int y, Vector velocity)
    {
        super(x, y, 10, velocity);
    }

    @Override
    public void move(){
        super.move();

        boolean hit = false;

        if( x + hitSize > (Constants.WORLD_DIM.width - Constants.WORLD_COLLISION_PADDING - 1) )
        {
            x = Constants.WORLD_DIM.width - Constants.WORLD_COLLISION_PADDING - Math.round(hitSize) - 1;
            hit = true;
        }
        else if(x - hitSize < ( Constants.WORLD_COLLISION_PADDING - 1 ) )
        {
            x = Constants.WORLD_COLLISION_PADDING + Math.round(hitSize) - 1;
            hit = true;
        }

        if( y + hitSize > (Constants.WORLD_DIM.height - Constants.WORLD_COLLISION_PADDING - 1) )
        {
            y = Constants.WORLD_DIM.height - Constants.WORLD_COLLISION_PADDING - Math.round(hitSize) - 1;
            hit = true;
        }
        else if(y - hitSize < ( Constants.WORLD_COLLISION_PADDING - 1 ) )
        {
            y = Constants.WORLD_COLLISION_PADDING + Math.round(hitSize) - 1;
            hit = true;
        }

        if(hit)
        {
            PolarForm polarVel = velocity.inPolar();
            velocity = new PolarForm(polarVel.radius, Math.round(polarVel.angle+Math.PI));
        }
    }

    @Override
    public boolean isMapped()
    {
        return true;
    }

    @Override
    public void render(Graphics2D graphics2D)
    {
        graphics2D.setColor(Color.GREEN);
        int halfSize = (hitSize/2);
        graphics2D.drawOval(x - halfSize, y - halfSize, hitSize, hitSize);
    }

    @Override
    public boolean isVisible()
    {
        return true;
    }
}
