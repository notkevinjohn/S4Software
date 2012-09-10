package SocketHandelers;

import java.io.IOException;
import java.net.Socket;
import java.util.Vector;
import Data.PayloadData;
import FileWriters.PayloadLogger;
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
	private PayloadLogger payloadLogger;
	
	public PayloadDataController(Socket socket, Controller controller, String deviceName)
	{
		this.socket = socket;
		this.deviceName = deviceName;
		getStreamIn = new GetStreamIn();
		streamOut = new SendStreamOut();
		streamOut.attachSocket(socket);
		payloadDataVector = new Vector<PayloadData>();
		payloadData = new PayloadData();
		payloadLogger = new PayloadLogger();
		payloadLogger.payloadLogger(deviceName);
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
				  
				  

				if(streamInString.startsWith("$GPGGA"))
				{
					
					int scienceDataStart = streamInString.indexOf('@');
					if(scienceDataStart > 0)
					{
					    String tempGpsData = streamInString.substring(0,scienceDataStart);
					    payloadData.gpsData = tempGpsData;
						String tempScienceData = streamInString.substring(scienceDataStart);
						tempScienceData += '\r';
	
						payloadData.scienceData = tempScienceData;
						  
						payloadData.timeStamp = System.currentTimeMillis();
						payloadDataVector.addElement(payloadData);
						payloadData = new PayloadData();
						payloadLogger.recieveText(tempGpsData);
						payloadLogger.recieveText(tempScienceData);
					}
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
