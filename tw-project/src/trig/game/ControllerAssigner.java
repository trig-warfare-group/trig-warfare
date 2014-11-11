package trig.game;

import net.java.games.input.Controller;
import net.java.games.input.ControllerEvent;
import net.java.games.input.ControllerListener;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Assigns controllers to players as need be?, singleton?
 */
public class ControllerAssigner implements ControllerListener
{
    private interface Activity //trying another way of unifying synchronicity, this might be too inefficient, tbh.
    {
        //performs the action, should be effectively considered consumption of the action?
        public void perform();
    }

    private class AddPlayerActivity implements Activity
    {
        private GamepadControlTracker target;
        public AddPlayerActivity(GamepadControlTracker target)
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
        private GamepadControlTracker target;

        public RemovePlayerActivity(GamepadControlTracker target)
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

    private LinkedHashMap<GamepadControlTracker, Controller> playerToController = new LinkedHashMap<GamepadControlTracker, Controller>(); //stores players that may or may not be assigned controllers
    private LinkedHashMap<Controller, GamepadControlTracker> controllerToPlayer = new LinkedHashMap<Controller, GamepadControlTracker>(); //stores controllers that may or may not be assignment players.
    ArrayList<Controller> allControllers = new ArrayList<Controller>();
    public static ControllerAssigner assigner = new ControllerAssigner();

    /**
     * @return whether or not there is at least one player who does not have a controller assigned is returned.
     */
    private boolean hasPlayerWaiting()
    {
        return playerToController.containsValue(null); //NOT equivalent to controllerToPlayer.containsKey()
    }

    private boolean hasAvailableControllers()
    {
        return controllerToPlayer.containsValue(null);

    }

    private boolean isAllocated(GamepadControlTracker target)
    {
        if (!playerToController.containsKey(target))
        {
            throw new IllegalArgumentException("This player is not known to the collection");
        }
        return playerToController.get(target) != null;
    }

    private boolean isAllocated(Controller target)
    {
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
    private GamepadControlTracker getNextWaitingPlayer() throws IllegalArgumentException
    {
        if (!hasPlayerWaiting())
        {
            throw new IllegalArgumentException("There is no player waiting."); //helpfully specific error, and preserves encapsulation? probably inefficient in production code but good for unstable builds/debugging?
        }
        for (GamepadControlTracker each : playerToController.keySet())
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
    private Controller getNextAvailableController() throws IllegalArgumentException
    {
        if (!hasAvailableControllers())
        {
            throw new IllegalArgumentException("There is no controller available");
        }

        for (Controller each : controllerToPlayer.keySet())
        {
            if (!isAllocated(each))
            {
                return each;
            }
        }

        throw new IllegalArgumentException("There is no controller available, but the collection was checked anyway.");
    }

    private Controller getControllerByPort(int portNumber)
    {
        for (Controller each : controllerToPlayer.keySet())
        {
            if (each.getPortNumber() == portNumber)
            {
                return each;
            }
        }
        throw new IllegalArgumentException("There is no controller in the specified port.");
    }

    private void allocate(GamepadControlTracker player, Controller controller)
    {
        if (isAllocated(player))
        {
            throw new IllegalArgumentException("The specified player has already been allocated a controller.");
        } else if (isAllocated(controller))
        {
            throw new IllegalArgumentException("The specified controller has already been allocated to a player");
        }

        playerToController.put(player, controller);
        controllerToPlayer.put(controller, player);

        player.onControllerAllocation(controller);
    }

    private void deallocate(GamepadControlTracker player, Controller controller)
    {
        playerToController.put(player, null);
        controllerToPlayer.put(controller, null);
        player.onControllerDeallocation();
    }

    private void add(GamepadControlTracker target)
    {
        playerToController.put(target, null);
        if (hasAvailableControllers())
        {
            allocate(target, getNextAvailableController());
        }
        System.out.printf("Player added: %s%n", target.playerID);
    }

    private void remove(GamepadControlTracker target)
    {
        if (isAllocated(target))
        {
            deallocate(target, playerToController.get(target));
        }
        playerToController.remove(target);
    }

    private void add(Controller target)
    {
        controllerToPlayer.put(target, null);
        if (hasPlayerWaiting())
        {
            allocate(getNextWaitingPlayer(), target);
        }

        System.out.printf("Controller added: %s%n", target.getPortNumber());

        System.out.printf("Controller name: %s%n", target.getName());
    }

    private void remove(Controller target)
    {
        if (isAllocated(target))
        {
            deallocate(controllerToPlayer.get(target), target);
        }
        playerToController.remove(target);

        System.out.printf("Controller remove: %s%n", target.getPortNumber());
    }

    private void onGameUpdate()
    {
        for(GamepadControlTracker each : playerToController.keySet())
        {
            each.update();
        }
    }
    /**
     * Enforces synchronicity of all allocation changes, to ensure not attempts are made to doubled up, etc. this is probably a bad way to do it, but for now, it's fine?
     * @param activity the activity to perform.
     */
    synchronized private void performActivity(Activity activity)
    {
        activity.perform();
    }

    @Override
    public void controllerAdded(ControllerEvent controllerEvent)
    {
        Controller target = controllerEvent.getController();
        allControllers.add(target);
        if(target.getType().toString().equals("Gamepad"))//target.getName().equals("Controller (XBOX 360 For Windows)")
        {
            performActivity(new AddControllerActivity(target));
        }

    }

    @Override
    public void controllerRemoved(ControllerEvent controllerEvent)
    {
        Controller target = controllerEvent.getController();
        if(target.getType().toString().equals("Gamepad"))
        {
            performActivity(new RemoveControllerActivity(controllerEvent.getController()));
        }
    }

    public void playerAdded(GamepadControlTracker player)
    {
        performActivity(new AddPlayerActivity(player));
    }

    public void playerRemoved(GamepadControlTracker player)
    {
        performActivity(new RemovePlayerActivity(player));
    }

    public void updateGame()
    {
        performActivity(gameUpdateActivity);
    }


}
