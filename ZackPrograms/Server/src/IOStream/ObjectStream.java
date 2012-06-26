package IOStream;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import Data.Payload;

public class ObjectStream
{
	public Vector<Payload> payloadList;
	
	public ObjectStream(Vector<Payload> payloadList)
	{
		this.payloadList = payloadList;
	}
	public void sendObject(Socket socket)
	{
		ObjectOutputStream output;
		try {
			output = new ObjectOutputStream(socket.getOutputStream());
			output.writeObject(payloadList);
			output.flush();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}

}
