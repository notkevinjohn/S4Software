package Componets;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTextField;
import Events.CompleteSendEvent;
import Events.ICompleteSendEventListener;
import Main.DataController;

public class SendLineTextFieldActionListener implements KeyListener
{
	private JTextField sendLine;
	private String sendString;
	
	public SendLineTextFieldActionListener(JTextField sendLine)
	{
		this.sendLine = sendLine;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			sendString = sendLine.getText() + "\n";
			sendLine.setText("");
			CompleteSendEvent complete = new CompleteSendEvent(this,sendString);
			Object[] listeners = DataController.listenerList.getListenerList(); 
	   		for (int i=0; i<listeners.length; i+=2) 
	   		{
	             if (listeners[i]==ICompleteSendEventListener.class)
	             {
	                 ((ICompleteSendEventListener)listeners[i+1]).completeSendEventHandler(complete);
	             }
	        } 
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) 
	{	
	}
	@Override
	public void keyReleased(KeyEvent e) 
	{
	}
}


	
