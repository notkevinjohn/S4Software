package Componets.Connection;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import Data.IPData;
import GUI.GetIP;

public class EditIPButton extends JButton 
{
	private static final long serialVersionUID = -1021920621979703549L;
	private boolean actionListenerSet = false;
	private GetIP getIP;
	private JComboBox<String> TCPcomboBox;
	
	
	public EditIPButton(GetIP getIP,JComboBox<String> TCPcomboBox, ArrayList<IPData> IPStorage)
	{
		super("Edit");	
		this.setName("Edit");
		this.getIP = getIP;
		this.TCPcomboBox = TCPcomboBox;
	}
	
	public void setActionListener (ArrayList<IPData> IPStorage)
	{
		if(!actionListenerSet)
		{
			this.addActionListener(new EditIPActionListener(this, getIP, TCPcomboBox, IPStorage));
			actionListenerSet = true;
		}
	}
	
}

