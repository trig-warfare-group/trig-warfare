package trig.game.state.login;

/**
 * This class asserts that you are logged-out, and deals with the events States appropriately.
 * 
 * @author brody
 *
 */

public class LoggedOutState extends LoginState
{
	@Override
	public void login(String username, String password) 
	{
		if(username.equals("asd") && password.equals("asd"))
		{
			System.out.println("You are logging in...");
			this.transitionState(new LoggedInState());
		}
		else
		{
			System.out.println("Incorrect username or password.");
		}
	}

	@Override
	public void logout(String username) 
	{
		try 
		{
			throw new Exception("[LoggedOutState] - You are already logged out!");
		} 
			catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

}
