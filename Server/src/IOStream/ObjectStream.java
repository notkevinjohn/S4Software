package IOStream;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;
import Data.TerminalPayloadList;

public class ObjectStream
{
	public Vector<TerminalPayloadList> payloadListVector;
	public ObjectOutputStream objectOutputStream;
	public ObjectStream(Vector<TerminalPayloadList> payloadListVector)
	{
		this.payloadListVector = payloadListVector;
		
	}
	public void sendObject(Socket socket)
	{
		try {
			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			//Object obj = (Object)payloadList;
			objectOutputStream.writeUnshared(payloadListVector);
			objectOutputStream.flush();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		

	}

}
