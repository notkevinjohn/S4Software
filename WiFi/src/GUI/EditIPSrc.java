package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.miginfocom.swing.MigLayout;
import Componets.Connection.AddIPButton;
import Componets.Connection.DelIPButton;
import Componets.Connection.IPOKButton;
import Data.IPData;


public class EditIPSrc
{
	public JFrame frame;
	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JPanel delPanel;
	private JTextField addNameText;
	private JTextField addIPText;
	private JTextField addPortText;
	private JTextField nameText;
	private JTextField IPText;
	private JTextField portText;
	private JCheckBox delCheckBx;
	private AddIPButton btnAdd;
	private IPOKButton btnOk;
	private JPanel panel;
	private DelIPButton btnDelSelected;
	private GetIP getIP;
	private JComboBox<String> TCPcomboBox;
	public ArrayList<JTextField> ipArray = new ArrayList<JTextField>();
	public ArrayList<JCheckBox> deleteArray = new ArrayList<JCheckBox>();
	
	public EditIPSrc(GetIP getIP, JComboBox<String> TCPcomboBox, ArrayList<IPData> IPStorage) 
	{
		this.getIP = getIP;
		this.TCPcomboBox = TCPcomboBox;
		try 
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } 
	    catch (UnsupportedLookAndFeelException e) { }
	    catch (ClassNotFoundException e) {}
	    catch (InstantiationException e) {}
	    catch (IllegalAccessException e) {}
		
		editIPSrc(IPStorage);
	}

	public void editIPSrc(ArrayList<IPData> IPStorage) 
	{
		
		frame = new JFrame("Edit Stored IP Addresses");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 471, 333);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		frame.setContentPane(contentPane);
		
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblNewIp = new JLabel("New IP");
		lblNewIp.setBounds(10, 11, 46, 14);
		panel.add(lblNewIp);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(10, 36, 46, 14);
		panel.add(lblName);
		
		JLabel lblIp = new JLabel("IP");
		lblIp.setBounds(109, 36, 46, 14);
		panel.add(lblIp);
		
		JLabel lblPort = new JLabel("Port");
		lblPort.setBounds(225, 36, 46, 14);
		panel.add(lblPort);
		
		addNameText = new JTextField();
		addNameText.setBounds(10, 57, 86, 20);
		panel.add(addNameText);
		addNameText.setColumns(10);
		
		addIPText = new JTextField();
		addIPText.setBounds(109, 57, 106, 20);
		panel.add(addIPText);
		addIPText.setColumns(10);
		
		addPortText = new JTextField();
		addPortText.setBounds(225, 57, 52, 20);
		addPortText.setText("2000");
		panel.add(addPortText);
		addPortText.setColumns(10);
		
		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(10, 88, 314, 186);
		panel.add(scrollPane);
		
		btnOk = new IPOKButton(this, getIP, TCPcomboBox, IPStorage);
		btnOk.setBounds(346, 251, 89, 23);
		panel.add(btnOk);
		btnOk.setActionListener(IPStorage);
		
		frame.setVisible(true);
	
		redrawIP(IPStorage);
	}
	
	
	
	public void redrawIP(ArrayList<IPData> IPStorage)
	{
		delPanel = new JPanel();
		scrollPane.setViewportView(delPanel);
		delPanel.setLayout(null);
		delPanel.setLayout(new MigLayout("", "[107.00px][102.00][47.00][20]", "[]"));
		
		int lengthOfIP = IPStorage.size();
		System.out.print(lengthOfIP);
		
		ipArray.clear();
		deleteArray.clear();
		
		
		for(int i =0; i< lengthOfIP; i++)
		{
			String name =IPStorage.get(i).name;
			nameText = new JTextField();
			nameText.setBackground(Color.WHITE);
			nameText.setSize(50,20);
			nameText.setEditable(false);
			nameText.setColumns(10);
			ipArray.add(nameText);
			delPanel.add(nameText);
			nameText.setText(name);
			
			String ip = IPStorage.get(i).ip;
			IPText = new JTextField();
			IPText.setBackground(Color.WHITE);
			IPText.setSize(50,20);
			IPText.setEditable(false);
			IPText.setColumns(10);
			ipArray.add(IPText);
			delPanel.add(IPText);
			IPText.setText(ip);
			
			String port = Integer.toString(IPStorage.get(i).port);
			portText = new JTextField();
			portText.setBackground(Color.WHITE);
			portText.setSize(50,20);
			portText.setEditable(false);
			portText.setColumns(10);
			ipArray.add(portText);
			delPanel.add(portText);
			portText.setText(port);
			
			delCheckBx = new JCheckBox("");
			delCheckBx.setBackground(Color.WHITE);
			delCheckBx.setSize(50,20);
			deleteArray.add(delCheckBx);
			delPanel.add(delCheckBx,"wrap");
			delCheckBx.setBounds(271, 10, 97, 23);
		}
		
		btnAdd = new AddIPButton(this, delPanel, IPStorage, addNameText, addIPText, addPortText);
		btnAdd.setBounds(334, 56, 101, 23);
		panel.add(btnAdd);
		btnAdd.setActionListener(IPStorage);
		
		btnDelSelected = new DelIPButton(this, delPanel , IPStorage, ipArray, deleteArray);
		btnDelSelected.setBounds(334, 102, 101, 23);
		panel.add(btnDelSelected);
		
		btnDelSelected.setActionListener(IPStorage);
	}
}
