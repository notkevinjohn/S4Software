package Connection;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

import Data.Payload;
import Data.GetPayloadName;
import Main.Controller;
import SocketHandelers.PayloadDataController;
import Sockets.IPSet;

public class ConnectPayloadSocket extends Thread
{
	private int InPort = 2000;
	public ServerSocket serverInSocket;
	public Vector<Socket> socketPayloadList; // change to socket ip pair later
	public Vector<PayloadDataController> payloadDataList;
	public int socketPayloadIndex = 0;
	private IPSet ipSet;
	private Payload payload;
	private Vector<Payload> payloadList;
	public ConnectPayloadSocket(IPSet ipSet)
	{
		this.ipSet = ipSet;
		socketPayloadList = new Vector<Socket>();
		payloadList = new Vector<Payload>();
		
		payloadDataList = new Vector<PayloadDataController>();
		try 
		{
			serverInSocket = new ServerSocket(InPort);
			
			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void run() 
	{
		try 
		{
			
			Socket socket = serverInSocket.accept();
			payload = new Payload();
			GetPayloadName getPayloaName = new GetPayloadName();
			payload.socket = socket;
			payload.deviceName = getPayloaName.getPName(socket);
			System.out.println(payload.deviceName);
			System.out.println(payload.deviceName);
			System.out.println(payload.deviceName);
			System.out.println(payload.deviceName);
			payloadList.add(payload);
			
			PayloadDataController payloadDataController = new PayloadDataController(socket,ipSet);
			
			payloadDataList.add(payloadDataController);
			socketPayloadList.add(socketPayloadIndex, socket);
			
			
			socketPayloadIndex++;
			ipSet.UPDatePayloadList(socketPayloadList,payloadDataList);
			//controller.payloadIsConnected = true;
		}
		catch (UnknownHostException e1) 
		{
		} 
		catch (IOException e1) 
		{	
		}
	}
}
