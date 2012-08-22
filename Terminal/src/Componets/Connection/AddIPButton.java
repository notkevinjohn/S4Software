package Componets.Connection;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import Data.IPData;
import GUI.EditIPSrc;

public class AddIPButton extends JButton
{
	private static final long serialVersionUID = -665169574061051089L;
	private boolean actionListenerSet = false;
	private JTextField addNameText;
	private JTextField addIPText;
	private JTextField addPortText;
	private EditIPSrc editIPSrc;
	private JPanel delPanel;
	
	public AddIPButton(EditIPSrc editIPSrc, JPanel delPanel, ArrayList<IPData> IPStorage,JTextField addNameText,JTextField addIPText,JTextField addPortText)
	{
		super("Add IP");	
		this.setName("Add IP");
		this.editIPSrc = editIPSrc;
		this.addNameText = addNameText;
		this.addIPText = addIPText;
		this.addPortText = addPortText;
		this.delPanel = delPanel;
	}
	
	public void setActionListener (ArrayList<IPData> IPStorage)
	{
		if(!actionListenerSet)
		{
			this.addActionListener(new AddIPActionListener(this, editIPSrc, delPanel, IPStorage, addNameText, addIPText, addPortText));
			actionListenerSet = true;
		}
	}
}