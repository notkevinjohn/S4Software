package Componets;

import javax.swing.JCheckBox;
import javax.swing.JTextPane;





public class AutoscrollCheckBox extends JCheckBox
{
	private static final long serialVersionUID = -7735859156210641768L;
	private boolean actionListenerSet = false;
	private  JTextPane textPane;
	
	public AutoscrollCheckBox(JTextPane textPane)
	{
		super("AutoScroll");	
		this.setName("AutoScroll");
		this.textPane = textPane;
	}	
	
	public void setActionListener ()
	{
		if(!actionListenerSet)
		{
			this.addActionListener( new AutoscrollCheckBoxActionListener(textPane));
			actionListenerSet = true;
		}
	}
}