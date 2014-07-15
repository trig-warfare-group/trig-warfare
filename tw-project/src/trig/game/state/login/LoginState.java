package trig.game.state.login;

import trig.game.state.State;

public abstract class LoginState extends State
{
	public abstract void login(String username, String password);
	
	public abstract void logout(String username);

}
