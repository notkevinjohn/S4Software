package Main;

import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Vector;

import javax.swing.SwingUtilities;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import Data.PayloadData;
import Events.CompleteSendEventListener;
import Events.StartStop.ICompleteStartEventListener;
import Events.StartStop.ICompleteStopEventListener;
import Events.StartStop.CompleteStartEventListener;
import Events.StartStop.CompleteStopEventListener;
import Events.ICompleteSendEventListener;
import FileWriters.WiFiWriter;
import GUI.Terminal;
import IOStream.GetStreamIn;
import IOStream.PayloadObjectRX;
import IOStream.SendStreamOut;

public class DataController extends Thread 
{
	private Terminal terminal;
	public Socket socket;
	private int updateRate = 1000;
	private SendStreamOut streamOut;
	private String streamInString;
	private GetStreamIn getStreamIn;
	private int available = 0;
	private SimpleAttributeSet blue = new SimpleAttributeSet();
	private SimpleAttributeSet green = new SimpleAttributeSet();
	public Vector<PayloadData> payloadDataVector;
	public boolean boolStream = true;
	public long lastReadTime = System.currentTimeMillis();
	public long lastUpdateTime = System.currentTimeMillis();;
	public String ip;
	public int port;
	public WiFiWriter wiFiWriter;
	public static javax.swing.event.EventListenerList listenerList = new javax.swing.event.EventListenerList();
	public String deviceName;
	public PayloadObjectRX payloadObjectRX;
	public  ObjectInputStream objectInputStream;
	
	public void Initilize(Socket socket, String ip, int port, String deviceName, ObjectInputStream objectInputStream)
	{
		this.objectInputStream = objectInputStream;
		this.socket = socket;
		this.ip = ip;
		this.port = port;
		this.deviceName = deviceName;
		
		StyleConstants.setForeground(blue, Color.BLUE);
		StyleConstants.setForeground(green, new Color(0,64,0));
		
		terminal = new Terminal(deviceName);
		wiFiWriter = new WiFiWriter();
		getStreamIn = new GetStreamIn();
		streamOut = new SendStreamOut();
		streamOut.attachSocket(socket);
		payloadObjectRX = new PayloadObjectRX(socket,objectInputStream);
		payloadDataVector = new Vector<PayloadData>();
		TextSendController();
		Start();
		Stop();
		this.start();
	}
	
	public void run() 
	{
		while(true)
		{
			if(boolStream)
			{
				try 
				{
					available = socket.getInputStream().available();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
				
				if(available > 0)
				{
					  lastReadTime = System.currentTimeMillis();
					  streamInString = getStreamIn.StreamIn(socket);
					  System.out.println(streamInString);
					   
					  if(streamInString.startsWith("PayloadUpdate"))
					  {
							payloadDataVector = payloadObjectRX.getPayloadObject(payloadDataVector,this);
							
							if(payloadDataVector != null)
							{
								updateText(payloadDataVector.lastElement().gpsData , blue);
								String tempString = payloadDataVector.lastElement().scienceData;
								tempString += '\n';
								
								updateText(tempString, blue);
							}
					  }
				}
				
//				if(!isConnectionAlive())
//				{
//					updateText("Lost Connection...\n" , green);
//					terminal.btnSend.setEnabled(false);
//					
//					Reconnect reconnect = new Reconnect(this,ip,port);
//					socket = reconnect.socketLoop();
//					
//					if(socket.isBound())
//					{
//						
//						streamOut.attachSocket(socket);
//						terminal.btnSend.setEnabled(true);
//						lastReadTime = System.currentTimeMillis();			
//					}
//				}
				
				if(System.currentTimeMillis() - lastUpdateTime > updateRate)
				{
					String stringOut = "payloadUpdateRequest." + deviceName;
					streamOut.streamOut(stringOut);
					lastUpdateTime = System.currentTimeMillis();

				}
				
			}
			else
			{
				 getStreamIn.StreamIn(socket);
				 lastReadTime = System.currentTimeMillis();
			}
			try { Thread.sleep(10); } catch(InterruptedException e) { /* we tried */}
		}
	}
	
	public void updateText(final String _streamInString, final SimpleAttributeSet type) {
		  SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	 terminal.updateText(_streamInString,type);
		    	 wiFiWriter.recieveText(_streamInString);
		    }
		  });
		}
	
	public  void TextSendController()
	{
		addCompleteSendEventListener(new CompleteSendEventListener(this, streamOut));
	}
	
	public void Start()
	{
		addCompleteStartEventListener(new CompleteStartEventListener(this));
	}
	
	public void Stop()
	{
		addCompleteStopEventListener(new CompleteStopEventListener(this));
	}
	public void SendButtonDisable()
	{
		terminal.btnSend.setEnabled(false);
	}
	
	public void SendButtonEnable()
	{
		terminal.btnSend.setEnabled(true);
	}
	
	public boolean isConnectionAlive()
	{
		return true; //(System.currentTimeMillis() - lastReadTime) < timeout;
	}

	public static void addCompleteSendEventListener (ICompleteSendEventListener completeSendEventListener)
	{
		listenerList.add(ICompleteSendEventListener.class, completeSendEventListener);
	}
	public static void removeCompleteSendEventListener (ICompleteSendEventListener completeSendEventListener)
	{
		listenerList.remove(ICompleteSendEventListener.class, completeSendEventListener);
	}
	public static void addCompleteStartEventListener (ICompleteStartEventListener completeStartEventListener)
	{
		listenerList.add(ICompleteStartEventListener.class, completeStartEventListener);
	}
	public static void addCompleteStopEventListener (ICompleteStopEventListener completeStopEventListener)
	{
		listenerList.add(ICompleteStopEventListener.class, completeStopEventListener);
	}

}
