package Componets.Connection;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.event.EventListenerList;

import Data.IPData;
import Events.CompleteConnectEvent;
import Events.CompleteConnectEventListener;
import Events.ICompleteConnectEventListener;
import Socket.Connect;

public class ConnectActionListener implements ActionListener
{
	private ArrayList<IPData> IPStorage;
	private String ip;
	private int port;
	private JComboBox<String> TCPcomboBox;
	private JComboBox<String> simcomboBox;
	private JFrame frame;
	public static EventListenerList listenerList = new EventListenerList();
	
	public ConnectActionListener (JButton connectButton, ArrayList<IPData> IPStorage, JFrame frame, JComboBox<String> TCPcomboBox, JComboBox<String> simcomboBox)
	{
		this.IPStorage = IPStorage;
		this.TCPcomboBox = TCPcomboBox;
		this.simcomboBox =simcomboBox;
		this.frame = frame;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		port = IPStorage.get(TCPcomboBox.getSelectedIndex()).port;
		ip = IPStorage.get(TCPcomboBox.getSelectedIndex()).ip;
		String name = IPStorage.get(TCPcomboBox.getSelectedIndex()).name;
		String type = simcomboBox.getSelectedItem().toString();
		frame.setVisible(false);
		
		CompleteConnectEvent complete = new CompleteConnectEvent(this,ip,port,name,type);
		Object[] listeners = Connect.listenerList.getListenerList(); 
   		for (int i=0; i<listeners.length; i+=2) 
   		{
             if (listeners[i]==ICompleteConnectEventListener.class)
             {
                 ((ICompleteConnectEventListener)listeners[i+1]).completeConnectEventHandler(complete);
             }
        } 		 
	}
	
	public static void addCompleteConnectEventListener (CompleteConnectEventListener completeConnectEventListener)
	{
		listenerList.add(ICompleteConnectEventListener.class, completeConnectEventListener);
	}
}
