package Parcers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import Data.IPData;


public class IPAdder {	
	private File directory;
	private File file;
	private String directoryLocation = "IP";
	private String filePath = "IP/IPCONFIG.txt";
	public List<String> records;
	public ArrayList<IPData> IpStorage;

	public ArrayList<IPData> ProcessFile()
	{	
		 List<String> records; 
		 createDirectoryIfNeeded();
   	  try
         {
   		  	
             records = readFileAsListOfStrings(filePath);        
             readStringAsListOfElements(records);              
         }
         catch (Exception error){}
   	  return IpStorage;
	}
	
	 public  List<String> readFileAsListOfStrings(String filePath) throws Exception
     {
         List<String> records = new ArrayList<String>(); 
         BufferedReader reader = new BufferedReader(new FileReader(filePath));    
         String line;
         
         while ((line = reader.readLine()) != null)       
         {
           records.add(line);								
         }
         reader.close();  
        
         return records;          
     }
	 
	 
	 public  void readStringAsListOfElements(List<String> records) throws Exception
     {
		  ArrayList<ArrayList<String>> elements = new ArrayList<ArrayList<String>>(); // instantiating elements as a array of an array of strings
    	  ArrayList<String> element =  new ArrayList<String>();                         // instantiation elements as an array of strings
    	  for(int i=0 ; i < records.size(); i++)                                     // run through every array element of the records
    	  {
    		  String currentRecord = records.get(i);		    		  
    		  element = new ArrayList<String>();								  // initiate element
    		  String value = "";												  // initiate value
	    		 
	    	  for(int j = 0; j < currentRecord.length(); j++)					  // run through a charater at a time until the end of currentRecord
	    	  {																	  
	    		 char ch = currentRecord.charAt(j);								  // define the ch as the char at the jth position of currentRecord
	    		 if(ch != ',')													  // if the ch is not a comma then concatinate it to the String value
	    		 {
	    			 if(j != currentRecord.length()-1)							  // if the jth ch is not the end of the currentRecord string the concatinate the ch to value
		    		 {
		    			 value += ch;												  
		    		 }
	    			 else
	    			 {
	    				 value += ch;
	    				 element.add(value);										  // end with adding the value to the elment Array List
		    			 value = "";
	    			 }
	    		 }  
	    		 else 
	    		 {
	    		 	element.add(value);											  // if it is not the last or it is not a comma the add the value to the element
	    		 	value = "";													  // i.e. if it is in the middle of the string
	    		 }
	    	  }
	    	  elements.add(element);	
    	  }
    	  parseData(elements);	
     }
	 
	  public  void parseData(ArrayList<ArrayList<String>> elements) 
      {
    	  ArrayList<IPData> data = new ArrayList<IPData>();
    	    for(int i = 0; i < elements.size(); i++)
    	    {
    	    	IPData ipData = new IPData();
    	    	ipData.name = elements.get(i).get(0);
    	    	ipData.ip = elements.get(i).get(1);
    	    	ipData.port = Integer.parseInt(elements.get(i).get(2));
    	    	data.add(ipData);
    	    }
    	    IpStorage = data;
    	    
      }
	  
	  private void createDirectoryIfNeeded()
	  {
		  directory = new File(directoryLocation);

		  if (!directory.exists())
		  {
			  directory.mkdir();

		      file = new File(filePath);
		      if (!file.exists())
		 	  {
		    	    try 
		 	     	{
		    	    	file.createNewFile();
		 	     	} 
		    	    catch (IOException e) 
					{
						e.printStackTrace();
					}
		 	  }
		      else
		      {
		    	  file = new File(filePath);
		    	  if (!file.exists())
		    	  {
		    		  try 
		    		  {
		    			  	file.createNewFile();
		    		  } catch (IOException e) 
		    		  {
		    			  e.printStackTrace();
		    		  }
		    	  }
		      }
		  }
		  
	  }
}
