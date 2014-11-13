package trig.game.state;

import java.awt.Graphics2D;

import trig.game.state.login.LoginMachine;
import trig.view.GameView;
//CHANGE
/**
 * The StateManager will hold all the over-arching states, managing them.
 * 
 * @author brody
 *
 */
//public class StateManager
//{
//	private GameView gameView;
//	private LoginMachine loginMachine;
//	//private GameMachine gameMachine;
//	private BasicStateMachine currentMachine;
//
//	public StateManager(GameView view)
//	{
//		this.gameView = view;
//	}
//
//    public void init()
//    {
//        this.loginMachine = new LoginMachine(gameView.constructLoginView());
//        currentMachine = loginMachine;
//    }
//
//
//	public void login(String username)
//	{
//		loginMachine.login(username, "");
//	}
//
//	public void update()
//	{
//		currentMachine.update();
//	}
//
//	public void render(Graphics2D g)
//	{
//		currentMachine.render(g);
//	}
//}
