package Main;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;
import Events.CompletePayloadTXEventListener;
import Events.CompleteTerminalTXEventListener;
import Events.ICompletePayloadTXEventListener;
import Events.ICompleteTerminalTXEventListener;
import SocketHandelers.PayloadDataController;
import SocketHandelers.TerminalDataController;
import Sockets.IPSet;

public class Controller extends Thread
{
	public Vector<PayloadDataController> payloadDataList;
	public Vector<TerminalDataController> terminalDataList;
	public Vector<Socket> socketTerminalList;
	public Vector<Socket> socketPayloadList;
	public boolean terminalIsConnected;
	public boolean payloadIsConnected;
	
	public static javax.swing.event.EventListenerList listenerList = new javax.swing.event.EventListenerList();
	
	public Controller()
	{
		terminalIsConnected = false;
		payloadIsConnected = false;
		this.start();
	}
	
	public void UpDateTerminalList(Vector<Socket> socketTerminalList, Vector<TerminalDataController> terminalDataList)
	{
		this.socketTerminalList = socketTerminalList;
		this.terminalDataList = terminalDataList;
	}
	public void UPDatePayloadList(Vector<Socket> socketPayloadList,Vector<PayloadDataController> payloadDataList)
	{
		this.socketPayloadList = socketPayloadList;
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
