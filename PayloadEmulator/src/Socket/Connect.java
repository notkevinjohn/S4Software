package Socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import Data.IPData;
import Events.CompleteConnectEvent;
import Events.ICompleteConnectEventListener;
import GUI.GetIP;
import Main.DataController;
import Parcers.IPAdder;
public class Connect extends Thread
{
	private String ip;
	private int port;
	private DataController dataController;
	private SocketAddress socketAddress;
	private int socketTimeout = 3000;
	public static javax.swing.event.EventListenerList listenerList = new javax.swing.event.EventListenerList();
	public Socket socket;
	public ArrayList<IPData> IpStorage;
	public boolean terminalIsConnected = false;
	public boolean payloadIsConnected = false;
	public String deviceName;
	public String type;
	
	public Connect()
	{
		IPAdder ipAdder = new IPAdder();
		IpStorage = ipAdder.ProcessFile();
		getIP();
	}
	public void getIP()
	{
		
		new GetIP(IpStorage);
		addCompleteConnectEventListener(new ICompleteConnectEventListener()							
		{			
			public void completeConnectEventHandler(CompleteConnectEvent event) 
			{
				ip = event.ip;
				port = event.port; 
				deviceName = event.name;
				type = event.type;
				connect();
			}
		});
	}
	
	public void connect()
	{
		try 
		{
		 socketAddress = new InetSocketAddress(ip, port);
		 socket = new Socket();
		 socket.connect(socketAddress, socketTimeout);
		} 
		catch (UnknownHostException e1) 
		{
		} 
		catch (IOException e1) 
		{	
			new GetIP(IpStorage);
		}
		if(socket.isConnected())
		{
		dataController = new DataController();
		dataController.Initilize(socket, deviceName,type);
		}
	}
	


	
	public static void addCompleteConnectEventListener (ICompleteConnectEventListener completeConnectEventListener)
	{
		listenerList.add(ICompleteConnectEventListener.class, completeConnectEventListener);
	}
	public static void removeCompleteConnectEventListener (ICompleteConnectEventListener completeConnectEventListener)
	{
		listenerList.remove(ICompleteConnectEventListener.class, completeConnectEventListener);
	}

}
