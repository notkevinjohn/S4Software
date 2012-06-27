package Data;

import java.io.Serializable;
import java.net.Socket;

public class Payload implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7670525197429457891L;
	
	public String deviceName;
	public Socket socket;
}
