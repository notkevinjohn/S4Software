package Main;

import java.io.ObjectOutputStream;
import java.util.Vector;

import Connection.ConnectPayloadSocket;
import Connection.ConnectTermToPayload;
import Connection.ConnectTerminalSocket;
import Data.Payload;
import Data.PayloadData;
import Data.TerminalPayloadList;
import Events.CompletePayloadTXEventListener;
import Events.CompleteTerminalTXEventListener;
import Events.ICompletePayloadTXEventListener;
import Events.ICompleteTerminalTXEventListener;
import GUI.GUI;
import IOStream.PayloadObjectTX;
import IOStream.SendStreamOut;
import SocketHandelers.PayloadDataController;
import SocketHandelers.TerminalDataController;

public class Controller extends Thread
{
	public Vector<PayloadDataController> payloadDataList;
	public Vector<TerminalDataController> terminalDataList;
	public Vector<Payload> payloadList;
	public TerminalPayloadList terminalPayloadList;
	public Vector<TerminalPayloadList> payloadListVector;
	public PayloadObjectTX payloadObjectTX;
	public SendStreamOut streamOut;
	public ObjectOutputStream objectOutputStream;
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
		streamOut = new SendStreamOut();
		payloadObjectTX = new PayloadObjectTX();
		
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
		payloadTXController();
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
	
	// Takes a request from a Terminal for the Payload update and updates it with the newest data
	public void terminalRequestForUpdate(TerminalDataController termDataController, String payloadName)
	{
		for(int i = 0; i < payloadDataList.size(); i++)
		{
			if(payloadName.equals(payloadDataList.get(i).deviceName))
			{
				
				PayloadData payloadLastData = payloadDataList.get(i).payloadDataVector.get(payloadDataList.get(i).payloadDataVector.size()-1);
				streamOut.attachSocket(termDataController.socket);
				streamOut.streamOut("PayloadUpdate");
				//try { Thread.sleep(10); } catch(InterruptedException e) { /* we tried */}
				payloadObjectTX.sendObject(termDataController.socket, payloadLastData, objectOutputStream);
			}
		}
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
