package trig.game.state;

/**
 * The standard definition of a State; a State when transitioning.
 * @author brody
 *
 */
public abstract class State 
{
	private BasicStateMachine gameMachine = null;
	
	/**
	 * This method will change the current state of the game-machine,
	 * to the parameter given.
	 * @param state - the State to set the game-machine.
	 */
	protected void transitionState(State state)
	{
		this.setState(state);
	}
	
	/**
	 * Privately handles the default state-transition, by setting the current State
	 * on the BasicStatemachine, informing the new State with a reference to the BasicStateMachine.
	 * Then, disallowing this State access to the BasicStateMachine.
	 * @param newState - new state for the BasicStateMachine.
	 */
	private void setState(State newState)
	{
		this.gameMachine.setCurrentState(newState);
		newState.setBasicStateMachine(gameMachine);
		gameMachine = null;
	}


	private void setBasicStateMachine(BasicStateMachine gameMachine) 
	{
		this.gameMachine = gameMachine;
	}
}
