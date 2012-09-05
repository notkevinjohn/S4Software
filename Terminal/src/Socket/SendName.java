package Socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Vector;
import Data.TerminalPayloadList;
import GUI.SendConnectionName;
import IOStream.GetObjectStream;
import IOStream.GetStreamIn;
import IOStream.SendStreamOut;

public class SendName
{
	public SendStreamOut sendStreamOut;
	private int available = 0;
	public String streamInString;
	public GetStreamIn getStreamIn;
	public String deviceName;
	private boolean deviceNameSet = false;
	public GetObjectStream getObjectStream;
	public Vector<TerminalPayloadList> payloadListVector;
	public SendConnectionName sendConnectionName;
	public ObjectInputStream objectInputStream;
	
	public boolean sendName(Socket socket)
	{
		getStreamIn = new GetStreamIn();
		sendStreamOut = new SendStreamOut();
		sendStreamOut.attachSocket(socket);
		
		getObjectStream = new GetObjectStream();
		
		getObjectStream.getObjectStream(socket);
		sendConnectionName = new SendConnectionName(this);
		
		sendStreamOut.streamOut("Refresh");
		
		
		
		while(!deviceNameSet)
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
//				 if(streamInString.equals("#"))
//				 {
//					 sendStreamOut.streamOut("Pong");
//				 }
				 if (streamInString.equals("@"))
				 {
					 deviceNameSet = true;
					 
				 }
				 else if(streamInString.equals("Refresh"))
				 { 
					 sendConnectionName.refreshPayloadList(getObjectStream.getObject());
					 objectInputStream = getObjectStream.objectInputStream;
				 }
			}
		//	try { Thread.sleep(10); } catch(InterruptedException e) { /* we tried */} This makes it mess up need to find out why
		}
		return true;
	}
	
	public void TXName(String sendString)
	{
		 sendStreamOut.streamOut(sendString);
		 if(!sendString.equals("Refresh"))
		 {
			 deviceName = sendString.substring(10);
		 }
	}
}



