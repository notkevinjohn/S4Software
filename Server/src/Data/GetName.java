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
					 deviceNameSet = false;
					 sendStreamOut.streamOut("@");
				 }
				 else if (streamInString.startsWith("Refresh"))
				 {
					sendStreamOut.streamOut("Refresh");
					ObjectStream objectStream =  new ObjectStream(payloadListVector);
					objectStream.sendObject(socket);
				 }
			}
			Ping();
			try { Thread.sleep(10); } catch(InterruptedException e) { /* we tried */}
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
