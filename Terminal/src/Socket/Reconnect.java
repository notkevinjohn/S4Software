package Socket;

import java.awt.Color;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import Main.DataController;


public class Reconnect{
	private String ip;
	private int port;
	private DataController dataController;
	private boolean isConnected = false;
	private int restartCycle = 0;
	private int socketTimeout = 5000;
	private SocketAddress socketAddress;
	public SimpleAttributeSet green = new SimpleAttributeSet();
	public Socket socket;
	
	public Reconnect(DataController dataController, String ip, int port)
	{
		this.ip = ip;
		this.port = port;
		this.dataController = dataController;
		StyleConstants.setForeground(green, new Color(0,64,0));
	}
	
	public void AttachSocket()
	{
		dataController.updateText("Attempting Reconnect...\n" , green);
	
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
				dataController.updateText("Failed...\n" , green);
				restartCycle++;	
				if(restartCycle < 3)
				{
					AttachSocket();
				}
				else
				{
					dataController.updateText("Unable To Connect...\n" , green);
				}
			}
		}
	public Socket socketLoop()
	{
		while(isConnected == false && restartCycle < 2)
		{
			AttachSocket();
			if(socket.isBound())
			{
				dataController.updateText("Reconnected! \n\n" , green);
				dataController.lastReadTime = System.currentTimeMillis();
				isConnected = true;
			}
		}
		return socket;
	}
	

}
