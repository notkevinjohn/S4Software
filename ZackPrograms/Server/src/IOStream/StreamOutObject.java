package IOStream;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

public class StreamOutObject 

{	private ObjectOutputStream out = null;
	private DataOutputStream dataOutputStream;
	public Socket socket;
	private Vector<String> IPList;
	
	public void attachSocket(Socket _socket)
	{
		socket = _socket;
	}
	public  void objectStreamOut(Vector<String> _IPList)
	{
		this.IPList = _IPList;
		
		try 
		{
			dataOutputStream = new DataOutputStream(socket.getOutputStream());
			out = new ObjectOutputStream(dataOutputStream);
			out.writeObject(IPList);
			out.flush();
	    } 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}


