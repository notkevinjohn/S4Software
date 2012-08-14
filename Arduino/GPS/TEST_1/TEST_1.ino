#include <SoftwareSerial.h>
#include <SD.h>

#define RXPIN 2
#define TXPIN 3
#define GPSBUAD 4800
SoftwareSerial uart_gps(RXPIN, TXPIN);
int count; 
int chipSelect =4;
byte gps;
String gpsString;
String gpsStringPass;
File dataFile;
//String arrayBuffer[10];
String sensorOne;
char gpsChar;


void sdDataWrite(String arrayBuffer[10], int &count);
void gpsSentence(char gpsChar, String &gpsString, String &gpsStringPass, int &count, File dataFile);
void dataBuffer(String arrayBuffer[10], int &count, String &gpsStringPass, String sensorOne);

void setup()
{
  int count; 
  count=0;
  pinMode(10, OUTPUT);
  uart_gps.begin(GPSBUAD);
 // Serial.begin(9800);
  gpsString = "";
  gpsStringPass= "";
  
   if (!SD.begin(chipSelect)){
  //  Serial.println("Card failed, not present");
    return;
  }
//  Serial.println("card initialized.");
  

}

void loop(){
  
  while (uart_gps.available())
  {
    
    gps = uart_gps.read();
    gpsChar = char(gps);
    if(gpsChar != '\n')
    {
        gpsString += gpsChar;
    }else{
        gpsString += ',';
        count++;
    }
    gpsSentence(gps, gpsString, gpsStringPass, count, dataFile);
  }
}

void gpsSentence(char gpsChar, String &gpsString, String &gpsStringPass, int &count, File dataFile)
{
  if(count > 3)
  {
 // Serial.println(gpsString);
   
    dataFile = SD.open("data.txt", FILE_WRITE);
    if(dataFile){
      dataFile.println(gpsString);
      dataFile.close();
      
      } else{
     // Serial.print("error opening ");
      }
      
      gpsString="";
      count =0;
    }
}


/*
void dataBuffer(String arrayBuffer[10], int &count, String &gpsStringPass, String sensorOne)
{
  if(count == 5)
  {
    arrayBuffer[0] = gpsStringPass;
    arrayBuffer[1] = sensorOne;
    
    
    sdDataWrite(arrayBuffer[10], &count);
  }
}


void sdDataWrite(String arrayBuffer[10], int &count)
{
  dataFile = SD.open("SensData.txt", FILE_WRITE);
  if(dataFile){
    for(int i = 0; i < 10; i++)
    {
    dataFile.print(arrayBuffer[i]);
    }
    dataFile.println("");
    dataFile.close();
    }else{
      Serial.print("ERROR OPENING SENDATA!!!");
    }
  count = 0;
}
  
 */
