package Events.StartStop;

import java.util.EventObject;

public class CompleteStopEvent  extends EventObject
{
	private static final long serialVersionUID = -4277930454878824170L;

	public  CompleteStopEvent (Object source)
	{
		super(source);
	}
}

