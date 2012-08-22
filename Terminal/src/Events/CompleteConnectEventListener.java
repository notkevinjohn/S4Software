package Events;

public class CompleteConnectEventListener implements ICompleteConnectEventListener
{
	public void completeConnectEventHandler(CompleteConnectEvent event) 
	{
		System.out.print(event);
	}
}

