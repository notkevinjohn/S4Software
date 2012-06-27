package Componets.Connection;

import java.awt.CheckboxGroup;

import javax.swing.JButton;

import Socket.SendName;

public class DeviceConnectButton extends JButton
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7584046511221040196L;
	private boolean actionListenerSet = false;
	
	public DeviceConnectButton()
	{
		super("Connect");	
		this.setName("ConnectDevice");

	}
	
	public void setActionListener (SendName sendName, CheckboxGroup checkBoxGroup)
	{
		if(!actionListenerSet)
		{
			this.addActionListener(new DeviceConnectButtonActionListener(sendName ,checkBoxGroup));
			actionListenerSet = true;
		}
	}
}