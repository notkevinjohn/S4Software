package GUI;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import javax.swing.BoxLayout;

import Socket.SendName;

import Componets.Connection.DeviceConnectButton;
import Componets.Connection.RefreshButton;
import Data.Payload;

public class SendConnectionName extends JFrame
{

	private static final long serialVersionUID = -5571237008779560428L;
	private JScrollPane deviceScrollPane;
	private JPanel deviceNamePanel;
	private JPanel contentPane;
	public ArrayList<JCheckBox> selectArray = new ArrayList<JCheckBox>();
	public ArrayList<JTextField> deviceNames = new ArrayList<JTextField>();
	public ArrayList<String> deviceStringNames = new ArrayList<String>();
	public JFrame frame;
	public RefreshButton refreshButton;
	public DeviceConnectButton deviceConnectButton;
	public CheckboxGroup checkBoxGroup;
	public  Vector<Payload> payloadList;
	
	public SendConnectionName(SendName sendName) 
	{
		frame = new JFrame("Select Payload");
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setSize(455, 225);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		frame.setContentPane(contentPane);
		
		deviceScrollPane = new JScrollPane();
		deviceScrollPane.setBounds(10, 11, 246, 164);
		contentPane.add(deviceScrollPane);
		
		
		
		refreshButton = new RefreshButton();
		refreshButton.setBounds(335, 11, 89, 23);
		contentPane.add(refreshButton);
		refreshButton.setActionListener(sendName);
		
		deviceConnectButton = new DeviceConnectButton();
		deviceConnectButton.setBounds(335, 152, 89, 23);
		contentPane.add(deviceConnectButton);
		
		
		redrawDeviceNames(deviceStringNames);
		deviceConnectButton.setActionListener(sendName, checkBoxGroup);
		frame.repaint();
	}

	public void redrawDeviceNames(ArrayList<String> deviceStringNames)
	{
		deviceNamePanel = new JPanel();
		deviceScrollPane.setViewportView(deviceNamePanel);
		
		deviceScrollPane.setViewportView(deviceNamePanel);
		deviceNamePanel.setLayout(new BoxLayout(deviceNamePanel, BoxLayout.Y_AXIS));
		
		int deviceSize = 10;//deviceStringNames.size();
		
		checkBoxGroup = new CheckboxGroup();
		
		for(int i =0; i< deviceSize; i++)
		{
			String name = "hi";//deviceStringNames.get(i);
			deviceNamePanel.add(new Checkbox(name,checkBoxGroup,false));
		}
		
		
	}
	public void refreshPayloadList(Vector<Payload> payloadList)
	{
		this.payloadList = payloadList;
	}
}
