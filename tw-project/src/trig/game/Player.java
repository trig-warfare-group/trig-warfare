package trig.game;

import trig.game.engine.GameEngine;
import trig.game.entity.Bullet;
import trig.game.entity.Ship;
import trig.utility.math.vector.FloatCartesian;
import trig.utility.math.vector.IntCartesian;

import trig.utility.math.Methods;

import java.awt.*;

/**
 * Created by marcos on 26/07/2014.
 */
public class Player
{
    //CONTSTANT COMMAND INT VALS;
    public static int MOVE_FORWARD = 0;
    public static int MOVE_BACKWARD = 1;
    public static int TURN_CLOCKWISE = 3;
    public static int TURN_ANTICLOCKWISE = 4;
    public static int FIRE_BULLET = 5;
    public static int REVIVE = 6;
    public static int KILL = 7;

    protected boolean[] inputCommands = new boolean[8];
    protected boolean[] activeCommands = new boolean[8];

    protected GameEngine engine;

    protected IntCartesian initialVector;

    protected IntCartesian forwardVector;
    protected IntCartesian backwardVector;
    //amount of movement change at a time
    protected int moveDelta = 5;

    protected float curAngle = 0;
    protected float turnAngle = (float) (Math.PI /50);
    protected Ship ship;

    public Player(GameEngine engine){
        this.engine = engine;
        initialVector = new IntCartesian(moveDelta,0);
        forwardVector = initialVector.clone();
        backwardVector = initialVector.clone();
        backwardVector.rotate((float) Math.PI);

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
        FloatCartesian shipGunLocation = ship.getHitbox().get(0).clone(); //happens to be the first point in the polygon, luckily
        IntCartesian bulletSpawnLocation =  new IntCartesian(Math.round(shipGunLocation.x), Math.round(shipGunLocation.y));

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


    //input handling
    public void setInputValue(int command, boolean value)
    {
        inputCommands[command] = value;
        updateActive();
    }



    public void executeCommands(){
        //note that order of execution is important, and will effect gamePlay, so putting activeCommands in a list wouldn't be any better.
        if(activeCommands[REVIVE])
        {
            revive();
        }

        if(activeCommands[KILL])
        {
            kill();
        }

        if(isAlive())
        {

            if (activeCommands[FIRE_BULLET])
            {
                fireBullet();
            }

            if (activeCommands[TURN_CLOCKWISE])
            {
                turn(true);
            }
            else
            if (activeCommands[TURN_ANTICLOCKWISE])
            {
                turn(false);
            }

            if (activeCommands[MOVE_FORWARD])
            {
                move(true);
            }
            else
            if(activeCommands[MOVE_BACKWARD])
            {
                move(false);
            }
        }

    }

    /**
     * Similar in purpose to a state machine, but with so many small pairings like moveForward, moveBackward, individual FSMs would be cumbersome
     */
    public void updateActive(){

        //note that these pairings (moveForward, moveBackward) are NOT compliments, (remember De Morgan's Theorem)

        activeCommands[REVIVE] = inputCommands[REVIVE];

        activeCommands[KILL] = inputCommands[KILL];

        if(isAlive())
        {
            activeCommands[MOVE_FORWARD] = inputCommands[MOVE_FORWARD] && !inputCommands[MOVE_BACKWARD];

            activeCommands[MOVE_BACKWARD] = !inputCommands[MOVE_FORWARD] && inputCommands[MOVE_BACKWARD];

            activeCommands[TURN_CLOCKWISE] = inputCommands[TURN_CLOCKWISE] && !inputCommands[TURN_ANTICLOCKWISE];

            activeCommands[TURN_ANTICLOCKWISE] = !inputCommands[TURN_CLOCKWISE] && inputCommands[TURN_ANTICLOCKWISE];

            activeCommands[FIRE_BULLET] = inputCommands[FIRE_BULLET];
        }
        return;
    }

}
