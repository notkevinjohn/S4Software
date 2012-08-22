package Componets.Connection;

import javax.swing.JButton;

import Socket.SendName;

public class RefreshButton extends JButton
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8621913791786754816L;
	private boolean actionListenerSet = false;

	public RefreshButton()
	{
		super("Refresh");	
		this.setName("Refresh");

	}
	
	public void setActionListener (SendName sendName)
	{
		if(!actionListenerSet)
		{
			this.addActionListener(new RefreshButtonActionListener(sendName));
			actionListenerSet = true;
		}
	}
}