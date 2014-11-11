package trig.game.world;

import trig.utility.math.vector.FloatCartesian;

/**
 * Created by marcos on 10/11/2014.
 */
public class SCannonBETA extends Weapon
{

    SCannonBETA(FloatCartesian location, FloatCartesian unitTrajectory)
    {
        super(location, unitTrajectory);
    }

    public void move(FloatCartesian shift)
    {
        location.add(shift);
    }

    //no visuals, just a firing point.
    @Override
    public void rotate(float theta)
    {
        location.rotate(theta);
        unitTrajectory.rotate(theta);
    }

    @Override
    public void rotateAbout(float theta, FloatCartesian center)
    {
        move(FloatCartesian.mirror(center));
        rotate(theta);
        move(center);
    }

    public void setUnitTrajectory(FloatCartesian unitTrajectory)
    {
        super.setUnitTrajectory(unitTrajectory);
    }

    @Override
    public void setLocation(FloatCartesian location)
    {
        this.location = location.clone();
    }

}
