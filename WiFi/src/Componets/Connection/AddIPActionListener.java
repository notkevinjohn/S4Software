package Componets.Connection;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Data.IPData;
import GUI.EditIPSrc;

public class AddIPActionListener implements ActionListener {

	private JTextField addNameText;
	private JTextField addIPText;
	private JTextField addPortText;
	private ArrayList<IPData> IPStorage;
	private EditIPSrc editIPSrc;
	private JPanel delPanel;
	
	public AddIPActionListener(JButton AddIPButton, EditIPSrc editIPSrc, JPanel delPanel, ArrayList<IPData> IPStorage,JTextField addNameText,JTextField addIPText,JTextField addPortText)
	{
		this.IPStorage = IPStorage;
		this.editIPSrc = editIPSrc;
		this.addNameText = addNameText;
		this.addIPText = addIPText;
		this.addPortText = addPortText;
		this.delPanel = delPanel;
	}
	
	public void actionPerformed(ActionEvent arg0) 
	{
		String name = addNameText.getText();
		String ip = addIPText.getText();
		int port = Integer.parseInt(addPortText.getText());
		
		IPData ipData = new IPData();
		ipData.name = name;
		ipData.ip = ip;
		ipData.port = port; 	 
		IPStorage.add(ipData);
		
		addNameText.setText("");
		addIPText.setText("");
		
		editIPSrc.frame.remove(delPanel);
	    editIPSrc.redrawIP(IPStorage);
	  }
     	
}

	

	