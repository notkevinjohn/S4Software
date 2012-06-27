package Componets;

import java.awt.event.MouseListener;
import javax.swing.JTextPane;

public class TerminalTextActionListener implements MouseListener
{
	private AutoscrollCheckBox chckbxAutoscroll;
	private JTextPane textPane;
	public TerminalTextActionListener(JTextPane textPane,AutoscrollCheckBox chckbxAutoscroll)
	{
		this.chckbxAutoscroll = chckbxAutoscroll;
		this.textPane = textPane;
	}
	
	@Override
	public void mouseClicked(java.awt.event.MouseEvent arg0) {}

	@Override
	public void mouseEntered(java.awt.event.MouseEvent arg0) {}

	@Override
	public void mouseExited(java.awt.event.MouseEvent arg0) {}

	@Override
	public void mousePressed(java.awt.event.MouseEvent arg0) {}

	@Override
	public void mouseReleased(java.awt.event.MouseEvent arg0) 
	{
		if(chckbxAutoscroll.isSelected())
		{
			textPane.setCaretPosition(textPane.getDocument().getLength());
		}
	} 
}


