package IOStream;

import java.io.IOException;
import java.net.Socket;

public class GetStreamIn
{
	public String inputString;
	public String recieve;
	public String ip;
	public int port;
	public Socket socket;

	public  String StreamIn(Socket _socket)
	{
		socket = _socket;
		try 
		{
			int available = socket.getInputStream().available();
			byte chunk[] = new byte[available];
			socket.getInputStream().read(chunk, 0, available);
			recieve = new String(chunk);
	    } 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return recieve;
	}
}
