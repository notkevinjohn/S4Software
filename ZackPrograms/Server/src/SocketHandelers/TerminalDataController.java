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
	private int available = 0;
	public SendStreamOut streamOut;
	public String streamInString;
	public GetStreamIn getStreamIn;
	public long lastReadTime = System.currentTimeMillis();
	public long pingLastReadTime = System.currentTimeMillis();
	public long lastPingTime = System.currentTimeMillis();
	public long timeout = 1000;
	public boolean terminalConnected = false;
	public String payloadConnectIP = "";
	public boolean isPayloadIPSet = false;
	public int payloadElementNubmer;
	
	public TerminalDataController(Socket socket, int payloadElementNubmer)
	{
		this.socket = socket;
		this.payloadElementNubmer = payloadElementNubmer;
		
		getStreamIn = new GetStreamIn();
		streamOut = new SendStreamOut();
		streamOut.attachSocket(socket);
		
		this.start();
		streamOut.streamOut("@"); // Complete handshake
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
				  

				  if(streamInString.equals("Pong"))
				  {
					  pingLastReadTime = System.currentTimeMillis();
				  }
				  else
				  {
					  CompleteTerminalTXEvent complete = new CompleteTerminalTXEvent(this,payloadElementNubmer,streamInString); 
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
			}
			
			Ping();
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