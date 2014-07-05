package trig.game.state.login;

/**
 * The LoggedInState asserts, that currently the machine is logged in.
 * 
 * @author brody
 *
 */

public class LoggedInState extends LoginState 
{
	
	public void login(String username, String password)
	{
		try 
		{
			throw new Exception("[LoggedInState] - you are already logged in");
		}
			catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void logout(String username)
	{
		System.out.println("You are logging out...");
		this.transitionState(new LoggedOutState());
	}
}
