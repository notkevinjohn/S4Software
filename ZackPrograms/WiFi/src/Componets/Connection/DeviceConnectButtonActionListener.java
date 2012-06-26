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
			sendName.TXName(checkBoxGroup.getSelectedCheckbox().getLabel());
			System.out.println(checkBoxGroup.getSelectedCheckbox().getLabel());
		}
		else
		{
			System.out.println("Select A device!");
		}
	}
}
