package Componets.Connection;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import Data.IPData;
import GUI.EditIPSrc;

public class DelIPActionListener  implements ActionListener 
{
	private ArrayList<IPData> IPStorage;
	private EditIPSrc editIPSrc;
	private JPanel delPanel;
	public ArrayList<JTextField> ipArray = new ArrayList<JTextField>();
	public ArrayList<JCheckBox> deleteArray = new ArrayList<JCheckBox>();
	
	public DelIPActionListener(JButton DelIPButton, EditIPSrc editIPSrc, JPanel delPanel, ArrayList<IPData> IPStorage, ArrayList<JTextField> ipArray, ArrayList<JCheckBox> deleteArray )
	{
		this.IPStorage = IPStorage;
		this.editIPSrc = editIPSrc;
		this.delPanel = delPanel;
		this.ipArray = ipArray;
		this.deleteArray =deleteArray;
	}
	public void actionPerformed(ActionEvent arg0) 
	{
		int j = 0;
		int ipSize = IPStorage.size();
		for(int i = 0; i < ipSize; i++)
		{
			
			if(deleteArray.get(i).isSelected())
			{
				IPStorage.remove(j);
				j--;
			}
			j++;
		}
		
		
		editIPSrc.frame.remove(delPanel);
	    editIPSrc.redrawIP(IPStorage);
	

	 }
	
}
