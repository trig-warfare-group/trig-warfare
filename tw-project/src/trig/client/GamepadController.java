package trig.client;

import net.java.games.input.*;
import net.java.games.input.Component;
import trig.game.engine.GameEngine;
import trig.utility.math.vector.FloatCartesian;

/**
 * Created by marcos on 10/11/2014.
 */
public class GamepadController extends VesselController
{


    private Controller controller;
    //between [-1, -1], strength of "thrust", e.g. joystick distance from center
    private FloatCartesian thrust;
    private static FloatCartesian NO_THRUST = new FloatCartesian();
    protected Component.Identifier.Axis THRUST_X = Component.Identifier.Axis.X;
    protected Component.Identifier.Axis THRUST_Y = Component.Identifier.Axis.Y;
    protected Component.Identifier.Axis LOOK_X = Component.Identifier.Axis.RX;
    protected Component.Identifier.Axis LOOK_Y = Component.Identifier.Axis.RY;
    protected Component.Identifier.Button SUICIDE_BTN = Component.Identifier.Button._6;
    protected Component.Identifier.Button REVIVE_BTN = Component.Identifier.Button._7;

    GamepadController(Controller controller)
    {
        this.controller = controller;
    }

    public void updateInputs()
    {
        controller.poll();
        FloatCartesian unitThrust = new FloatCartesian(controller.getComponent(THRUST_X).getPollData(), controller.getComponent(THRUST_Y).getPollData());
        FloatCartesian thrust = unitThrust.clone();
        thrust.scale(player.getSpeed()/2);
        setVelocity(unitThrust.magnitude() > 0.25 ? thrust : NO_THRUST);

        FloatCartesian lookVector = new FloatCartesian(controller.getComponent(LOOK_X).getPollData(), controller.getComponent(LOOK_Y).getPollData());

        if(lookVector.magnitude() > 0.25)
        {
            pullWeaponTrigger();
            setFacingDirection(lookVector.direction());
        }
        else
        {
            releaseWeaponTrigger();
        }

//        for(Component each : controller.getComponents())
//        {
//            if(each.getIdentifier().getClass() != Component.Identifier.Axis.class && each.getPollData() > 0)
//            {
//                System.out.printf("%s is active!%n", each.getName());
//            }
//        }

        if(controller.getComponent(SUICIDE_BTN).getPollData() > 0)
        {
            holdSuicideButton();
        }
        else
        {
            releaseSuicideButton();
        }

        if (controller.getComponent(REVIVE_BTN).getPollData() > 0)
        {
            holdReviveButton();
        }
        else
        {
            releaseReviveButton();
        }
        
    }


//    public void onControllerAllocation(Controller controller)
//    {
//        this.controller = controller;
//    }
//
//    public void onControllerDeallocation()
//    {
//        controller = null;
//    }

    public int getControllerPortNumber()
    {
        return controller.getPortNumber();
    }
}
