package trig.game;

    import java.awt.Graphics2D;
    import java.awt.event.KeyEvent;
    import java.util.HashMap;

    import net.java.games.input.Controller;
    import net.java.games.input.ControllerEnvironment;
    import net.java.games.input.ControllerEvent;
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
    private GameEngine gameEngine;
    private VesselControlTracker[] controlTrackers;
    private StateManager stateManager;
    private GameView gameView;
    private GameThread thread;
    private boolean gameRunning = true;
    private ControllerEnvironment controllerEnvironment;

    public GameClient()
    {
        gameView = new GameView(this);
        stateManager = new StateManager(gameView);

        gameEngine = new GameEngine();

        thread = new GameThread();
        controlTrackers = new VesselControlTracker[4]; //limit to one
        controllerEnvironment = ControllerEnvironment.getDefaultEnvironment();
        this.init();
    }

    private void addKeyboardPlayer(int playerID)
    {
        HashMap<Integer, Integer> bindingMap = new HashMap<Integer, Integer>();

        bindingMap.put(KeyEvent.VK_UP, KeyboardControlTracker.MOVE_FORWARD);
        bindingMap.put(KeyEvent.VK_DOWN, KeyboardControlTracker.MOVE_BACKWARD);
        bindingMap.put(KeyEvent.VK_LEFT, KeyboardControlTracker.TURN_ANTICLOCKWISE);
        bindingMap.put(KeyEvent.VK_RIGHT, KeyboardControlTracker.TURN_CLOCKWISE);

        bindingMap.put(KeyEvent.VK_SPACE, KeyboardControlTracker.FIRE_BULLET);

        bindingMap.put(KeyEvent.VK_ENTER, KeyboardControlTracker.REVIVE);
        bindingMap.put(KeyEvent.VK_BACK_SPACE, KeyboardControlTracker.KILL);

        KeyboardControlTracker controlTracker = new KeyboardControlTracker(gameEngine, bindingMap, playerID);
        gameView.getGameFrame().addKeyListener(controlTracker);
        controlTrackers[playerID] = controlTracker;
    }
    //should probably rearrange this and possibly the control tracker to allow for players to pick their input device?, and possibly the assigner? maybe, maybe not. At the least, it might be useful to make it such that players can change input mode on the fly.
    private void addGamepadControlTracker(int playerId)
    {
        GamepadControlTracker targetPlayer = new GamepadControlTracker(gameEngine, playerId);
        controlTrackers[playerId] = targetPlayer; //disabling for now until null pointer bug is understood
        ControllerAssigner.assigner.playerAdded(targetPlayer); //when a controller is connected it will be assigned to the player if possible.
    }

    /**
     * Initializes the game.
     */
    private void init()
    {
        //stateManager.init();
        gameView.init();
        controllerEnvironment.addControllerListener(ControllerAssigner.assigner);
        Controller[] controllers =  controllerEnvironment.getControllers();
        if(controllers.length > 0)
        {
            for(Controller each : controllers)
            {
                ControllerAssigner.assigner.controllerAdded(new ControllerEvent(each));
            }
        }
        addGamepadControlTracker(0);
        //addKeyboardPlayer(0);
        thread.start();
    }

    public void updateInputs()
    {
        for(VesselControlTracker each : controlTrackers)
        {
            if(each != null)
            {
                each.update();
            }
        }
    }

    public void updateGame()
    {
        updateInputs();

        ControllerAssigner.assigner.updateGame();
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
