package trig.game.entity;

import trig.game.engine.GameEngine;

/**
 * For classes that take action periodically on game ticks or frames or something?
 * @author marcos
 */
public interface Automata extends Entity
{
    abstract public void update(GameEngine engine); //needs some data about engine (for now, possibly not later? (move function to the engine maybe? but how would you impliment it for AI then, without events?)
}
