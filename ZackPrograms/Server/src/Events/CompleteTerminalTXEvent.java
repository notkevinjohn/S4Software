package Events;

import java.util.EventObject;

public class CompleteTerminalTXEvent extends EventObject 
{
	private static final long serialVersionUID = 1193711128248938869L;
	public int payloadNumber;
	public String sendString;
		
	public  CompleteTerminalTXEvent (Object source, int payloadNumber, String sendString)
	{
		super(source);
		this.payloadNumber = payloadNumber;
		this.sendString = sendString;
	}
}
