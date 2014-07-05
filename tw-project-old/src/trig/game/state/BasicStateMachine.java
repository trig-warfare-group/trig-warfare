package trig.game.state;

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
}
