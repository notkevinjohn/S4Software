package Main;

import Connection.ConnectPayloadSocket;
import Connection.ConnectTermToPayload;
import Connection.ConnectTerminalSocket;

public class Main 
{
	public static void main(String[] args) 
	{
		Controller controller = new Controller();
		ConnectTermToPayload connectTermToPayload = new ConnectTermToPayload();
		ConnectPayloadSocket connectPayloadSocket = new ConnectPayloadSocket(controller);
		ConnectTerminalSocket connectTerminalSocket = new ConnectTerminalSocket(controller, connectTermToPayload);
		connectPayloadSocket.start();
		connectTerminalSocket.start();
		connectTermToPayload.connectTermToPayload(connectPayloadSocket,connectTerminalSocket);
	}

}
