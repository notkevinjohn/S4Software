package GUI;

import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import Componets.Connection.ConnectButton;
import Componets.Connection.EditIPButton;
import Data.IPData;


public class GetIP extends JFrame 
{
	private static final long serialVersionUID = -44419473300948763L;
	private EditIPButton btnEdit;
	private ConnectButton btnConnect;
	public static javax.swing.event.EventListenerList listenerList = new javax.swing.event.EventListenerList();
	public JFrame frame;
	public ArrayList<IPData> IPStorage;
	public JComboBox<String> TCPcomboBox;
	public JComboBox<String> SimComboBox;
	
	public GetIP(ArrayList<IPData> _IpStorage) 
	{
		try 
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } 
	    catch (UnsupportedLookAndFeelException e) { }
	    catch (ClassNotFoundException e) {}
	    catch (InstantiationException e) {}
	    catch (IllegalAccessException e) {}
		
		IPStorage = _IpStorage;
		setIP();
	}
	
	public void setIP() 
	{
		
		frame = new JFrame("Connect to IP");
		frame.setVisible(true);
		frame.setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 465, 95);
		
		JPanel contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);

		getIPcomboBox(IPStorage);
		
		SimComboBox = new JComboBox<String>();
		SimComboBox.setBackground(Color.WHITE);
		SimComboBox.setBounds(223, 34, 121, 20);
		contentPane.add(SimComboBox);
		SimComboBox.addItem("Name and Time");
		SimComboBox.addItem("RockSim");
		
		btnConnect = new ConnectButton(frame, TCPcomboBox,SimComboBox);
		btnConnect.setBounds(354, 33, 95, 23);
		contentPane.add(btnConnect);
		btnConnect.setActionListener(IPStorage);
		
		btnEdit = new EditIPButton(this, TCPcomboBox, IPStorage);
		btnEdit.setBounds(149, 31, 64, 23);
		contentPane.add(btnEdit);
		btnEdit.setActionListener(IPStorage);
		
		
		
		JLabel lblPayloadName = new JLabel("Payload Name");
		lblPayloadName.setBounds(10, 11, 129, 14);
		contentPane.add(lblPayloadName);
		
		JLabel lblSimulationType = new JLabel("Simulation Type");
		lblSimulationType.setBounds(223, 9, 121, 14);
		contentPane.add(lblSimulationType);
	
		frame.setContentPane(contentPane);
		
		frame.addWindowListener(new WindowListener() // Handle differently
		{
			@Override
			public void windowActivated(WindowEvent arg0) {}
			@Override
			public void windowClosed(WindowEvent arg0)
			{
			}
			@Override
			public void windowDeactivated(WindowEvent arg0) {}
			@Override
			public void windowDeiconified(WindowEvent arg0) {}
			@Override
			public void windowIconified(WindowEvent arg0) {}
			@Override
			public void windowOpened(WindowEvent arg0) {}
			@Override
			public void windowClosing(WindowEvent e) 
			{
				System.exit(0);
			}
		});
	}
	
		
	public void getIPcomboBox(ArrayList<IPData> _IPStorage)
	{
		IPStorage = _IPStorage;
		
		TCPcomboBox = new JComboBox<String>();
		TCPcomboBox.setBackground(Color.WHITE);
		TCPcomboBox.setBounds(10, 34, 129, 20);
		
		for(int i = 0; i < IPStorage.size(); i++)
		{
			TCPcomboBox.addItem(IPStorage.get(i).name);
		}
		
		frame.getContentPane().add(TCPcomboBox);
	}
}
