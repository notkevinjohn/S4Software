package Componets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Events.StartStop.CompleteStopEvent;
import Events.StartStop.ICompleteStopEventListener;
import Events.StartStop.CompleteStartEvent;
import Events.StartStop.ICompleteStartEventListener;
import Main.DataController;

public class StartStopButtonActionListener implements ActionListener
{
	private StartStopButton startStopButton;
	private boolean buttonStateStop;
	
	public StartStopButtonActionListener(StartStopButton startStopButton)
	{
		this.startStopButton = startStopButton;
	}

	public void actionPerformed(ActionEvent arg0) 
	{
		if(buttonStateStop ==false)
		{
			buttonStateStop = true;
			startStopButton.setText("Start");
			
			CompleteStopEvent complete = new CompleteStopEvent(this);
			Object[] listeners = DataController.listenerList.getListenerList(); 
	   		for (int i=0; i<listeners.length; i+=2) 
	   		{
	             if (listeners[i]==ICompleteStopEventListener.class)
	             {
	                 ((ICompleteStopEventListener)listeners[i+1]).completeStopEventHandler(complete);
	             }
	        } 
		}
		else if(buttonStateStop == true)
		{
			buttonStateStop = false;
			startStopButton.setText("Stop");
			CompleteStartEvent complete = new CompleteStartEvent(this);
			Object[] listeners = DataController.listenerList.getListenerList(); 
	   		for (int i=0; i<listeners.length; i+=2) 
	   		{
	             if (listeners[i]==ICompleteStartEventListener.class)
	             {
	                 ((ICompleteStartEventListener)listeners[i+1]).completeStartEventHandler(complete);
	             }
	        } 
		}
    } 		 
}




