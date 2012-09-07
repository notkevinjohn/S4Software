package FileWriters;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WiFiWriter 
{
	private File directory;
	private String directoryLocation = "Log";
	
	
	public BufferedWriter wiFiFile;
	public WiFiWriter()
	{
		createDirectoryIfNeeded();
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		Date date = new Date();
		
		String FilePath = "Log/Log_"+dateFormat.format(date)+".txt";
		FileWriter fstream = null;
		
		try 
		{
			fstream = new FileWriter(FilePath);
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		wiFiFile = new BufferedWriter(fstream);
	}
	
	public void recieveText(String returnString)
	{
		try 
		{
			wiFiFile.write(returnString);
			wiFiFile.flush();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	 private void createDirectoryIfNeeded()
	  {
		  directory = new File(directoryLocation);
		  
		  if (!directory.exists())
		  {
			  directory.mkdir();
		  }
	  }

}
