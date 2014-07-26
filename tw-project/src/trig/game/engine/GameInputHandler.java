package trig.game.engine;

import com.sun.xml.internal.ws.api.pipe.Engine;
import trig.game.GameClient;
import trig.game.Player;
import trig.game.state.BasicStateMachine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

/**
 * Bit of an example maybe for input handlers for various states machines? //shrug
 * Created by marcos on 26/07/2014.
 */
public class GameInputHandler implements KeyListener
{
    //CONTSTANT COMMAND INT VALS;
    public static int MOVE_FORWARD = 0;
    public static int MOVE_BACKWARD = 1;
    public static int TURN_CLOCKWISE = 3;
    public static int TURN_ANTICLOCKWISE = 4;
    public static int FIRE_BULLET = 5;
    public static int REVIVE = 6;
    public static int KILL = 7;



    protected Player player;
    protected HashMap<Integer, Integer> bindingMap;



//    protected class GameCommands {
//        public boolean moveForward, moveBackward, turnClockwise, turnAntiClockwise, fireBullet;
//
//        public GameCommands()
//        {
//            moveForward = false;
//            moveBackward = false;
//            turnClockwise = false;
//            turnAntiClockwise = false;
//            fireBullet = false;
//        }
//    }


    protected boolean[] inputCommands = new boolean[8];
    protected boolean[] activeCommands = new boolean[8];

    public GameInputHandler(Player player, HashMap bindingMap){
        this.player = player;
        this.bindingMap = bindingMap;
    }

    public void executeCommands(){
        //note that order of execution is important, and will effect gamePlay, so putting activeCommands in a list wouldn't be any better.
        if(activeCommands[REVIVE])
        {
            player.revive();
        }

        if(activeCommands[KILL])
        {
            player.kill();
        }

        if(player.isAlive())
        {

            if (activeCommands[FIRE_BULLET])
            {
                player.fireBullet();
            }

            if (activeCommands[TURN_CLOCKWISE])
            {
                player.turn(true);
            }
            else
            if (activeCommands[TURN_ANTICLOCKWISE])
            {
                player.turn(false);
            }

            if (activeCommands[MOVE_FORWARD])
            {
                player.move(true);
            }
            else
            if(activeCommands[MOVE_BACKWARD])
            {
                player.move(false);
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

        if(player.isAlive())
        {
            activeCommands[MOVE_FORWARD] = inputCommands[MOVE_FORWARD] && !inputCommands[MOVE_BACKWARD];

            activeCommands[MOVE_BACKWARD] = !inputCommands[MOVE_FORWARD] && inputCommands[MOVE_BACKWARD];

            activeCommands[TURN_CLOCKWISE] = inputCommands[TURN_CLOCKWISE] && !inputCommands[TURN_ANTICLOCKWISE];

            activeCommands[TURN_ANTICLOCKWISE] = !inputCommands[TURN_CLOCKWISE] && inputCommands[TURN_ANTICLOCKWISE];

            activeCommands[FIRE_BULLET] = inputCommands[FIRE_BULLET];
        }
        return;
    }

//    /**
//     * The commands should maybe be reset on death? (no?)
//     */
//    public void reset(){
//        activeCommands.moveForward = false;
//
//        activeCommands.moveBackward = false;
//
//        activeCommands.turnClockwise = false;
//
//        activeCommands.turnAntiClockwise = false;
//
//        activeCommands.fireBullet = false;
//    }


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
        if(bindingMap.containsKey(keyCode))
        {
            inputCommands[ bindingMap.get(keyCode) ] = true;
        }
        updateActive();
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
        if( bindingMap.containsKey(keyCode) )
        {
            inputCommands[ bindingMap.get(keyCode) ] = false;
        }

        updateActive();
    }

}
