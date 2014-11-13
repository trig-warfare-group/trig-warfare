package trig.client;

import trig.game.engine.GameEngine;
import trig.utility.math.vector.FloatCartesian;

/**
 * Created by marcos on 10/11/2014.
 */
abstract class VesselController
{


    //revive and suicide only occur once per press, so forget about it being performed once it is released.
    private boolean weaponTriggerHeld, revivePerformed, suicidePerformed, reviveHeld, suicideHeld;
    private FloatCartesian velocity;
    protected Player player;
    @Deprecated
    private int fireRateTicker;


    //bit of a hack really, but frame-based behaviour instead of properly timed behaviour will do for now, (it doesn't have long-term wait flexibility but yeh.)

    protected VesselController()
    {
        this.player = null;
        weaponTriggerHeld = false;
        revivePerformed = false;
        suicidePerformed = false;
        reviveHeld = false;
        suicideHeld = false;
        velocity = new FloatCartesian();

        fireRateTicker = 0;
    }


    protected boolean isReviveDue()
    {
        if (reviveHeld && !revivePerformed)
        {
            revivePerformed = true;
            return true;
        } else
        {
            return false;
        }
    }

    protected boolean isSuicideDue()
    {
        if (suicideHeld && !suicidePerformed)
        {
            suicidePerformed = true;
            return true;
        } else
        {
            return false;
        }
    }

    //reset the switch when the button is released
    protected void releaseReviveButton()
    {
        reviveHeld = false;
        revivePerformed = false;
    }

    protected void releaseSuicideButton()
    {
        suicideHeld = false;
        suicidePerformed = false;
    }

    protected void holdReviveButton()
    {
        reviveHeld = true;
    }

    protected void holdSuicideButton()
    {
        suicideHeld = true;
    }

    protected void pullWeaponTrigger()
    {
        weaponTriggerHeld = true;
    }

    protected void releaseWeaponTrigger()
    {
        weaponTriggerHeld = false;
    }

    protected boolean isWeaponTriggerHeld()
    {
        return weaponTriggerHeld;
    }

    protected float getFacingDirection()
    {
        return player.getFacingDirection();
    }

    protected void setFacingDirection(float theta)
    {
        player.setFacingDirection(theta);
    }

    protected void rotate(float theta)
    {
        player.rotate(theta);
    }

    protected FloatCartesian getVelocity()
    {
        return velocity.clone();
    }

    protected void setVelocity(FloatCartesian velocity)
    {
        this.velocity = velocity.clone();
    }

    abstract protected void updateInputs();

    public void update()
    {
        updateInputs();

        if (isReviveDue())
        {
            player.revive();
        }

        if (player.isAlive())
        {
            player.move(velocity);
            if (isWeaponTriggerHeld())
            {
                //scheduled bullet firing?
                if (fireRateTicker == 0) {
                    player.fireBullet();
                    fireRateTicker = 20;
                } else
                {
                    fireRateTicker--;
                }
            }

            if(isSuicideDue())
            {
                player.kill();
            }
        }
    }
    public boolean hasPlayer()
    {
        return player != null;
    }

    public Player getPlayer()
    {
        if(!hasPlayer())
        {
            throw new IllegalStateException("There is no Player associated with this controller.");
        }
        return player;
    }

    public void setPlayer(Player player)
    {
        if(hasPlayer())
        {
            throw new IllegalStateException("There is already a Player associated with this controller.");
        }

        this.player = player;
    }

    public void removePlayer()
    {
        player = null;
    }

}
