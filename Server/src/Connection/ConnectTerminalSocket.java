package Connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;
import Data.GetName;
import Data.Terminal;
import Main.Controller;
import SocketHandelers.TerminalDataController;


public class ConnectTerminalSocket extends Thread
{
	private int terminalPort = 2001;
	private Terminal terminal;
	private Vector<Terminal> terminalList;
	private ConnectTermToPayload connectTermToPayload;
	private int payloadSocketNubmer = 0;
	private Controller controller;
	public ServerSocket serverTerminalSocket;
	public Vector<TerminalDataController> terminalDataList;
	
	public ConnectTerminalSocket(Controller controller, ConnectTermToPayload connectTermToPayload)
	{
		this.controller = controller;
		this.connectTermToPayload = connectTermToPayload;
		terminalDataList = new Vector<TerminalDataController>();
		terminalList = new Vector<Terminal>(); // this may have to move to allow mulitple terminal connections
		
		try 
		{
			serverTerminalSocket = new ServerSocket(terminalPort);
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
			Socket socket = serverTerminalSocket.accept();
			
			GetName getName = new GetName(controller);
			terminal = new Terminal();
			terminal.socket = socket;
			terminal.deviceName = getName.getPName(socket);
			terminalList.add(terminal);
		
			payloadSocketNubmer = connectTermToPayload.Connect(terminal.deviceName);
			if(payloadSocketNubmer != 99)
			{
				TerminalDataController terminalDataController = new TerminalDataController(socket, payloadSocketNubmer);
				terminalDataList.add(terminalDataController);
				controller.UpDateTerminalList(terminalDataList);
			}
			else
			{
				System.out.println("Fail"); // need to make this do something to correct it
			}
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