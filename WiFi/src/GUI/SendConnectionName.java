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
import Data.TerminalPayloadList;

public class SendConnectionName extends JFrame
{

	private static final long serialVersionUID = -5571237008779560428L;
	private JScrollPane deviceScrollPane;
	private JPanel deviceNamePanel;
	private JPanel contentPane;
	private SendName sendName;
	public ArrayList<JCheckBox> selectArray = new ArrayList<JCheckBox>();
	public ArrayList<JTextField> deviceNames = new ArrayList<JTextField>();
	public ArrayList<String> deviceStringNames = new ArrayList<String>();
	public JFrame frame;
	public RefreshButton refreshButton;
	public DeviceConnectButton deviceConnectButton;
	public CheckboxGroup checkBoxGroup;
	public Vector<TerminalPayloadList> payloadListVector;
	
	public SendConnectionName(SendName sendName) 
	{
		this.sendName = sendName;
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
		
		
		
		
		frame.repaint();
		if(payloadListVector != null)
		{
		redrawDeviceNames(deviceStringNames);
		}
	}

	public void redrawDeviceNames(ArrayList<String> deviceStringNames)
	{
		deviceNamePanel = new JPanel();
		deviceScrollPane.setViewportView(deviceNamePanel);
		
		deviceScrollPane.setViewportView(deviceNamePanel);
		deviceNamePanel.setLayout(new BoxLayout(deviceNamePanel, BoxLayout.Y_AXIS));
		
		int deviceSize = payloadListVector.size();
		
		checkBoxGroup = new CheckboxGroup();
		
		for(int i =0; i< deviceSize; i++)
		{
			String name = payloadListVector.get(i).deviceName;
			deviceNamePanel.add(new Checkbox(name,checkBoxGroup,false));
		}
		deviceConnectButton.setActionListener(sendName, checkBoxGroup);
		
	}
	public void refreshPayloadList(Vector<TerminalPayloadList> payloadListVector)
	{
		this.payloadListVector = payloadListVector;
		redrawDeviceNames(deviceStringNames);
		
	}
}
