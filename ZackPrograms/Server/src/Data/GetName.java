package Data;

import java.io.IOException;
import java.net.Socket;

import IOStream.GetStreamIn;
import IOStream.SendStreamOut;

public class GetName
{
	private int available = 0;	
	private boolean deviceNameSet = true;
	private long lastPingTime = System.currentTimeMillis();
	public GetStreamIn getStreamIn;
	public SendStreamOut sendStreamOut;
	public String streamInString;
	public String DeviceName;

	public String getPName(Socket socket)
	{
		sendStreamOut = new SendStreamOut();
		sendStreamOut.attachSocket(socket);
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
				 if(streamInString.startsWith("Pong"))
				 {
					 String tempName = streamInString.substring(4);
					 DeviceName = tempName.replaceAll("[\n\r]", "");
					 System.out.print(DeviceName);
					 deviceNameSet = false;
				 }
			}
			Ping();
		}
		return DeviceName;
	}
	
	public void Ping()
	{
		if((System.currentTimeMillis() - lastPingTime) > 3000) //// Fix this
		{
			sendStreamOut.streamOut("Ping"); // Ping outgoing
			lastPingTime = System.currentTimeMillis();
		}
	}
}
