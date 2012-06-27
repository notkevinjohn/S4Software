package Events;

import java.util.EventObject;

public class CompleteSendEvent  extends EventObject{
	private static final long serialVersionUID = 1893392283841623289L;
	public String send;
	
	public  CompleteSendEvent (Object source, String _send)
	{
		super(source);
		send = _send;
	}
}



