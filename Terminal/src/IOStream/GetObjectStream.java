package IOStream;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Vector;

import Data.PayloadData;
import Data.TerminalPayloadList;

public class GetObjectStream 
{
	private Socket socket;
	public  ObjectInputStream objectInputStream; 
	public Vector<TerminalPayloadList> payloadListVector;
	public Vector<PayloadData> payloadDataVector;
	
	
	public void getObjectStream(Socket _socket)
	{
		socket = _socket;
	}
	
	@SuppressWarnings("unchecked")
	public  Vector<TerminalPayloadList> getObject()	
	{
		try 
		{
			objectInputStream = new ObjectInputStream(socket.getInputStream());
			try 
			{
				payloadListVector = (Vector<TerminalPayloadList>)objectInputStream.readUnshared();
				
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

