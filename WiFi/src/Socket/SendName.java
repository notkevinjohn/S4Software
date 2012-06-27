package Socket;

import java.io.IOException;
import java.net.Socket;

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

	public boolean sendName(Socket socket)
	{
		getStreamIn = new GetStreamIn();
		sendStreamOut = new SendStreamOut();
		sendStreamOut.attachSocket(socket);
		
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
					 sendStreamOut.streamOut("PongSSU-01");
				 }
				 else if (streamInString.equals("@"))
				 {
					 deviceNameSet = false;
					 
				 }
			}
		}
		return true;
	}
}



