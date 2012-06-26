package Events;

import java.util.EventObject;

public class CompletePayloadTXEvent extends EventObject 
{
	private static final long serialVersionUID = -4079343760417562062L;
	public int payloadNumber;
	public String sendString;
		
	public  CompletePayloadTXEvent (Object source, int payloadNumber, String sendString)
	{
		super(source);
		this.payloadNumber = payloadNumber;
		this.sendString = sendString;
	}
}
