package Events;

import java.util.EventListener;

public interface ICompleteConnectEventListener extends EventListener 
{
	public void completeConnectEventHandler (CompleteConnectEvent event);	
}
