package trig.client;

import trig.game.engine.Match;
import trig.game.world.Bullet;
import trig.game.world.Vessel;
import trig.game.world.Weapon;
import trig.utility.math.vector.FloatCartesian;

import trig.utility.math.Methods;

import java.awt.*;

/**
 * Created by marcos on 26/07/2014.
 */
public class Player
{

    //amount of movement change at a time
    protected int speed = 10;
    protected Vessel vessel;
    public final int playerID;
    Match match;
    public Player(int playerID)
    {
        this.playerID = playerID;
        vessel = new Vessel("AA", Color.GREEN, 10);
    }
    public int getSpeed()
    {
        return speed;
    }
    public boolean isAlive(){
        return !vessel.isTrash();
    }

    public void fireBullet()
    {
        match.add(vessel.fireWeapon());
    }

    public void move(FloatCartesian unitVelicity)
    {
        vessel.move(unitVelicity);
    }

    public FloatCartesian getLocation(){
        return vessel.getLocation();
    }

    public void setLocation(FloatCartesian location)
    {
        vessel.setLocation(location);
    }

    public void revive()
    {
        vessel.setHp(vessel.getMaxHp());
        if( !match.contains(vessel) )
        {
            vessel.setLocation(new FloatCartesian(400,400));
            match.add(vessel);
        }
    }

    public void kill()
    {
        vessel.setHp(0);
    }

    public void turn(float theta)
    {
       vessel.rotate(theta);

    }

    public void setFacingDirection(float theta)
    {

        vessel.setFacingDirection(theta);
    }

    public float getFacingDirection()
    {
        return vessel.getFacingDirection();
    }

    public void rotate(float theta)
    {
        vessel.rotate(theta);
    }

    public int getHP()
    {
        return vessel.getHp();
    }

    public void setHP(int hp)
    {
        vessel.setHp(hp);
    }

    //TODO: ROBUSTNESS OF WHEN THE PLAYER IS NOT IN A MATCH, ETC?

    public void join(Match match)
    {
        this.match = match;
    }

    public void leaveMatch()
    {
        this.match = null;
    }
}
