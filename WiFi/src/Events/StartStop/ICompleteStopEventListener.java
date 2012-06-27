package Events.StartStop;

import java.util.EventListener;

public interface ICompleteStopEventListener  extends EventListener
{
	public void completeStopEventHandler (CompleteStopEvent event);
}

