package Parcers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import Data.IPData;

public class RockSimParcer 
{
	private String filePath = "RockSim/RockSim.csv";
	private File directory;
	private String directoryLocation = "RockSim";
	public List<String> records;
	public ArrayList<IPData> IpStorage;
	public double priorTime = 0;
	public double time = 0;
	public ArrayList<String> gpsData;
	
	public ArrayList<String> parcer()
	{
		 createDirectoryIfNeeded();
		 try 
		 {
			records = readFileAsListOfStrings(filePath);
			readStringAsListOfElements(records);
			System.out.println("done");
		 } 
		 catch (Exception e) 
		 {
			e.printStackTrace();
		 }    
		 return gpsData;
	}
	
	 public  List<String> readFileAsListOfStrings(String filename) throws Exception
     {
         List<String> records = new ArrayList<String>(); 
         BufferedReader reader = new BufferedReader(new FileReader(filename)); 
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
    	  ArrayList<String> element =  new ArrayList<String>();    
    	  
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
	    	  if(elements.size() >0)
	    	  {
	    		  time = Double.parseDouble(element.get(0));
		    	  if(time-priorTime > 1)
		    	  {
		    		  elements.add(element);
		    		  priorTime = time;
		    	  }
	    	  }
	    	  else
	    	  {
	    		  elements.add(element);
	    	  }
    	  }
    	  
    	  
    	  System.out.println("done");
    	  convertToGPS(elements);
      }
	  
	  public void convertToGPS(ArrayList<ArrayList<String>> elements)
	  {
		  gpsData = new ArrayList<String>();
		  
		  DecimalFormat tf = new DecimalFormat(".###"); 
		  DecimalFormat lf = new DecimalFormat(".####"); 
		  DecimalFormat af = new DecimalFormat(".#"); 
		  
		  for(int i = 1; i < elements.size(); i++)
		  {
			  String gpsString = "$GPGGA," + tf.format(100000.000+Double.parseDouble(elements.get(i).get(0))) + ","+lf.format(3820.3824 + 1/(1855*3.28)*Double.parseDouble(elements.get(i).get(3))) +",N,12240.6673,W,1,06,1.3,"+af.format(43.58 + Double.parseDouble(elements.get(i).get(2))*.3048)+",M,-25.1,M,,0000*62";
			  gpsData.add(gpsString);
			  
			  String sensorString = "@,&," + Double.parseDouble(elements.get(i).get(1));
			  gpsData.add(sensorString);
		  }
	  }
	  
	  private void createDirectoryIfNeeded()
	  {
		  directory = new File(directoryLocation);

		  if (!directory.exists())
		  {
			  directory.mkdir();
		  }
		 File file = new File(filePath);
		  if (!file.exists())
		  {
			  System.out.println("FileNotFound"); // bring up window!!!
		  }
		  
	  }
}



















