package Componets;

import javax.swing.JTextField;

public class SendLineTextField extends JTextField
{
	private static final long serialVersionUID = 5490994239608906784L;
	private boolean actionListenerSet = false;
	
	public SendLineTextField()
	{
		super("");	
		this.setName("SendLine");
	}
	
	public void setActionListener ()
	{
		if(!actionListenerSet)
		{
			this.addKeyListener( new SendLineTextFieldActionListener(this));
			actionListenerSet = true;
		}
	}
}



