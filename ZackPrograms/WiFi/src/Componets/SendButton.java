package Componets;

import javax.swing.JButton;
import javax.swing.JTextField;

public class SendButton extends JButton
{
	private static final long serialVersionUID = -5039977757360860835L;
	private boolean actionListenerSet = false;
	private JTextField sendLine;
	
	public SendButton(JTextField sendLine)
	{
		super("Send");	
		this.setName("SendButton");
		this.sendLine = sendLine;
	}
	
	public void setActionListener ()
	{
		if(!actionListenerSet)
		{
			this.addActionListener( new SendActionListener(sendLine));
			actionListenerSet = true;
		}
	}
}
