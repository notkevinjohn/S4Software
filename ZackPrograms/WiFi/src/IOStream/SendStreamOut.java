package IOStream;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class SendStreamOut
{
	private PrintWriter out = null;
	private String sendText;
	public Socket socket;
	
	public void attachSocket(Socket _socket)
	{
		socket = _socket;
	}
	
	public  void streamOut(String _sendText)
	{
		sendText = _sendText;
		
		try 
		{
			out = new PrintWriter(socket.getOutputStream(), true);
			out.print(sendText);
			out.flush();
	    } 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}
