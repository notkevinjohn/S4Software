package Data;

import java.io.Serializable;

public class TerminalPayloadList implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8981466204225150432L;
	public String deviceName;
	public String IP;
	public int remotePort;
	public int localPort;
}
