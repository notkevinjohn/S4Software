package Connection;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;
import Data.Payload;
import Data.GetName;
import Main.Controller;
import SocketHandelers.PayloadDataController;

public class ConnectPayloadSocket extends Thread
{
	private int payloadPort = 2000;
	private Payload payload;
	private ServerSocket serverInSocket;
	public Vector<PayloadDataController> payloadDataList;
	public  Vector<Payload> payloadList;
	public Controller controller;
	
	public ConnectPayloadSocket(Controller controller)
	{
		this.controller = controller;
		payloadList = new Vector<Payload>();
		payloadDataList = new Vector<PayloadDataController>();
		
		try 
		{
			serverInSocket = new ServerSocket(payloadPort);
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
			
			GetName getName = new GetName();
			payload = new Payload();
			payload.socket = socket;
			payload.deviceName = getName.getPName(socket);
			payloadList.add(payload);
			
			PayloadDataController payloadDataController = new PayloadDataController(socket);
			payloadDataList.add(payloadDataController);
			controller.UPDatePayloadList(payloadDataList);
		}
		catch (UnknownHostException e1) 
		{
			e1.printStackTrace();
		}
		catch (IOException e1) 
		{
			e1.printStackTrace();
		}
	}
}
