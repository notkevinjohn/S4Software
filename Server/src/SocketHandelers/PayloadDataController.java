package SocketHandelers;

import java.io.IOException;
import java.net.Socket;
import java.util.Vector;
import Data.PayloadData;
import IOStream.GetStreamIn;
import IOStream.SendStreamOut;
import Main.Controller;

public class PayloadDataController extends Thread
{
	public Vector<PayloadData> payloadDataVector;
	public PayloadData payloadData;
	public String deviceName;
	private Socket socket;
	private int available = 0;
	private SendStreamOut streamOut;
	private String streamInString;
	private GetStreamIn getStreamIn;
	private boolean payloadConnected = true;
	
	public PayloadDataController(Socket socket, Controller controller, String deviceName)
	{
		this.socket = socket;
		this.deviceName = deviceName;
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
				  
				  if(streamInString.startsWith("$"))
				  {
					  int scienceDataStart = streamInString.indexOf('@');
					  payloadData.gpsData = streamInString.substring(1,scienceDataStart);
					  payloadData.scienceData = streamInString.substring(scienceDataStart);
					  payloadData.timeStamp = System.currentTimeMillis();
					  payloadDataVector.addElement(payloadData);
					  payloadData = new PayloadData();
				  }
			}
			try { Thread.sleep(10); } catch(InterruptedException e) { }
		}
	}
	
	public void StreamOut(String sendText)
	{
		streamOut.streamOut(sendText);
	}
}
