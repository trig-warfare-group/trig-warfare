package trig.game;

import trig.game.engine.GameEngine;
import trig.utility.math.vector.FloatCartesian;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

/**
 * Created by marcos on 10/11/2014.
 */
class KeyboardControlTracker extends VesselControlTracker implements KeyListener {

    //CONTSTANT COMMAND INT VALS;
    public static int MOVE_FORWARD = 0;
    public static int MOVE_BACKWARD = 1;
    public static int TURN_CLOCKWISE = 2;
    public static int TURN_ANTICLOCKWISE = 3;
    public static int FIRE_BULLET = 4;
    public static int REVIVE = 5;
    public static int KILL = 6;

    protected boolean[] inputCommands = new boolean[7];
    protected boolean[] activeCommands = new boolean[4];

    protected Map<Integer, Integer> bindings;
    protected float turnAngle = (float) (Math.PI /50);

    FloatCartesian initialVector, forwardVector, backwardVector;


    protected float curAngle = 0;

    public KeyboardControlTracker(GameEngine engine, Map<Integer, Integer> bindings, int playerID)
    {
        super(engine, playerID);
        this.bindings = bindings;
        initialVector = new FloatCartesian(moveDelta,0);
        forwardVector = initialVector.clone();
        backwardVector = initialVector.clone();
        backwardVector.rotate((float) Math.PI);
    }

    public Map<Integer, Integer> getBindings()
    {
        return bindings;
    }

    public void setBindings(Map<Integer, Integer> bindings)
    {
        this.bindings = bindings;
    }

    /**
     * Similar in purpose to a state machine, but with so many small pairings like moveForward, moveBackward, individual FSMs would be cumbersome
     */
    public void updateActive(){

        //note that these pairings (moveForward, moveBackward) are NOT compliments, (remember De Morgan's Theorem)
        if(inputCommands[REVIVE])
        {
            holdReviveButton();
        }
        else
        {
            releaseReviveButton();
        }

        if(isAlive())
        {
            activeCommands[MOVE_FORWARD] = inputCommands[MOVE_FORWARD] && !inputCommands[MOVE_BACKWARD];

            activeCommands[MOVE_BACKWARD] = !inputCommands[MOVE_FORWARD] && inputCommands[MOVE_BACKWARD];

            activeCommands[TURN_CLOCKWISE] = inputCommands[TURN_CLOCKWISE] && !inputCommands[TURN_ANTICLOCKWISE];

            activeCommands[TURN_ANTICLOCKWISE] = !inputCommands[TURN_CLOCKWISE] && inputCommands[TURN_ANTICLOCKWISE];

            setFiring(inputCommands[FIRE_BULLET]);



            if(inputCommands[KILL])
            {
                holdSuicideButton();
            }
            else
            {
                releaseSuicideButton();
            }

        }else{
            releaseSuicideButton();
            setFiring(false);

            activeCommands[MOVE_FORWARD] = false;

            activeCommands[MOVE_BACKWARD] = false;

            activeCommands[TURN_CLOCKWISE] = false;

            activeCommands[TURN_ANTICLOCKWISE] = false;
        }
        return;
    }


    public void update()
    {
        FloatCartesian initialVector, forwardVector, backwardVector;
        if(isReviveDue())
        {
            revive();
        }





        if(isAlive())
        {
            if(isFiring())
            {
                fireBullet();
            }

            if(activeCommands[MOVE_FORWARD])
            {
                move(true);
            }
            else
            if (activeCommands[MOVE_BACKWARD])
            {
                move(false);
            }

            if(activeCommands[TURN_CLOCKWISE])
            {
                turn(true);
            }
            else if(activeCommands[TURN_ANTICLOCKWISE])
            {
                turn(false);
            }

            if(isSuicideDue())
            {
                kill();
            }
        }
    }
    public void turn(boolean clockwise)
    {
        float theta = clockwise ? turnAngle : -turnAngle;
        vessel.rotate(theta);
        forwardVector.rotate(theta);

        backwardVector.rotate(theta);
    }

    public void move(boolean forward){
        vessel.move(forward ? forwardVector : backwardVector);
    }

    //input handling
    public void setInputValue(int command, boolean value)
    {
        inputCommands[command] = value;
        updateActive();
    }

    /**
     * Invoked when a key has been typed.
     * See the class description for {@link java.awt.event.KeyEvent} for a definition of
     * a key typed event.
     *
     * @param e
     */
    @Override
    public void keyTyped(KeyEvent e)
    {
        //nothing?
    }

    /**
     * Invoked when a key has been pressed.
     * See the class description for {@link java.awt.event.KeyEvent} for a definition of
     * a key pressed event.
     *
     * @param e
     */
    @Override
    public void keyPressed(KeyEvent e)
    {
        int keyCode =  e.getKeyCode();
        if(bindings.containsKey(keyCode))
        {
            setInputValue(bindings.get(keyCode), true);
        }
    }

    /**
     * Invoked when a key has been released.
     * See the class description for {@link java.awt.event.KeyEvent} for a definition of
     * a key released event.
     *
     * @param e
     */
    @Override
    public void keyReleased(KeyEvent e)
    {
        int keyCode = e.getKeyCode();
        if( bindings.containsKey(keyCode) )
        {
            setInputValue(bindings.get(keyCode), false);
        }
    }


}
