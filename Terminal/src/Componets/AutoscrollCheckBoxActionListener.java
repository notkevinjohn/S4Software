package Componets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextPane;

public class AutoscrollCheckBoxActionListener implements ActionListener
{
	private boolean isAutoScroll = false;
	private  JTextPane textPane;
	
	public AutoscrollCheckBoxActionListener(JTextPane textPane)
	{
		this.textPane = textPane;
	}
	
	public void actionPerformed(ActionEvent arg0) 
	{
		if(isAutoScroll)
		{
			textPane.setCaretPosition(textPane.getDocument().getLength());
			isAutoScroll = false;
		}
		else
		{
			textPane.setCaretPosition(textPane.getDocument().getLength()-1);
			textPane.setEditable(true);
			isAutoScroll = true;
		}
    } 		 
}


	

	