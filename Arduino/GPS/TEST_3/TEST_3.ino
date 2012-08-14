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
String sdStringWrite;
File dataFile;
//String arrayBuffer[10];
String sensorOne;
char gpsChar;


void sdDataWrite(String &sdStringWrite);
void gpsSentence(char gpsChar, String &gpsString, String &gpsStringPass, int &count, File dataFile);
void dataBuffer(int &count, String &gpsString,  String &gpsStringPass, String &sdStringWrite,  String sensorOne, File dataFile);

void setup()
{
  int count; 
  count=0;
  pinMode(10, OUTPUT);
  uart_gps.begin(GPSBUAD);
  Serial.begin(9800);
  gpsString = "";
  gpsStringPass= "";
  sdStringWrite = "";
  
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
    dataBuffer(count, gpsString, gpsStringPass, sdStringWrite, sensorOne, dataFile);
  }
}
/*
void gpsSentence(char gpsChar, String &gpsString, String &gpsStringPass, int &count, File dataFile)
{
  if(count > 3)
  {
    gpsStringPass = gpsString;
  Serial.println(gpsStringPass);
   
    dataFile = SD.open("data.txt", FILE_WRITE);
    if(dataFile){
      dataFile.println(gpsString);
      dataFile.close();
      
      } else{
     // Serial.print("error opening ");
      }
      
      gpsString="";
    }
}

*/

void dataBuffer(int &count, String &gpsString,  String &gpsStringPass, String &sdStringWrite,  String sensorOne, File dataFile)
{
  if(count > 4)
  {
   sdStringWrite = gpsString + sensorOne; //// used to add more strings
    
   //Serial.println(gpsString);      // used to test
   //Serial.println(gpsStringPass);  // used to test
   //Serial.println(sdStringWrite);  // used to test

   sdDataWrite(sdStringWrite, dataFile);
   
   gpsString = "";
   gpsStringPass = "";
   sdStringWrite = "";
   count = 0;
   
  }
}

void sdDataWrite(String &sdStringWrite, File dataFile)
{
  dataFile = SD.open("SData.txt", FILE_WRITE);
  if(dataFile){
    Serial.println(sdStringWrite);
    dataFile.println(sdStringWrite);
    dataFile.close();
    
    }else{
      Serial.print("ERROR");
    }
}
  

