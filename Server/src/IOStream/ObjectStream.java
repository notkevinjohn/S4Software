package IOStream;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;
import Data.TerminalPayloadList;

public class ObjectStream
{
	public Vector<TerminalPayloadList> payloadListVector;
	
	public ObjectStream(Vector<TerminalPayloadList> payloadListVector)
	{
		this.payloadListVector = payloadListVector;
		
	}
	public void sendObject(Socket socket)
	{
		ObjectOutputStream output;
		try {
			output = new ObjectOutputStream(socket.getOutputStream());
			//Object obj = (Object)payloadList;
			output.writeUnshared(payloadListVector);
			output.flush();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}

}
