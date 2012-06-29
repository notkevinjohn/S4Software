package Parcers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import Data.IPData;

public class FileUpdater 
{
	
	public ArrayList<IPData> IPStorage;
	public String fileLocation = "IP/IPCONFIG.txt";
	
	public FileUpdater(ArrayList<IPData> _IPStorage)
	{
		IPStorage = _IPStorage;
		FileWriter fstream;
		try {
			fstream = new FileWriter(fileLocation);
			BufferedWriter IPConfig = new BufferedWriter(fstream);
			
			for(int i = 0; i < IPStorage.size(); i++)
			{
				IPConfig.write(IPStorage.get(i).name + ",");
				IPConfig.write(IPStorage.get(i).ip + ",");
				String _port = Integer.toString(IPStorage.get(i).port);
				IPConfig.write(_port);
				IPConfig.newLine();
			}
			IPConfig.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		
	}
}
