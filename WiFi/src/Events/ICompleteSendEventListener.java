package Events;

import java.util.EventListener;

public interface ICompleteSendEventListener extends EventListener
{
	public void completeSendEventHandler (CompleteSendEvent event);
}
