package Events.StartStop;

import java.util.EventListener;

public interface ICompleteStartEventListener extends EventListener
{
	public void completeStartEventHandler (CompleteStartEvent event);
}
