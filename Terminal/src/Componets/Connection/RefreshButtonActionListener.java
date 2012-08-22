package Componets.Connection;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Socket.SendName;

public class RefreshButtonActionListener implements ActionListener 
{
	private SendName sendName;
	public RefreshButtonActionListener(SendName sendName)
	{
		this.sendName = sendName;
	}

	public void actionPerformed(ActionEvent arg0) 
	{
		sendName.TXName("Refresh");
	}
}
