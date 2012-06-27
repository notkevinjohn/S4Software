package Componets.Connection;

import java.awt.CheckboxGroup;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Socket.SendName;

public class DeviceConnectButtonActionListener implements ActionListener 
{
	private CheckboxGroup checkBoxGroup;
	private SendName sendName;
	public DeviceConnectButtonActionListener(SendName sendName, CheckboxGroup checkBoxGroup)
	{
		this.checkBoxGroup = checkBoxGroup;
		this.sendName = sendName;
	}

	public void actionPerformed(ActionEvent arg0) 
	{
		if(checkBoxGroup.getSelectedCheckbox() != null)
		{
			String sendString = "DeviceName" + checkBoxGroup.getSelectedCheckbox().getLabel();
			sendName.TXName(sendString);
			System.out.println(sendString);
		}
		else
		{
			System.out.println("Select A device!");
		}
	}
}
