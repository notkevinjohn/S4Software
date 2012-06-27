package Connection;

import Main.Controller;


public class ConnectTermToPayload
{
	public ConnectPayloadSocket connectPayloadSocket;
	private int payloadElementNumber = 0;
	private boolean isAvailable = false;
	private Controller controller;
	
	public ConnectTermToPayload()
	{
	}
	
	public void connectTermToPayload(Controller controller, ConnectPayloadSocket _connectPayloadSocket, ConnectTerminalSocket connectTerminalSocket) 
	{
		this.connectPayloadSocket = _connectPayloadSocket;
		this.controller = controller;
	}
	
	public int Connect(String deviceName)
	{
		for(int i = 0; i < connectPayloadSocket.payloadDataList.size(); i++)
		{
			if(controller.payloadList.get(i).deviceName.equals(deviceName))
			{
				payloadElementNumber = i;
				isAvailable = true;
			}
		}
		if(isAvailable)
		{
			return payloadElementNumber;
		}
		else
		{
			return 99; // Arbitrary number to let terminal know that it failed or is not available yet
		}
			
	}



}
