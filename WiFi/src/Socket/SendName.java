package Socket;

import java.io.IOException;
import java.net.Socket;
import java.util.Vector;

import Data.Payload;
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
	public String DeviceName;
	private boolean deviceNameSet = true;
	private GetObjectStream getObjectStream;
	public  Vector<Payload> payloadList;
	public SendConnectionName sendConnectionName;
	public boolean sendName(Socket socket)
	{
		getStreamIn = new GetStreamIn();
		sendStreamOut = new SendStreamOut();
		sendStreamOut.attachSocket(socket);
		
		sendConnectionName = new SendConnectionName(this);
		sendStreamOut.streamOut("Refresh");
		getObjectStream = new GetObjectStream(socket);
		
		while(deviceNameSet)
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
				 if(streamInString.equals("Ping"))
				 {
					 sendStreamOut.streamOut("Pong");
				 }
				 else if (streamInString.equals("@"))
				 {
					 deviceNameSet = false;
					 
				 }
				 else if(streamInString.equals("Refresh"))
				 {
					 sendConnectionName.refreshPayloadList(getObjectStream.getObject(socket));
				 }
			}
		}
		return true;
	}
	
	public void TXName(String sendString)
	{
		 sendStreamOut.streamOut(sendString);
	}
}



