package trig.client;

import trig.game.engine.GameEngine;
import trig.utility.math.vector.FloatCartesian;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

/**
 * Created by marcos on 10/11/2014.
 */
class KeyboardController extends VesselController implements KeyListener {
    //CONTSTANT COMMAND INT VALS;
    public static int MOVE_FORWARD = 0;
    public static int MOVE_BACKWARD = 1;
    public static int TURN_CLOCKWISE = 2;
    public static int TURN_ANTICLOCKWISE = 3;
    public static int FIRE_BULLET = 4;
    public static int REVIVE = 5;
    public static int KILL = 6;

    protected boolean[] inputCommands = new boolean[7];

    protected Map<Integer, Integer> bindings;
    protected float turnAngle = (float) (Math.PI /50);

    FloatCartesian forwardVector, backwardVector;


    protected float curAngle = 0;

    public KeyboardController(Map<Integer, Integer> bindings)
    {
        this.bindings = bindings;
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
    public void updateInputs(){

        //note that these pairings (moveForward, moveBackward) are NOT compliments, (remember De Morgan's Theorem)
        if(inputCommands[REVIVE])
        {
            holdReviveButton();
        }
        else
        {
            releaseReviveButton();
        }

        if(player.isAlive())
        {


            if(inputCommands[TURN_CLOCKWISE])
            {
                if(!inputCommands[TURN_ANTICLOCKWISE])
                {
                    rotate(true);
                }
            }
            else if(inputCommands[TURN_ANTICLOCKWISE])
            {
                rotate(false);
            }

            if(inputCommands[MOVE_FORWARD])
            {
                if(!inputCommands[MOVE_BACKWARD])
                {
                    setVelocity(forwardVector);
                }
            } else if (inputCommands[MOVE_BACKWARD])
            {
                setVelocity(backwardVector);
            } else
            {
                setVelocity(new FloatCartesian());
            }

            if(inputCommands[FIRE_BULLET])
            {
                pullWeaponTrigger();
            }
            else
            {
                releaseWeaponTrigger();
            }



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
            releaseWeaponTrigger();
        }
        return;
    }

    public void rotate(boolean clockwise)
    {
        float theta = clockwise ? turnAngle : -turnAngle;
        rotate(theta);
        forwardVector.rotate(theta);

        backwardVector.rotate(theta);
    }

    //input handling
    public void setInputValue(int command, boolean value)
    {
        inputCommands[command] = value;
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

    @Override
    public void setPlayer(Player player)
    {
        super.setPlayer(player);
        forwardVector = new FloatCartesian(player.getSpeed()/2, 0);
        forwardVector.rotate(player.getFacingDirection());
        backwardVector = forwardVector.clone();
        backwardVector.rotate((float) Math.PI);
    }
}
