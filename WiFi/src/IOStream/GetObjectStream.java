package IOStream;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Vector;
import Data.TerminalPayloadList;

public class GetObjectStream 
{
	private Socket socket;
	private  ObjectInputStream objectInputStream; 
	public Vector<TerminalPayloadList> payloadListVector;
	
	public GetObjectStream(Socket _socket)
	{
		socket = _socket;
	}
	

	@SuppressWarnings("unchecked")
	public  Vector<TerminalPayloadList> getObject(Socket _socket)	
	{
		try 
		{
			objectInputStream = new ObjectInputStream(socket.getInputStream());
			
			try 
			{
				//if(objectInputStream. available() >0)
				//{
					payloadListVector = (Vector<TerminalPayloadList>)objectInputStream.readUnshared();
			//	}
				
			} catch (ClassNotFoundException e) 
			{
				e.printStackTrace();
			}
			
			
	    } 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return payloadListVector;
	}
}

