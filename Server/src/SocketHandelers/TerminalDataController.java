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
	public Socket socket;
	private int available = 0;
	public SendStreamOut streamOut;
	public String streamInString;
	public GetStreamIn getStreamIn;
	public long lastReadTime = System.currentTimeMillis();
	public long pingLastReadTime = System.currentTimeMillis();
	public long lastPingTime = System.currentTimeMillis();
	public long timeout = 5000;
	public boolean terminalConnected = true;
	public String payloadConnectIP = "";
	public boolean isPayloadIPSet = false;
	public int payloadElementNubmer;
	public String payloadDeviceName;
	public Controller controller;
	
	public TerminalDataController(Socket socket, int payloadElementNubmer, String payloadDeviceName, Controller controller)
	{
		this.controller = controller;
		this.payloadDeviceName = payloadDeviceName;
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
		while(terminalConnected)
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
				  pingLastReadTime = System.currentTimeMillis();
				  
				  if(streamInString.startsWith("payloadUpdateRequest."))
				  {
					  String payloadRequested = streamInString.substring(21);
					  payloadUpdateRequested(payloadRequested);
				  }

				  if(streamInString.equals("Pong"))
				  {
					  pingLastReadTime = System.currentTimeMillis();
				  }
//				  else
//				  {
//					  CompleteTerminalTXEvent complete = new CompleteTerminalTXEvent(this,payloadElementNubmer,streamInString); 
//						Object[] listeners = Controller.listenerList.getListenerList(); 
//				   		for (int i=0; i<listeners.length; i+=2) 
//				   		{
//				             if (listeners[i]==ICompleteTerminalTXEventListener.class)
//				             {
//				                 ((ICompleteTerminalTXEventListener)listeners[i+1]).CompleteTXEventHandler(complete);
//				             }
//				        }
//				   	pingLastReadTime = System.currentTimeMillis();
//				  }
			}
			
			Ping();
			if(Disconnected())
			{
				System.out.print(payloadDeviceName);
				System.out.println(" Terminal Disconected!!!");
				terminalConnected = false;
			}
		
			try { Thread.sleep(10); } catch(InterruptedException e) { /* we tried */}
		}
	}
	
	public void payloadUpdateRequested(String payloadRequested)
	{
		controller.terminalRequestForUpdate(this, payloadRequested);
	}
	public void StreamOut(String sendText)
	{
		streamOut.streamOut(sendText);
	}
	
	public boolean Disconnected()
	{

		return false; //(System.currentTimeMillis() - pingLastReadTime) > timeout;

	}
		
	public void Ping()
	{
		if((System.currentTimeMillis() - pingLastReadTime) > 1000 && (System.currentTimeMillis() - lastPingTime) > 1000)
		{
			streamOut.streamOut("Ping"); // Ping outgoing
			lastPingTime = System.currentTimeMillis();
		}
	
	}

}