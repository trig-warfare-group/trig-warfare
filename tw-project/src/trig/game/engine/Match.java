package trig.game.engine;

import trig.client.Player;
import trig.client.PlayerManager;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by marcos on 13/11/2014.
 */
public class Match extends GameEngine //not a fully complete class, really just sets up the way for players to get access to an engine.
{
    private PlayerManager playerManager;
    public Match(PlayerManager playerManager)
    {
        this.playerManager = playerManager;
        playerManager.attach(this);
    }

    public void update()
    {
        playerManager.updateGame();
        super.update();
    }
}
