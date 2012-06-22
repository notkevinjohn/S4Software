package Connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

import Main.Controller;
import SocketHandelers.TerminalDataController;

public class ConnectTerminalSocket extends Thread
{
	private int InPort = 2001;
	public ServerSocket serverTerminalSocket;
	public Vector<Socket> socketTerminalList; // change to socket ip pair later
	public Vector<TerminalDataController> terminalDataList;
	public int socketTerminalIndex = 0;
	private Controller controller;
	
	public ConnectTerminalSocket(Controller controller)
	{
		this.controller = controller;
		socketTerminalList = new Vector<Socket>();
		terminalDataList = new Vector<TerminalDataController>();
		try 
		{
			serverTerminalSocket = new ServerSocket(InPort);
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
			
			TerminalDataController terminalDataController = new TerminalDataController(socket);
			terminalDataList.add(terminalDataController);
			
			socketTerminalList.add(socketTerminalIndex, socket);
			socketTerminalIndex++;
			controller.UpDateTerminalList(socketTerminalList, terminalDataList);
			controller.terminalIsConnected = true;
		}
		catch (UnknownHostException e1) 
		{
		} 
		catch (IOException e1) 
		{	
		}
	}
}
