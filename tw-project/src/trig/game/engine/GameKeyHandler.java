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
public class GameKeyHandler implements KeyListener
{
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

    public GameKeyHandler(Player player, HashMap bindingMap){
        this.player = player;
        this.bindingMap = bindingMap;
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
            player.setInputValue(bindingMap .get(keyCode), true);
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
        if( bindingMap.containsKey(keyCode) )
        {
            player.setInputValue(bindingMap.get(keyCode), false);
        }
    }

}
