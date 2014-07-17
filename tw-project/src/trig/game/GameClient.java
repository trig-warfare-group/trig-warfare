package trig.game;

import java.awt.Graphics2D;

import trig.game.engine.GameEngine;
import trig.game.state.StateManager;
import trig.listener.ListenHandler;
import trig.utility.Constants;
import trig.view.GameView;

/**
 * GameClient acts as a model for the client-data.
 * This is where all the major features of the game are present.
 * @author brody
 *
 */

public class GameClient
{
    private GameEngine gameEngine;
    private ListenHandler listenHandler;
    private StateManager stateManager;
    private GameView gameView;
    private GameThread thread;
    private boolean gameRunning = true;

    public GameClient()
    {
        gameView = new GameView(this);
        listenHandler = new ListenHandler(gameView);
        stateManager = new StateManager(gameView);
        gameEngine = new GameEngine();
        thread = new GameThread();
        this.init();
    }

    /**
     * Initializes the game.
     */
    private void init()
    {
        //stateManager.init();
        listenHandler.init();
        gameView.init();
        thread.start();
    }
    public void updateGame()
    {
        gameEngine.update();
    }

    public void render(Graphics2D g)
    {
        gameEngine.render(g);
    }

    public void login(String username)
    {
        stateManager.login(username);
    }

    public void delayGame()
    {
        try {
            thread.sleep(Constants.FPS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class GameThread extends Thread
    {
        @Override
        public void run()
        {
            while(gameRunning)
            {
                updateGame();
                gameView.render();
                delayGame();
            }
        }
    }


}
