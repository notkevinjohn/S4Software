package Data;

import java.io.Serializable;
import java.util.Vector;

public class PayloadListVector implements Serializable
{
	private static final long serialVersionUID = 201039042641623708L;
	
	public Vector<TerminalPayloadList> terminalPayloadList;
}
