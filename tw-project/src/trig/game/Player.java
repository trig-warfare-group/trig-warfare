package trig.game;

import trig.game.engine.GameEngine;
import trig.game.world.Bullet;
import trig.game.world.Vessel;
import trig.game.world.Weapon;
import trig.utility.math.vector.FloatCartesian;

import trig.utility.math.Methods;

import java.awt.*;

/**
 * Created by marcos on 26/07/2014.
 */
abstract public class Player
{
    protected GameEngine engine;
    protected FloatCartesian velocity;
    //amount of movement change at a time
    protected int moveDelta = 10;
    protected Vessel vessel;
    public final int playerID;
    protected int weaponCoolDown;

    public Player(GameEngine engine, int playerID){
        this.engine = engine;
        this.playerID = playerID;
        velocity = new FloatCartesian(moveDelta,0);

        vessel = new Vessel("AA", Color.GREEN, 10);
        revive();
        weaponCoolDown = 10;
    }

    public Vessel getVessel()
    {
        return vessel;
    }

    public void setVessel(Vessel vessel)
    {
        this.vessel = vessel;
    }

    public boolean isAlive(){
        return !vessel.isTrash();
    }

    public void fireBullet()
    {
        Weapon temp = vessel.getWeapon();
        engine.addEntity(new Bullet(temp.getLocation(), temp.getUnitTrajectory()));
        weaponCoolDown = 10;
    }

    public void revive()
    {
        vessel.setHp(vessel.getMaxHp());
        if( !engine.containsEntity(vessel) )
        {
            vessel.setLocation(new FloatCartesian(400,400));
            engine.addEntity(vessel);
        }
    }

    public void kill()
    {
        vessel.setHp(0);
    }

    abstract public void update();
}
