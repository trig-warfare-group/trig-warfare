package trig.game;

import net.java.games.input.*;
import net.java.games.input.Component;
import trig.game.engine.GameEngine;
import trig.utility.math.vector.FloatCartesian;

/**
 * Created by marcos on 10/11/2014.
 */
public class GamepadControlTracker extends VesselControlTracker
{


    private Controller controller;
    //between [-1, -1], strength of "thrust", e.g. joystick distance from center
    private FloatCartesian thrust;
    private static FloatCartesian noThrust = new FloatCartesian();
    private float weaponDirection;


    protected Component.Identifier.Axis THRUST_X = Component.Identifier.Axis.X;
    protected Component.Identifier.Axis THRUST_Y = Component.Identifier.Axis.Y;
    protected Component.Identifier.Axis LOOK_X = Component.Identifier.Axis.RX;
    protected Component.Identifier.Axis LOOK_Y = Component.Identifier.Axis.RY;
    protected Component.Identifier.Button SUICIDE_BTN = Component.Identifier.Button._6;
    protected Component.Identifier.Button REVIVE_BTN = Component.Identifier.Button._7;

    GamepadControlTracker(GameEngine engine, int playerID)
    {
        super(engine, playerID);
        weaponDirection = 0;
    }

    public FloatCartesian getThrust()
    {
        return thrust.clone();
    }

    protected void setThrust(FloatCartesian thrust)
    {
        this.thrust = thrust;
    }

    public float getWeaponDirection()
    {
        return weaponDirection;
    }


    public void setWeaponDirection(float weaponDirection)
    {
        this.weaponDirection = weaponDirection;
    }

    public void updateInputs()
    {
        controller.poll();
        FloatCartesian unitThrust = new FloatCartesian(controller.getComponent(THRUST_X).getPollData(), controller.getComponent(THRUST_Y).getPollData());
        FloatCartesian thrust = unitThrust.clone();
        thrust.scale(moveDelta/2);
        setThrust(unitThrust.magnitude() > 0.25 ? thrust : noThrust);

        FloatCartesian lookVector = new FloatCartesian(controller.getComponent(LOOK_X).getPollData(), controller.getComponent(LOOK_Y).getPollData());
        setWeaponDirection(lookVector.direction());
        setFiring(lookVector.magnitude() > 0.25);

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

    @Override
    public void update()
    {
        float oldDirection = weaponDirection;
        updateInputs();
        if(isSuicideDue())
        {
            kill();
        }
        if(isReviveDue())
        {
            revive();
        }
        if(isAlive())
        {
            vessel.move(getThrust());
            vessel.rotate(weaponDirection - oldDirection);
            if(isFiring() && weaponCoolDown == 0)
            {
                fireBullet();
            }
            else
            {
                if(weaponCoolDown > 0)
                {
                    weaponCoolDown--;
                }
            }
        }

    }


    public void onControllerAllocation(Controller controller)
    {
        this.controller = controller;
    }

    public void onControllerDeallocation()
    {
        controller = null;
    }
}
