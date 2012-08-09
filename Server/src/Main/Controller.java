package Main;

import java.util.Vector;

import Connection.ConnectPayloadSocket;
import Connection.ConnectTermToPayload;
import Connection.ConnectTerminalSocket;
import Data.Payload;
import Data.TerminalPayloadList;
import Events.CompletePayloadTXEventListener;
import Events.CompleteTerminalTXEventListener;
import Events.ICompletePayloadTXEventListener;
import Events.ICompleteTerminalTXEventListener;
import GUI.GUI;
import SocketHandelers.PayloadDataController;
import SocketHandelers.TerminalDataController;

public class Controller extends Thread
{
	public Vector<PayloadDataController> payloadDataList;
	public Vector<TerminalDataController> terminalDataList;
	public Vector<Payload> payloadList;
	public TerminalPayloadList terminalPayloadList;
	public Vector<TerminalPayloadList> payloadListVector;
	
	public static javax.swing.event.EventListenerList listenerList = new javax.swing.event.EventListenerList();
	
	public Controller()
	{	
		new GUI();
		this.start();
		ConnectTermToPayload connectTermToPayload = new ConnectTermToPayload();
		ConnectPayloadSocket connectPayloadSocket = new ConnectPayloadSocket(this);
		ConnectTerminalSocket connectTerminalSocket = new ConnectTerminalSocket(this, connectTermToPayload);
		connectPayloadSocket.start();
		connectTerminalSocket.start();
		connectTermToPayload.connectTermToPayload(this,connectPayloadSocket,connectTerminalSocket);
		
		terminalPayloadList = new TerminalPayloadList();
		payloadListVector = new Vector<TerminalPayloadList>();
	}
	
	public void UpDateTerminalList(Vector<TerminalDataController> terminalDataList)
	{
		this.terminalDataList = terminalDataList;
	}
	
	public void UPDatePayloadList(Vector<PayloadDataController> payloadDataList, Vector<Payload> payloadList)
	{
		this.payloadDataList = payloadDataList;
		this.payloadList = payloadList;
		terminalPayloadList = new TerminalPayloadList();
		terminalPayloadList.deviceName = payloadList.lastElement().deviceName;
		terminalPayloadList.IP = payloadList.lastElement().socket.getLocalAddress().toString();
		terminalPayloadList.localPort = payloadList.lastElement().socket.getLocalPort();
		terminalPayloadList.remotePort = payloadList.lastElement().socket.getPort();
		payloadListVector.add(terminalPayloadList);
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
