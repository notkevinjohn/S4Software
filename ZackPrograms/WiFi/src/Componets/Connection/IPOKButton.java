package Componets.Connection;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import Data.IPData;
import GUI.EditIPSrc;
import GUI.GetIP;

public class IPOKButton  extends JButton
{
	private static final long serialVersionUID = -3745957213468940230L;
	private boolean actionListenerSet = false;
	private EditIPSrc editIPSrc;
	private GetIP getIP;
	private JComboBox<String> TCPcomboBox;
	
	public IPOKButton( EditIPSrc editIPSrc, GetIP getIP, JComboBox<String> TCPcomboBox, ArrayList<IPData> IPStorage)
	{
		super("OK");	
		this.setName("OK");
		this.editIPSrc = editIPSrc;
		this.getIP = getIP;
		this.TCPcomboBox = TCPcomboBox;
	}
	
	public void setActionListener (ArrayList<IPData> IPStorage)
	{
		if(!actionListenerSet)
		{
			this.addActionListener(new IPOKActionListener(editIPSrc, getIP,TCPcomboBox, IPStorage));
			actionListenerSet = true;
		}
	}
}
