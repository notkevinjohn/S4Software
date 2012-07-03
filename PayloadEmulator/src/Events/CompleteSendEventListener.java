package Events;

import java.awt.Color;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import IOStream.SendStreamOut;

public class CompleteSendEventListener implements ICompleteSendEventListener
{
	private SendStreamOut streamOut;
	private SimpleAttributeSet red = new SimpleAttributeSet();
	
	public CompleteSendEventListener (SendStreamOut streamOut)
	{
		this.streamOut = streamOut;
		StyleConstants.setForeground(red, Color.RED);
	}
	
	public void completeSendEventHandler(CompleteSendEvent event) 
	{
		streamOut.streamOut(event.send);
	}
}

	

