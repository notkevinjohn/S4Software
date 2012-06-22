package Events;

import java.util.EventListener;

public interface ICompletePayloadTXEventListener extends EventListener
{
	public void CompleteTXEventHandler (CompletePayloadTXEvent event);
}

