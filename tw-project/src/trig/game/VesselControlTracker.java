package trig.game;

import trig.game.engine.GameEngine;
import trig.utility.math.vector.FloatCartesian;

/**
 * Created by marcos on 10/11/2014.
 */
abstract class VesselControlTracker extends Player
{


    //revive and suicide only occur once per press, so forget about it being performed once it is released.
    private boolean firing, revivePerformed, suicidePerformed, reviveHeld, suicideHeld;

    public VesselControlTracker(GameEngine engine, int playerID)
    {
        super(engine, playerID);
    }

    public boolean isReviveDue()
{
    if(reviveHeld && !revivePerformed)
    {
        revivePerformed = true;
        return true;
    }
    else
    {
        return false;
    }
}

    public boolean isSuicideDue()
    {
        if(suicideHeld && !suicidePerformed)
        {
            suicidePerformed = true;
            return true;
        }
        else
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

    public boolean isFiring()
    {
        return firing;
    }

    protected void setFiring(boolean firing)
    {
        this.firing = firing;
    }
}
