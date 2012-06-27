package Data;

import java.io.IOException;
import java.net.Socket;
import java.util.Vector;

import IOStream.GetStreamIn;
import IOStream.ObjectStream;
import IOStream.SendStreamOut;
import Main.Controller;

public class GetName
{
	private int available = 0;	
	private boolean deviceNameSet = true;
	private long lastPingTime = System.currentTimeMillis();
	public GetStreamIn getStreamIn;
	public SendStreamOut sendStreamOut;
	public String streamInString;
	public String DeviceName;
	public Vector<TerminalPayloadList> payloadListVector;
	
	public GetName(Controller controller) 
	{
		
		this.payloadListVector = controller.payloadListVector;
	}

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
				 System.out.println(streamInString);
				 if(streamInString.startsWith("DeviceName"))
				 {
					
					 String tempName = streamInString.substring(10);
					 DeviceName = tempName.replaceAll("[\n\r]", "");
					 System.out.print(DeviceName);
					 deviceNameSet = false;
					 
				 }
				 else if (streamInString.startsWith("Refresh"))
				 {
					 //Send block of stuff over
					sendStreamOut.streamOut("Refresh");
					System.out.println("Request Made for Payload names");
					ObjectStream objectStream =  new ObjectStream(payloadListVector);
					objectStream.sendObject(socket);
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
			sendStreamOut.streamOut("#"); // Ping outgoing
			lastPingTime = System.currentTimeMillis();
		}
	}
}
