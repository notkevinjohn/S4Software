package Main;

import Sockets.IPSet;
import Connection.ConnectPayloadSocket;
import Connection.ConnectTerminalSocket;

public class Main 
{
	private static Controller controller;
	private static IPSet ipSet;
	
	public static void main(String[] args) 
	{
		controller = new Controller();
		ipSet = new IPSet();
		ConnectPayloadSocket connectPayloadSocket = new ConnectPayloadSocket(ipSet);
		ConnectTerminalSocket connectTerminalSocket = new ConnectTerminalSocket(ipSet);
		connectPayloadSocket.start();
		connectTerminalSocket.start();
		
	}

}
