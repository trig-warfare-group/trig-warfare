package trig.client;

import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.ControllerEvent;
import net.java.games.input.ControllerListener;
import trig.game.engine.Match;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

/**
 * Assigns controllers to players as need be?, singleton?
 */

//TODO: currently players cannot join mid-match.
public class PlayerManager
{
    public static HashMap<Integer, Integer> getDefaultKeyBindings()
    {
        HashMap<Integer, Integer> bindingMap = new HashMap<Integer, Integer>();

        bindingMap.put(KeyEvent.VK_UP, KeyboardController.MOVE_FORWARD);
        bindingMap.put(KeyEvent.VK_DOWN, KeyboardController.MOVE_BACKWARD);
        bindingMap.put(KeyEvent.VK_LEFT, KeyboardController.TURN_ANTICLOCKWISE);
        bindingMap.put(KeyEvent.VK_RIGHT, KeyboardController.TURN_CLOCKWISE);

        bindingMap.put(KeyEvent.VK_SPACE, KeyboardController.FIRE_BULLET);

        bindingMap.put(KeyEvent.VK_ENTER, KeyboardController.REVIVE);
        bindingMap.put(KeyEvent.VK_BACK_SPACE, KeyboardController.KILL);

        return bindingMap;
    }

    private class JInputControllerListener implements ControllerListener
    {
        @Override
        public void controllerAdded(ControllerEvent controllerEvent)
        {
            Controller target = controllerEvent.getController();
            if (target.getType().toString().equals("Gamepad"))//target.getName().equals("Controller (XBOX 360 For Windows)")
            {
                performActivity(new AddControllerActivity(target));
            }

        }

        @Override
        public void controllerRemoved(ControllerEvent controllerEvent)
        {
            Controller target = controllerEvent.getController();
            if (target.getType().toString().equals("Gamepad"))
            {
                performActivity(new RemoveControllerActivity(controllerEvent.getController()));
            }
        }
    }
    private Activity forceKeyboardActivity = new Activity()
    {
        public void perform(){
            Player target;
            if (!isAllocated(keyboardController))
            {
                target = players.get(0);
                if (isAllocated(target))
                {
                    deallocate(target, playerToController.get(target));
                }
                allocate(target, keyboardController);
            }
        }
    }

    ;

    private Activity forceGamepadActivity = new Activity()
    {
        @Override
        public void perform()
        {
            Player target;
            if(hasAvailableControllers())
            {
                target = players.get(0);
                if(isAllocated(target))
                {
                    deallocate(target, playerToController.get(target));
                }
                allocate(target, getNextAvailableController());
            }
        }
    };

    private class DebugListener implements KeyListener
    {
        @Override
        public void keyTyped(KeyEvent e)
        {

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

            switch (e.getKeyCode())
            {
                //TODO display a message about success/failure
                case KeyEvent.VK_1: //force keyboard use, if possible
                    performActivity(forceKeyboardActivity);
                    break;
                case KeyEvent.VK_2: //force controller use if possible
                    performActivity(forceGamepadActivity);
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e)
        {

        }
    }
    public final KeyListener debugListener = new DebugListener();

    private interface Activity //trying another way of unifying synchronicity, this might be too inefficient, tbh.
    {
        //performs the action, should be effectively considered consumption of the action?
        public void perform();
    }

    private class AddPlayerActivity implements Activity
    {
        private Player target;

        public AddPlayerActivity(Player target)
        {
            this.target = target;
        }

        @Override
        public void perform()
        {
            add(target);
        }
    }

    private class RemovePlayerActivity implements Activity
    {
        private Player target;

        public RemovePlayerActivity(Player target)
        {
            this.target = target;
        }

        @Override
        public void perform()
        {
            remove(target);
        }
    }

    private class AddControllerActivity implements Activity
    {
        private Controller target;

        AddControllerActivity(Controller target)
        {
            this.target = target;
        }

        @Override
        public void perform()
        {
            add(target);
        }
    }

    private class RemoveControllerActivity implements Activity
    {
        private Controller target;

        RemoveControllerActivity(Controller target)
        {
            this.target = target;
        }


        @Override
        public void perform()
        {
            remove(target);
        }
    }

