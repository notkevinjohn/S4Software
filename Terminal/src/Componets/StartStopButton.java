package Componets;

import javax.swing.JButton;

public class StartStopButton extends JButton 
{
	private static final long serialVersionUID = 6374099069494291211L;
	private boolean actionListenerSet = false;

	public StartStopButton()
	{
		super();	
		this.setName("StartStopButton");
		this.setText("Stop");
	}	
	
	public void setActionListener ()
	{
		if(!actionListenerSet)
		{
			this.addActionListener( new StartStopButtonActionListener(this));
			actionListenerSet = true;
		}
	}
}




