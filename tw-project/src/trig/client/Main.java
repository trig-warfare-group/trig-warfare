package trig.client;


import net.java.games.input.ControllerEnvironment;
import trig.game.engine.GameEngine;
import trig.game.engine.Match;
//import trig.game.state.StateManager;
import trig.utility.Constants;
import trig.view.GameView;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class Main
{
    public static Main MAIN = new Main();
    public final Timer timer = new Timer(); //should this not be public? dunno yet.. probably.
    public final PlayerManager playerManager;

    private class MainTask extends TimerTask
    {
        @Override
        public void run()
        {
            updateGame();
            gameView.render();
        }
    }


    private boolean gameRunning;
    private Match match;
    //private StateManager stateManager;
    private GameView gameView;



    public Main()
    {
        gameView = new GameView();
        //stateManager = new StateManager(gameView);

        KeyboardController keyboardController = new KeyboardController(PlayerManager.getDefaultKeyBindings());
        addKeyListener(keyboardController);
        playerManager = new PlayerManager(ControllerEnvironment.getDefaultEnvironment(), keyboardController);
        addKeyListener(playerManager.debugListener);
        playerManager.addNewPlayer();
        match = new Match(playerManager);

        gameRunning = false;
        init();
    }

    private Player addNewPlayer()
    {
        return playerManager.addNewPlayer();
    }

    /**
     * Initializes the game, ONLY CALL THIS ONCE.
     */
    private void init() throws IllegalStateException
    {
        //stateManager.init();
        gameView.init();


        timer.scheduleAtFixedRate(new MainTask(), 0, Constants.FrameDelay);
        gameRunning = true;
    }
    public void updateGame()
    {
        match.update();
    }

    public void render(Graphics2D g)
    {
        match.render(g);
    }

    public void addKeyListener(KeyListener listener)
    {
        gameView.getGameFrame().addKeyListener(listener);
    }

//    public void login(String username)
//    {
//        stateManager.login(username);
//    }

//    public void delayGame()
//    {
//        try {
//            thread.sleep(Constants.FPS);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

//    private class GameThread extends Thread
//    {
//        @Override
//        public void run()
//        {
//            while(gameRunning)
//            {
//                updateGame();
//                gameView.render();
////                delayGame();
//            }
//        }
//    }
	public static void main(String [] args)
	{
		MAIN.init();
	}
}
