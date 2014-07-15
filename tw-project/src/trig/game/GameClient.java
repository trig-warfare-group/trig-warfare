package trig.game;

        import java.awt.Graphics2D;

        import trig.game.engine.GameEngine;
        import trig.game.state.StateManager;
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
    private StateManager stateManager;
    private GameView gameView;
    private GameThread thread;
    private boolean gameRunning = true;

    //temp demo stuff
    private GameEngine gameEngine;

    private int testCounter = 0;


    public GameClient()
    {
        gameEngine = new GameEngine();
        gameView = new GameView(this);
        stateManager = new StateManager(gameView);
        thread = new GameThread();

        this.init();
    }

    public void login(String username)
    {
        stateManager.login(username);
    }

    /**
     * Initializes the game.
     */
    private void init()
    {
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
