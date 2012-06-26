package Main;

import java.util.Vector;
import Events.CompletePayloadTXEventListener;
import Events.CompleteTerminalTXEventListener;
import Events.ICompletePayloadTXEventListener;
import Events.ICompleteTerminalTXEventListener;
import SocketHandelers.PayloadDataController;
import SocketHandelers.TerminalDataController;

public class Controller extends Thread
{
	public Vector<PayloadDataController> payloadDataList;
	public Vector<TerminalDataController> terminalDataList;
	
	public static javax.swing.event.EventListenerList listenerList = new javax.swing.event.EventListenerList();
	
	public Controller()
	{
		this.start();
	}
	
	public void UpDateTerminalList(Vector<TerminalDataController> terminalDataList)
	{
		this.terminalDataList = terminalDataList;
	}
	public void UPDatePayloadList(Vector<PayloadDataController> payloadDataList)
	{
		this.payloadDataList = payloadDataList;
	}
	
	public void run() 
	{
		payloadTXController();// needs to loop through new listeners???
		terminalTXController();
	}
	
	public  void payloadTXController()
	{
		addCompletePayloadTXEventListener(new CompletePayloadTXEventListener(this));
	}
	public  void terminalTXController()
	{
		addCompletedTerminalTXEventListener(new CompleteTerminalTXEventListener(this));
	}
	
	
	public static void addCompletePayloadTXEventListener (ICompletePayloadTXEventListener completeTXEventListener)
	{
		listenerList.add(ICompletePayloadTXEventListener.class, completeTXEventListener);
	}
	public static void addCompletedTerminalTXEventListener (ICompleteTerminalTXEventListener completeTerminalTXEventListener)
	{
		listenerList.add(ICompleteTerminalTXEventListener.class, completeTerminalTXEventListener);
	}

}
