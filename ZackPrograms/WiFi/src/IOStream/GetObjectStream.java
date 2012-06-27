package IOStream;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Vector;

import Data.Payload;

public class GetObjectStream 
{
	private Socket socket;
	private  ObjectInputStream objectInputStream; 
	public  Vector<Payload> payloadList;
	
	public GetObjectStream(Socket _socket)
	{
		socket = _socket;
	}
	

	@SuppressWarnings("unchecked")
	public  Vector<Payload> getObject(Socket _socket)	
	{
		try 
		{
			objectInputStream = new ObjectInputStream(socket.getInputStream());
			
			try 
			{
				if(objectInputStream.available() >0)
				{
				payloadList = (Vector<Payload>)objectInputStream.readObject();
				}
				
			} catch (ClassNotFoundException e) 
			{
				e.printStackTrace();
			}
			
			
	    } 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return payloadList;
	}
}

