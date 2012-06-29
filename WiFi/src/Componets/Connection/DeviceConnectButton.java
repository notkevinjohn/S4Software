package Componets.Connection;

import javax.swing.JButton;
import javax.swing.JComboBox;

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
	
	public void setActionListener (SendName sendName,  JComboBox<String> payloadListComboBox)
	{
		if(!actionListenerSet)
		{
			this.addActionListener(new DeviceConnectButtonActionListener(sendName ,payloadListComboBox));
			actionListenerSet = true;
		}
	}
}