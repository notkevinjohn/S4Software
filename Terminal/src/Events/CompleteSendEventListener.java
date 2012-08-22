package Events;

import java.awt.Color;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import IOStream.SendStreamOut;
import Main.DataController;

public class CompleteSendEventListener implements ICompleteSendEventListener
{
	private SendStreamOut streamOut;
	private SimpleAttributeSet red = new SimpleAttributeSet();
	private DataController dataController;
	
	public CompleteSendEventListener (DataController dataController, SendStreamOut streamOut)
	{
		this.streamOut = streamOut;
		this.dataController = dataController;
		StyleConstants.setForeground(red, Color.RED);
	}
	
	public void completeSendEventHandler(CompleteSendEvent event) 
	{
		streamOut.streamOut(event.send);
		dataController.updateText(event.send, red);
	}
}

	

