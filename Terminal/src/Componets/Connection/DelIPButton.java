package Componets.Connection;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import Data.IPData;
import GUI.EditIPSrc;

public class DelIPButton  extends JButton
{
	private static final long serialVersionUID = -8578534858109448150L;
	private boolean actionListenerSet = false;
	private EditIPSrc editIPSrc;
	private JPanel delPanel;
	public ArrayList<JTextField> ipArray = new ArrayList<JTextField>();
	public ArrayList<JCheckBox> deleteArray = new ArrayList<JCheckBox>();
	
	public DelIPButton(EditIPSrc editIPSrc, JPanel delPanel, ArrayList<IPData> IPStorage, ArrayList<JTextField> ipArray, ArrayList<JCheckBox> deleteArray )
	{
		super("Delete");	
		this.setName("Del IP");
		this.editIPSrc = editIPSrc;
		this.delPanel = delPanel;
		this.ipArray = ipArray;
		this.deleteArray =deleteArray;
	}
	
	public void setActionListener (ArrayList<IPData> IPStorage)
	{
		if(!actionListenerSet)
		{
			this.addActionListener(new DelIPActionListener(this,editIPSrc, delPanel, IPStorage ,ipArray, deleteArray));
			actionListenerSet = true;
		}
	}
}
