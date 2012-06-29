package Componets.Connection;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import Data.IPData;

public class ConnectButton extends JButton 
{
	private static final long serialVersionUID = -6429240007417074059L;
	private boolean actionListenerSet = false;
	private JComboBox<String> TCPcomboBox;
	private JFrame frame;
	private JComboBox<String> simcomboBox;
	
	public ConnectButton(JFrame frame, JComboBox<String> TCPcomboBox, JComboBox<String> simcomboBox)
	{
		super("Connect");	
		this.setName("ConnectButton");
		this.TCPcomboBox = TCPcomboBox;
		this.frame = frame;
		this.simcomboBox = simcomboBox;
	}
	
	public void setActionListener (ArrayList<IPData> IPStorage)
	{
		if(!actionListenerSet)
		{
			this.addActionListener(new ConnectActionListener(this,IPStorage,frame, TCPcomboBox,simcomboBox));
			actionListenerSet = true;
		}
	}
}