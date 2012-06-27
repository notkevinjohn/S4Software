package Data;

import java.io.IOException;
import java.net.Socket;

import IOStream.GetStreamIn;

public class GetPayloadName
{
	public GetStreamIn getStreamIn;
	private int available = 0;
	public String streamInString;
	public String DeviceName;
	private boolean deviceNameSet = true;
	
	public String getPName(Socket socket)
	{
		getStreamIn = new GetStreamIn();
		
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
				 System.out.print(streamInString);
				 if(streamInString.startsWith("Pong"))
				 {
					 DeviceName = streamInString.substring(4);
					 deviceNameSet = false;
					 
				 }
			}
		}
		return DeviceName;
	}
}
