package Connection;

import java.io.IOException;
import java.io.ObjectOutputStream;
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
	private Controller controller;
	public ServerSocket serverTerminalSocket;
	public Vector<TerminalDataController> terminalDataList;
	public ObjectOutputStream objectOutputStream;
	public ConnectTerminalSocket(Controller controller)
	{
		this.controller = controller;
		terminalDataList = new Vector<TerminalDataController>();
		terminalList = new Vector<Terminal>();
		
		try 
		{
			serverTerminalSocket = new ServerSocket(terminalPort);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void attachTerminal(Socket socket)
	{
			GetName getName = new GetName(controller);
			terminal = new Terminal();
			terminal.socket = socket;
			terminal.deviceName = getName.getPName(socket);
			objectOutputStream = getName.objectOutputStream;
			terminalList.add(terminal);
			
			TerminalDataController terminalDataController = new TerminalDataController(socket, terminal.deviceName,controller);
			terminalDataList.add(terminalDataController);
			controller.UpDateTerminalList(terminalDataList);
			controller.objectOutputStream = objectOutputStream;
	}
	public void run() 
	{
		while(true)
		{
			try 
			{
				Socket socket = serverTerminalSocket.accept();
				attachTerminal(socket);
				
			}
			catch (UnknownHostException e1) 
			{
				e1.printStackTrace();
			} 
			catch (IOException e1) 
			{	
				e1.printStackTrace();
			}
			
			try { Thread.sleep(10); } catch(InterruptedException e) { /* we tried */}
		}
	}
}
