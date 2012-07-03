package IOStream;

import java.io.IOException;
import java.net.Socket;

public class HandShake 
{
	private SendStreamOut sendStreamOut;
	private GetStreamIn getStreamIn;
	private String streamInString;
	private Socket socket;
	private String deviceName;
	private int available;
	private long lastReadTime = System.currentTimeMillis();;
	private boolean boolStream = false;
	
	public HandShake(SendStreamOut sendStreamOut,GetStreamIn getStreamIn,Socket socket, String deviceName)
	{
		this.sendStreamOut = sendStreamOut;
		this.getStreamIn = getStreamIn;
		this.socket = socket;
		this.deviceName = deviceName;
	}
	
	public boolean isHandShakeComplete()
	{
		if((System.currentTimeMillis() - lastReadTime) > 2000)
		{
		String tempString = "DeviceName" + deviceName;
		sendStreamOut.streamOut(tempString);
		System.out.print(tempString);
		lastReadTime = System.currentTimeMillis();
		}
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
			  if(streamInString.contains("@"))
			  {
				  boolStream = true;
			  }
			  
		}
	return boolStream;
	}
}
