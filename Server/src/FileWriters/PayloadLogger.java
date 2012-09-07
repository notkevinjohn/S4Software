package FileWriters;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PayloadLogger 
{
	private File directory;
	private String directoryLocation = "PayloadLog";
	
	
	public BufferedWriter payloadFile;
	public void payloadLogger(String deviceName)
	{
		createDirectoryIfNeeded();
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		Date date = new Date();
		
		String FilePath = "PayloadLog/PayloadLog_"+ dateFormat.format(date)+ "_" + deviceName +".txt";
		FileWriter fstream = null;
		
		try 
		{
			fstream = new FileWriter(FilePath);
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		payloadFile = new BufferedWriter(fstream);
	}
	
	public void recieveText(String returnString)
	{
		try 
		{
			returnString = returnString += '\n';
			payloadFile.write(returnString);
			payloadFile.flush();
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
