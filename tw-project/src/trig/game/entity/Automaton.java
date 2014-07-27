package trig.game.entity;

import trig.game.engine.GameEngine;

/**
 * For classes that take action periodically on game ticks or frames or something?
 * @author marcos
 */
public interface Automaton extends Entity
{
    abstract public void update();
}
