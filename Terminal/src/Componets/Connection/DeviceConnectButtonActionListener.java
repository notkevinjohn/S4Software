package Componets.Connection;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import GUI.SendConnectionName;
import Socket.SendName;

public class DeviceConnectButtonActionListener implements ActionListener 
{
	private JComboBox<String> payloadListComboBox;
	private SendName sendName;
	private SendConnectionName sendConnectionName;
	
	public DeviceConnectButtonActionListener(SendName sendName, JComboBox<String> payloadListComboBox, SendConnectionName sendConnectionName)
	{
		this.payloadListComboBox = payloadListComboBox;
		this.sendName = sendName;
		this.sendConnectionName = sendConnectionName;
	}

	public void actionPerformed(ActionEvent arg0) 
	{
		if(payloadListComboBox.getSelectedItem() != null)
		{
			String sendString = "DeviceName" + payloadListComboBox.getSelectedItem().toString();
			sendName.TXName(sendString);
			System.out.println(sendString);
			sendConnectionName.frame.setVisible(false);
		}
		else
		{
			System.out.println("Select A device!");
		}
	}
}
