package trig.game.entity.interfaces;

import trig.game.engine.GameEngine;

/**
 * For classes that take action periodically on game ticks or frames or something? Such as maybe projectiles/bullets or AI.
 * Sort of a listener-like class
 * Should probably be renamed, possibly to TickAI?
 * May not keep, idk, seemed like a reasonable way to go about it though.
 * @author marcos
 */
public interface UpdateListener
{
    abstract public void update(GameEngine engine); //needs some data about engine (for now, possibly not later? (move function to the engine maybe? but how would you impliment it for AI then, without events?)
}
