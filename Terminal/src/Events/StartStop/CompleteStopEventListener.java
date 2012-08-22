package Events.StartStop;

import Main.DataController;

public class CompleteStopEventListener implements ICompleteStopEventListener
{
	private DataController dataController;
	
	public CompleteStopEventListener (DataController dataController)
	{
		this.dataController = dataController;
	}

	public void completeStopEventHandler(CompleteStopEvent event) 
	{
		dataController.SendButtonDisable();
		dataController.boolStream = false;
	}
}


	

