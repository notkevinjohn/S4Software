package Componets.Connection;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import Data.IPData;
import GUI.EditIPSrc;
import GUI.GetIP;

public class EditIPActionListener implements ActionListener
{

	private ArrayList<IPData> IPStorage;
	private GetIP getIP;
	private JComboBox<String> TCPcomboBox;
	
	public EditIPActionListener(JButton editIPButton, GetIP getIp, JComboBox<String> TCPcomboBox, ArrayList<IPData> IPStorage) 
	{
		this.IPStorage = IPStorage;
		this.getIP = getIp;
		this.TCPcomboBox = TCPcomboBox;
		
	}
	
	public void actionPerformed(ActionEvent arg0) 
	{
		new EditIPSrc(getIP,TCPcomboBox, IPStorage);
		getIP.frame.setVisible(false);
	} 	
}
