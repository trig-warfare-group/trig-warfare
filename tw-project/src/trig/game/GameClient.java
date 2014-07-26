package trig.game;

        import java.awt.Graphics2D;
        import java.awt.event.KeyEvent;
        import java.util.HashMap;

        import trig.game.engine.GameEngine;
        import trig.game.engine.GameKeyHandler;
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
    private GameEngine gameEngine;
    public Player player;
    public GameKeyHandler inputHandler;

    private StateManager stateManager;
    private GameView gameView;
    private GameThread thread;
    private boolean gameRunning = true;

    public GameClient()
    {
        gameView = new GameView(this);
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

        player = new Player(gameEngine);

        HashMap<Integer, Integer> bindingMap = new HashMap<Integer, Integer>();

        bindingMap.put(KeyEvent.VK_UP, player.MOVE_FORWARD);
        bindingMap.put(KeyEvent.VK_DOWN, player.MOVE_BACKWARD);
        bindingMap.put(KeyEvent.VK_LEFT, player.TURN_ANTICLOCKWISE);
        bindingMap.put(KeyEvent.VK_RIGHT, player.TURN_CLOCKWISE);

        bindingMap.put(KeyEvent.VK_SPACE, player.FIRE_BULLET);

        bindingMap.put(KeyEvent.VK_ENTER, player.REVIVE);
        bindingMap.put(KeyEvent.VK_BACK_SPACE, player.KILL);

        inputHandler = new GameKeyHandler(player, bindingMap);


        gameView.getGameFrame().addKeyListener(inputHandler);

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
                player.executeCommands();
                updateGame();
                gameView.render();
                delayGame();
            }
        }
    }
}
