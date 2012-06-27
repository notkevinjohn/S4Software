package Events.StartStop;

import Main.DataController;

public class CompleteStartEventListener implements ICompleteStartEventListener
{	
	private DataController dataController;
	
	public CompleteStartEventListener (DataController dataController)
	{
		this.dataController = dataController;
	}
	public void completeStartEventHandler(CompleteStartEvent event) 
	{
		dataController.boolStream = true;
		dataController.SendButtonEnable();
	}
}
