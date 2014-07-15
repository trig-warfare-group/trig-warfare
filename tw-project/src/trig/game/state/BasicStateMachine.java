package trig.game.state;

import java.awt.Graphics2D;

/**
 * The BasicStateMachine holds basic-states of the game, such as whether it in the game-state,
 * or the login state, or some other state.
 * 
 * Each state is required to handle events for the state.
 * For example, the login-state, will have to handle a login-event, initiated by the login listener.
 *  
 * 
 * @author brody
 *
 */

public abstract class BasicStateMachine
{
	protected State currentState;

	public void setCurrentState(State state) 
	{
		currentState = state;
	}
	
	public State getCurrentState()
	{
		return currentState;
	}

	/**
	 * The machine must have a way of updating its states.
	 */
	public abstract void update();
	
	/**
	 * The machine will need a way of displaying its status. 
	 */
	public abstract void render(Graphics2D g);
}
