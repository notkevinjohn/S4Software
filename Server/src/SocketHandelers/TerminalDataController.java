package SocketHandelers;

import java.io.IOException;
import java.net.Socket;
import IOStream.GetStreamIn;
import IOStream.SendStreamOut;
import Main.Controller;

public class TerminalDataController extends Thread
{
	private int available = 0;
	public Socket socket;
	public SendStreamOut streamOut;
	public String streamInString;
	public GetStreamIn getStreamIn;
	public boolean terminalConnected = true;
	public String payloadConnectIP = "";
	public boolean isPayloadIPSet = false;
	public String payloadDeviceName;
	public Controller controller;
	public long timeStamp = 0;
	public TerminalDataController(Socket socket, String payloadDeviceName, Controller controller)
	{
		this.controller = controller;
		this.payloadDeviceName = payloadDeviceName;
		this.socket = socket;
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
				  if(streamInString.startsWith("payloadUpdateRequest."))
				  {
					  String payloadRequested = streamInString.substring(21);

					  payloadRequested = payloadRequested.replaceAll("(\\r|\\n)", "");
					  payloadUpdateRequested(payloadRequested);
				  }
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
	

}