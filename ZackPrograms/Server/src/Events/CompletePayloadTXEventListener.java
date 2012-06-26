package Events;

import Main.Controller;

public class CompletePayloadTXEventListener implements ICompletePayloadTXEventListener
{
	private Controller controller;
	
	public CompletePayloadTXEventListener (Controller controller)
	{
		this.controller = controller;
	}
	public void CompleteTXEventHandler(CompletePayloadTXEvent event) 
	{
		if(controller.terminalDataList != null && controller.terminalDataList.size() >= event.payloadNumber )
		{
			controller.terminalDataList.get(event.payloadNumber).StreamOut(event.sendString);
		}
		else
		{
			System.out.println("Terminal not Connected");
		}
	}
}


