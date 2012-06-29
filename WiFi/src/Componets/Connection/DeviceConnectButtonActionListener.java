package Componets.Connection;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import Socket.SendName;

public class DeviceConnectButtonActionListener implements ActionListener 
{
	private JComboBox<String> payloadListComboBox;
	private SendName sendName;
	public DeviceConnectButtonActionListener(SendName sendName, JComboBox<String> payloadListComboBox)
	{
		this.payloadListComboBox = payloadListComboBox;
		this.sendName = sendName;
	}

	public void actionPerformed(ActionEvent arg0) 
	{
		if(payloadListComboBox.getSelectedItem() != null)
		{
			String sendString = "DeviceName" + payloadListComboBox.getSelectedItem().toString();
			sendName.TXName(sendString);
			System.out.println(sendString);
		}
		else
		{
			System.out.println("Select A device!");
		}
	}
}
