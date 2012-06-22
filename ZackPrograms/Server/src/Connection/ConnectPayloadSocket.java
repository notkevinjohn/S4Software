package Connection;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

import Main.Controller;
import SocketHandelers.PayloadDataController;

public class ConnectPayloadSocket extends Thread
{
	private int InPort = 2000;
	public ServerSocket serverInSocket;
	public Vector<Socket> socketPayloadList; // change to socket ip pair later
	public Vector<PayloadDataController> payloadDataList;
	public int socketPayloadIndex = 0;
	private Controller controller;
	
	public ConnectPayloadSocket(Controller controller)
	{
		this.controller = controller;
		socketPayloadList = new Vector<Socket>();
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
			PayloadDataController payloadDataController = new PayloadDataController(socket);
			payloadDataList.add(payloadDataController);
			
			socketPayloadList.add(socketPayloadIndex, socket);
			socketPayloadIndex++;
			controller.UPDatePayloadList(socketPayloadList,payloadDataList);
			controller.payloadIsConnected = true;
		}
		catch (UnknownHostException e1) 
		{
		} 
		catch (IOException e1) 
		{	
		}
	}
}
