package Main;

import Connection.ConnectPayloadSocket;
import Connection.ConnectTerminalSocket;

public class Main 
{
	private static Controller controller;
	public static void main(String[] args) 
	{
		controller = new Controller();
		ConnectPayloadSocket connectPayloadSocket = new ConnectPayloadSocket(controller);
		ConnectTerminalSocket connectTerminalSocket = new ConnectTerminalSocket(controller);
		connectPayloadSocket.start();
		connectTerminalSocket.start();
		
	}

}
