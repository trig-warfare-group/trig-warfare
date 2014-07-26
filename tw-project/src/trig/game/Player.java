package trig.game;

import trig.game.engine.GameEngine;
import trig.game.entity.Bullet;
import trig.game.entity.Ship;
import trig.utility.math.vector.IntCartesian;

import trig.utility.math.Methods;

import java.awt.*;

/**
 * Created by marcos on 26/07/2014.
 */
public class Player
{
    protected GameEngine engine;

    protected IntCartesian initialVector;

    protected IntCartesian forwardVector;
    protected IntCartesian backwardVector;
    //amount of movement change at a time
    protected int moveDelta = 5;

    protected float curAngle = 0;
    protected float turnAngle = (float) (Math.PI /25);
    protected Ship ship;

    public Player(GameEngine engine){
        this.engine = engine;
        initialVector = new IntCartesian(moveDelta,0);
        forwardVector = initialVector.clone();
        backwardVector = initialVector.clone();
        backwardVector.rotate((float) Math.PI/2);

        ship = new Ship("AA", Color.GREEN, 10);

        revive();
    }

    public Ship getShip()
    {
        return ship;
    }

    public void setShip(Ship ship)
    {
        this.ship = ship;
    }

    public boolean isAlive(){
        return !ship.isTrash();
    }

    public void turn(boolean clockwise){
        float theta = clockwise ? turnAngle : -turnAngle;
        ship.rotate(theta);

        curAngle = Methods.normalise(curAngle + theta);

        forwardVector = initialVector.clone();
        forwardVector.rotate(curAngle);

        backwardVector = forwardVector.clone();
        backwardVector.rotate((float) Math.PI);

    }

    public void move(boolean forward){
        ship.move(forward ? forwardVector : backwardVector);
    }

    public void fireBullet()
    {
        Rectangle bounds = ship.getHitbox().getBounds();
        IntCartesian bulletSpawnLocation = new IntCartesian(bounds.width, (int)Math.round(bounds.height/2.0));//(int)Math.round(bounds.width/2.0) );


        int centerX = (int)Math.round(bounds.width/2.0);
        int centerY = (int)Math.round(bounds.height/2.0);
        bulletSpawnLocation.translate(-centerX, -centerY);

        bulletSpawnLocation.rotate(curAngle);

        bulletSpawnLocation.translate(centerX, centerY);

        bulletSpawnLocation.translate(ship.getX(), ship.getY());

        //move ahead some degree to prevent collision
        bulletSpawnLocation.translate(forwardVector.x*3, forwardVector.y*3);
        engine.addEntity( new Bullet(bulletSpawnLocation, curAngle) );
    }

    public void revive()
    {
        ship.setHp(ship.getMaxHp());
        if( !engine.containsEntity(ship) )
        {
            ship.setLocation(250,250);
            engine.addEntity(ship);
        }
    }

    public void kill()
    {
        ship.setHp(0);
    }

}
