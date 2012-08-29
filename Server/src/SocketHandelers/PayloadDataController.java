package SocketHandelers;

import java.io.IOException;
import java.net.Socket;
import java.util.Vector;

import Data.PayloadData;
import Events.CompletePayloadTXEvent;
import Events.ICompletePayloadTXEventListener;
import IOStream.GetStreamIn;
import IOStream.SendStreamOut;
import Main.Controller;

public class PayloadDataController extends Thread
{
	public Vector<PayloadData> payloadDataVector;
	public PayloadData payloadData;
	private Socket socket;
	private int available = 0;
	private long pingLastReadTime = System.currentTimeMillis();
	private SendStreamOut streamOut;
	private String streamInString;
	private GetStreamIn getStreamIn;
	private long timeout = 180000; //3 min disconect time
	private Controller controller;
	private String deviceName;
	private boolean payloadConnected = true;
	
	
	public PayloadDataController(Socket socket, Controller controller, String deviceName)
	{
		this.socket = socket;
		this.deviceName = deviceName;
		this.controller = controller;
		getStreamIn = new GetStreamIn();
		streamOut = new SendStreamOut();
		streamOut.attachSocket(socket);
		payloadDataVector = new Vector<PayloadData>();
		payloadData = new PayloadData();
		this.start();
	}
	
	public void run() 
	{
		while(payloadConnected)
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
				  System.out.println(streamInString);
				  pingLastReadTime = System.currentTimeMillis();
				  
				  if(streamInString.startsWith("$"))
				  {
					  payloadData.gpsData = streamInString;
				  }
				  else
				  {
					  payloadData.scienceData = streamInString;
					  payloadData.timeStamp = System.currentTimeMillis();
					  payloadDataVector.addElement(payloadData);
					  payloadData = new PayloadData();
				  }
				 
				  if(controller.terminalDataList != null)
				  {
					for(int j = 0; j < controller.terminalDataList.size(); j++)
					{
						if(controller.terminalDataList.get(j).payloadDeviceName.equals(deviceName))
						{
							CompletePayloadTXEvent complete = new CompletePayloadTXEvent(this,j,streamInString); // 0 is the first terminal need to assign this!!! create list and loop through?
							Object[] listeners = Controller.listenerList.getListenerList(); 
							for (int i=0; i<listeners.length; i+=2) 
							{
							    if (listeners[i]==ICompletePayloadTXEventListener.class)
							    {
							    	((ICompletePayloadTXEventListener)listeners[i+1]).CompleteTXEventHandler(complete);
							    }
							} 
						}
				   		
					}
				  }	 
			}

			if(Disconnected())
			{
				System.out.print(deviceName);
				System.out.print("disconnected");
				payloadConnected = false;
			}
			try { Thread.sleep(10); } catch(InterruptedException e) { }
		}
	}
	
	public void StreamOut(String sendText)
	{
		streamOut.streamOut(sendText);
	}
	
	public boolean Disconnected()
	{
		return (System.currentTimeMillis() - pingLastReadTime) > timeout;
	}
}
