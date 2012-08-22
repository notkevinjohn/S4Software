package Componets;

import javax.swing.JTextPane;

public class TerminalText extends JTextPane
{
	private static final long serialVersionUID = -8629060581988499266L;
	private boolean actionListenerSet = false;
	
	public TerminalText(JTextPane textPane)
	{
		super();	
		this.setName("TerminalText");
	}
	
	public void setActionListener (JTextPane textPane,AutoscrollCheckBox chckbxAutoscroll)
	{
		if(!actionListenerSet)
		{
			this.addMouseListener(new TerminalTextActionListener(textPane,chckbxAutoscroll));
			actionListenerSet = true;
		}
	}
}

