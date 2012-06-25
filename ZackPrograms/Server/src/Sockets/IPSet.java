package Sockets;

import java.net.Socket;
import java.util.Vector;

import SocketHandelers.PayloadDataController;
import SocketHandelers.TerminalDataController;

public class IPSet 
{
	public Vector<PayloadDataController> payloadDataList;
	public Vector<TerminalDataController> terminalDataList;
	public Vector<Socket> socketTerminalList;
	public Vector<Socket> socketPayloadList;
	
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
}
