package Componets.Connection;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JComboBox;
import Data.IPData;
import GUI.EditIPSrc;
import GUI.GetIP;
import Parcers.FileUpdater;

public class IPOKActionListener implements ActionListener
{
	private ArrayList<IPData> IPStorage;
	private EditIPSrc editIPSrc;
	private GetIP getIP;
	private JComboBox<String> TCPcomboBox;
	
	public IPOKActionListener(EditIPSrc editIPSrc, GetIP getIP, JComboBox<String> TCPcomboBox, ArrayList<IPData> IPStorage)
	{
		this.editIPSrc = editIPSrc;
		this.IPStorage = IPStorage;
		this.getIP = getIP;
		this.TCPcomboBox = getIP.TCPcomboBox;
	}
	
	public void actionPerformed(ActionEvent arg0) 
	{
		getIP.IPStorage = IPStorage;
		TCPcomboBox.removeAllItems();
		
		for(int i = 0; i < IPStorage.size(); i++)
		{
			TCPcomboBox.addItem(IPStorage.get(i).name);
		}
		
		editIPSrc.frame.setVisible(false);
		getIP.frame.setVisible(true);
		new FileUpdater(IPStorage);
	 }
}
