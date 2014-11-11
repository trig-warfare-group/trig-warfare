package trig.game.world;

import trig.utility.math.vector.FloatCartesian;

/**
 * stores both the weapon position and the unit trajectory of the projectile the weapon emits - these should be used for bullet spawning. (unit trajectory is just rotation factors by which to multiply the base speed of the projectile). TODO extensibility is not fully complete with this but tbh since my vector needs expansion and the classes using it 'extensibly' should probably be revamped, not going to bother for now. Really overloading methodS TO TAKE BOTH primitives and vectors was probably counterproductive and hurt extensibility as it ruined encapsulation.
 */
public abstract class Weapon
{
    protected FloatCartesian location;
    protected FloatCartesian unitTrajectory;
    Weapon(FloatCartesian location, FloatCartesian unitTrajectory){
        this.location = location.clone();
        this.unitTrajectory = unitTrajectory.clone();
    }

    public FloatCartesian getLocation()
    {
        return location;
    }

    public FloatCartesian getUnitTrajectory()
    {
        return unitTrajectory.clone();
    }

    protected void setUnitTrajectory(FloatCartesian unitTrajectory)
    {
        this.unitTrajectory = unitTrajectory;
    }

    abstract public void setLocation(FloatCartesian location);

    abstract public void move(FloatCartesian shift);

    /**
     * normally rotates on-the-spot.
     * @param theta
     */
    abstract public void rotate(float theta);
    abstract public void rotateAbout(float theta, FloatCartesian center);
}
