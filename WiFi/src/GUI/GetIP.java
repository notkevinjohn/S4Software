package GUI;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import java.net.Socket;
import java.util.ArrayList;
import Componets.Connection.ConnectButton;
import Componets.Connection.EditIPButton;
import Data.IPData;


public class GetIP extends JFrame 
{
	private static final long serialVersionUID = -44419473300948763L;
	private EditIPButton btnEdit;
	public JFrame frame;
	private ConnectButton btnConnect;
	public static javax.swing.event.EventListenerList listenerList = new javax.swing.event.EventListenerList();
	public String ip;
	public int port;
	public String receve;
	public Socket socket;
	public ArrayList<IPData> IPStorage;
	public JComboBox<String> TCPcomboBox;
	
	public GetIP(ArrayList<IPData> _IpStorage) 
	{
		IPStorage = _IpStorage;
		try 
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } 
	    catch (UnsupportedLookAndFeelException e) { }
	    catch (ClassNotFoundException e) {}
	    catch (InstantiationException e) {}
	    catch (IllegalAccessException e) {}
		
		setIP();
	}
	
	public void setIP() 
	{
		
		frame = new JFrame("Connect to IP");
		frame.setVisible(true);
		frame.setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 357, 76);
		
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);

		getIPcomboBox(IPStorage);
		
		btnConnect = new ConnectButton(frame, TCPcomboBox);
		btnConnect.setBounds(244, 10, 95, 23);
		contentPane.add(btnConnect);
		btnConnect.setActionListener(IPStorage);
		
		btnEdit = new EditIPButton(this, TCPcomboBox, IPStorage);
		btnEdit.setBounds(149, 10, 64, 23);
		contentPane.add(btnEdit);
		btnEdit.setActionListener(IPStorage);
		
		frame.setContentPane(contentPane);
	}
	
	public void getIPcomboBox(ArrayList<IPData> _IPStorage)
	{
		IPStorage = _IPStorage;
		
		TCPcomboBox = new JComboBox<String>();
		TCPcomboBox.setBounds(10, 11, 129, 20);
		
		for(int i = 0; i < IPStorage.size(); i++)
		{
			TCPcomboBox.addItem(IPStorage.get(i).name);
		}
		
		frame.getContentPane().add(TCPcomboBox);
	}
}
