package trig.game.state;

import trig.game.state.login.LoggedInState;
import trig.game.state.login.LoggedOutState;
import trig.game.state.login.LoginState;

/**
 * The LoginMachine holds whether the system is in the login-state, or whether
 * the user has been accepted and allowed access to a resource.
 * 
 * Note: the LoginMachine starts off in the logged-out state.
 * 
 * 
 * @author brody
 *
 */

public class LoginMachine extends BasicStateMachine 
{
	private LoginState loggedIn;
	private LoginState loggedOut;
	
	public LoginMachine()
	{
		loggedIn =  new LoggedInState();
		loggedOut = new LoggedOutState();
		
		this.setCurrentState(loggedOut);
	}
	
	public void login(String username, String password)
	{
		((LoginState) currentState).login(username, password);
	}
	
	public void logout(String username)
	{
		((LoginState) currentState).logout(username);
	}
}
