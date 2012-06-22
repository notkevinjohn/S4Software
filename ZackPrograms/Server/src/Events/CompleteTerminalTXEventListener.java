package Events;

import Main.Controller;

public class CompleteTerminalTXEventListener implements ICompleteTerminalTXEventListener
{
	private Controller controller;
	
	public CompleteTerminalTXEventListener (Controller controller)
	{
		this.controller = controller;
	}
	
	public void CompleteTXEventHandler(CompleteTerminalTXEvent event) 
	{
		if(controller.payloadIsConnected)
		{
			controller.payloadDataList.get(event.payloadNumber).StreamOut(event.sendString);
		}
		else
		{
			System.out.println("Payload not Connected");
		}

	}
}