    /**
     * Triggers when the game client updates, generally once per frame?
     */
    private Activity gameUpdateActivity = new Activity()
    {
        @Override
        public void perform()
        {
            onGameUpdate();
        }
    };

    private class AttachMatchActivity implements Activity
    {
        Match match;
        public AttachMatchActivity(Match match)
        {
            this.match = match;
        }

        @Override
        public void perform()
        {
            allJoin(match);
        }
    }

    private Activity detachMatchActivity = new Activity()
    {
        @Override
        public void perform()
        {
            allLeaveMatch();
        }
    };

    private LinkedList<Player> players; //should probably be an array long term
    private int lastPlayerID;

    private LinkedHashMap<Player, VesselController> playerToController; //stores players that may or may not be assigned controllers
    private LinkedHashMap<VesselController, Player> controllerToPlayer; //stores controllers that may or may not be assignment players.
    private ArrayList<VesselController> activeControllers;
    private final KeyboardController keyboardController; //there need only be one of these, ever?
    private JInputControllerListener gamepadConnectionListener;
    private ControllerEnvironment controllerEnvironment;
    private Match currentMatch;

    public PlayerManager(ControllerEnvironment controllerEnvironment, KeyboardController keyboardController)
    {
        players = new LinkedList<Player>();
        lastPlayerID = 0;
        playerToController = new LinkedHashMap<Player, VesselController>();
        controllerToPlayer = new LinkedHashMap<VesselController, Player>();
        activeControllers = new ArrayList<VesselController>();
        this.controllerEnvironment = controllerEnvironment;

        this.keyboardController = keyboardController;
        gamepadConnectionListener = new JInputControllerListener();

        Controller[] existantControllers = controllerEnvironment.getControllers();
        if (existantControllers.length > 0)
        {
            for (Controller each : existantControllers)
            {
                add(each);
            }
        }

        controllerEnvironment.addControllerListener(gamepadConnectionListener);
        currentMatch = null;
    }

    /**
     * @return whether or not there is at least one player who does not have a controller assigned is returned.
     */
    private boolean hasPlayerWaiting()
    {
        return playerToController.containsValue(null); //NOT equivalent to controllerToPlayer.containsKey()
    }

    private boolean hasAvailableControllers()
    {
        return controllerToPlayer.containsValue(null) || keyboardController.hasPlayer();

    }

    private boolean isAllocated(Player target)
    {
        if (!playerToController.containsKey(target))
        {
            throw new IllegalArgumentException("This player is not known to the collection");
        }
        return playerToController.get(target) != null;
    }

    private boolean isAllocated(VesselController target)
    {
        if(target == keyboardController)
        {
            return keyboardController.hasPlayer();
        }

        if (!controllerToPlayer.containsKey(target))
        {
            throw new IllegalArgumentException("This controller is not known to the collection");
        }
        return controllerToPlayer.get(target) != null;
    }

    /**
     * Returns a single player who is waiting to receive a controller even if there are multiple of them, this should be called whenever a new controller is available and there is a waiting player.
     *
     * @return a player yet to be assigned
     * @throws IllegalArgumentException if there is no player waiting.
     */
    private Player getNextWaitingPlayer() throws IllegalArgumentException
    {
        if (!hasPlayerWaiting())
        {
            throw new IllegalArgumentException("There is no player waiting."); //helpfully specific error, and preserves encapsulation? probably inefficient in production code but good for unstable builds/debugging?
        }
        for (Player each : playerToController.keySet())
        {
            if (!isAllocated(each))
            {
                return each;
            }
        }

        throw new IllegalArgumentException("There is no player waiting, but the collection was checked anyway."); //helpfully specific error, and preserves encapsulation? probably inefficient in production code but good for unstable builds/debugging?
    }


    /**
     * @return an available controller, to be assigned to a player.
     * @throws IllegalArgumentException
     */
    private VesselController getNextAvailableController() throws IllegalArgumentException
    {
        if (!hasAvailableControllers())
        {
            throw new IllegalArgumentException("There is no controller available");
        }

        for (VesselController each : controllerToPlayer.keySet())
        {
            if (!isAllocated(each))
            {
                return each;
            }
        }

        //gamepads get preferential treatment, only attach to the keyboard if necessary or demanded
        if (!keyboardController.hasPlayer())
        {
            return keyboardController;
        }
        throw new IllegalArgumentException("There is no controller available, but the collection was checked anyway.");
    }

