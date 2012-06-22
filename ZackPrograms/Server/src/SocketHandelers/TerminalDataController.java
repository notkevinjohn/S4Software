package SocketHandelers;

import java.io.IOException;
import java.net.Socket;

import Events.CompleteTerminalTXEvent;
import Events.ICompleteTerminalTXEventListener;
import IOStream.GetStreamIn;
import IOStream.SendStreamOut;
import Main.Controller;

public class TerminalDataController extends Thread
{
	private Socket socket;
	public SendStreamOut streamOut;
	public String streamInString;
	public GetStreamIn getStreamIn;
	private int available = 0;
	public long lastReadTime = System.currentTimeMillis();
	public long pingLastReadTime = System.currentTimeMillis();
	public long lastPingTime = System.currentTimeMillis();
	public long timeout = 1000;
	
	public TerminalDataController(Socket socket)
	{
		this.socket = socket;
		getStreamIn = new GetStreamIn();
		streamOut = new SendStreamOut();
		streamOut.attachSocket(socket);
		this.start();
		streamOut.streamOut("Ping"); // Ping outgoing
	}
	
	public void run() 
	{
		while(true)
		{
			try 
			{
				available = socket.getInputStream().available();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			
			if(available > 0)
			{
				  streamInString = getStreamIn.StreamIn(socket);
				  if(!streamInString.equals("Pong"))  // Pong incoming
				  {			  
					  CompleteTerminalTXEvent complete = new CompleteTerminalTXEvent(this,0,streamInString); // 0 is the first terminal need to assign this!!!
						Object[] listeners = Controller.listenerList.getListenerList(); 
				   		for (int i=0; i<listeners.length; i+=2) 
				   		{
				             if (listeners[i]==ICompleteTerminalTXEventListener.class)
				             {
				                 ((ICompleteTerminalTXEventListener)listeners[i+1]).CompleteTXEventHandler(complete);
				             }
				        } 	
					  lastReadTime = System.currentTimeMillis();
				  }
				  else
				  {
					  pingLastReadTime = System.currentTimeMillis();
				  }
			}
			
			Ping();
			
			//if(Disconnected())
			//{
			//	System.out.println("Disconnected!"); // Add reconnect code
		//	}
		}
	}
	
	public void StreamOut(String sendText)
	{
		streamOut.streamOut(sendText);
	}
	
	public boolean Disconnected()
	{
		return (System.currentTimeMillis() - lastReadTime) > timeout;
	}
		
	public void Ping()
	{
		if((System.currentTimeMillis() - pingLastReadTime) > 3000 && (System.currentTimeMillis() - lastPingTime) > 3000)
		{
			streamOut.streamOut("Ping"); // Ping outgoing
			lastPingTime = System.currentTimeMillis();
		}
	
	}

}