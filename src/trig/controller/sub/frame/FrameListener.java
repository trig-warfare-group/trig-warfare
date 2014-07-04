package trig.controller.sub.frame;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

public class FrameListener implements WindowListener
{

	@Override
	public void windowActivated(WindowEvent e) 
	{

	}

	@Override
	public void windowClosed(WindowEvent e) 
	{

	}

	@Override
	public void windowClosing(WindowEvent e) 
	{
		JFrame f = (JFrame) e.getComponent();
		f.dispose();
		
		System.exit(0);
	}

	@Override
	public void windowDeactivated(WindowEvent e) 
	{

	}

	@Override
	public void windowDeiconified(WindowEvent e) 
	{
	
	}

	@Override
	public void windowIconified(WindowEvent e) 
	{
		
	}

	@Override
	public void windowOpened(WindowEvent e) 
	{	
		
	}

}