    private GamepadController getControllerByPort(int portNumber)
    {
        GamepadController eachGamePad;
        for (VesselController each : controllerToPlayer.keySet())
        {
            eachGamePad = (GamepadController) each;
            if (each instanceof GamepadController)
                if (eachGamePad.getControllerPortNumber() == portNumber)
                {
                    return eachGamePad;
                }
        }
        throw new IllegalArgumentException("There is no controller in the specified port.");
    }

    private void allocate(Player player, VesselController controller)
    {
        if (isAllocated(player))
        {
            throw new IllegalArgumentException("The specified player has already been allocated a controller.");
        } else if (isAllocated(controller))
        {
            throw new IllegalArgumentException("The specified controller has already been allocated to a player");
        }
        controller.setPlayer(player);
        playerToController.put(player, controller);
        controllerToPlayer.put(controller, player);
        activeControllers.add(controller);
    }

    private void deallocate(Player player, VesselController controller)
    {
        playerToController.put(player, null);
        controllerToPlayer.put(controller, null);
        activeControllers.remove(controller);
        controller.removePlayer();
    }

    private void add(Player target)
    {
        players.add(target);
        playerToController.put(target, null);
        if (hasAvailableControllers())
        {
            allocate(target, getNextAvailableController());
        }
        System.out.printf("Player added: %s%n", target.playerID);
    }

    @Deprecated
    //only the use of engine this way is deprecated, as more generally, the player that needs to effect the engine should probably be a nested class in engine, maybe? Well, ClientEngine, or match, or, you know, whatever.
    public Player addNewPlayer() //rather,
    {
        lastPlayerID++;
        Player result = new Player(lastPlayerID);
        add(result);
        return result;
    }

    public void remove(Player target)
    {
        if (isAllocated(target))
        {
            deallocate(target, playerToController.get(target));
        }
        playerToController.remove(target);
        players.remove(target);
    }

    private void add(VesselController target)
    {
        controllerToPlayer.put(target, null);
        if (hasPlayerWaiting())
        {
            allocate(getNextWaitingPlayer(), target);
        }

        System.out.printf("Controller added: %s%n", target);
    }

    private void remove(VesselController target)
    {
        if (isAllocated(target))
        {
            deallocate(controllerToPlayer.get(target), target);
        }

        System.out.printf("Controller removed: %s%n", target);
    }

    private void add(Controller target)
    {
        if(target.getType() == Controller.Type.GAMEPAD)
        {
            add(new GamepadController(target));
        }
    }

    private void remove(Controller target)
    {
        if(target.getType() == Controller.Type.GAMEPAD)
        {
            remove(getControllerByPort(target.getPortNumber()));
        }
    }

    private void onGameUpdate()
    {
        for (VesselController each : controllerToPlayer.keySet())
        {
            if (isAllocated(each))
            {
                each.update();
            }
        }
    }

    private void allJoin(Match match)
    {
        this.currentMatch = match;
        for (Player each : players)
        {
            each.join(match);
            each.revive();
        }
    }

    private void allLeaveMatch()
    {
        this.currentMatch = null;
        for(Player each : players)
        {
            each.leaveMatch();
        }
    }

    /**
     * Enforces synchronicity of all allocation changes, to ensure not attempts are made to doubled up, etc. this is probably a bad way to do it, but for now, it's fine?
     *
     * @param activity the activity to perform.
     */
    synchronized private void performActivity(Activity activity)
    {
        activity.perform();
    }

    public void attach(Player player)
    {
        performActivity(new AddPlayerActivity(player));
    }

    public void detach(Player player)
    {
        performActivity(new RemovePlayerActivity(player));
    }

    public void updateGame()
    {
        performActivity(gameUpdateActivity);
    }

    //TODO add an isMatchAttached activity?
    public void attach(Match match)
    {
        performActivity(new AttachMatchActivity(match));
    }

    public void detachMatch()
    {
        performActivity(detachMatchActivity);
    }

}
