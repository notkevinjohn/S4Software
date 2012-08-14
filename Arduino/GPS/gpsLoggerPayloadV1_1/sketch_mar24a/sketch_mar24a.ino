#include <SoftwareSerial.h>
#include <SD.h>

#define RXPIN 2
#define TXPIN 3
#define GPSBUAD 4800
SoftwareSerial uart_gps(RXPIN, TXPIN);
int count; 
int chipSelect = 4;
byte gps;
String gpsString;
String gpsString0;
String gpsString1;
String gpsString2;
String gpsString3;
String gpsString4;
File dataFile;
String sdDataWrite;
int sensorOne;
char gpsChar;
int sdWriteLed = 8;
//unsigned long time;

//void sdDataWrite(String &sdDataWrite);
//void gpsSentence(char gpsChar, String &gpsString, String &gpsStringPass, int &count, File dataFile);
//void dataBuffer(int &count, String &gpsString, String &sdDataWrite, String sensorOne);

void setup()
{
  int count; 
  count=0;
  pinMode(10, OUTPUT);
  pinMode(sdWriteLed, OUTPUT);
  
  uart_gps.begin(GPSBUAD);
   Serial.begin(9800);
  gpsString = "";
  
   if (!SD.begin(chipSelect)){
    //Serial.println("Card failed, not present");
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
  //  sensor(sensorOne);
    dataBuffer(count, gpsString, gpsString0, gpsString1, gpsString2, gpsString3, gpsString4, sdDataWrite, sensorOne, dataFile);
  }
}

void dataBuffer(int &count, String &gpsString, String &gpsString0,String &gpsString1,String &gpsString2,String &gpsString3,String &gpsString4, String &sdDataWrite, int &sensorOne, File &dataFile)
{
  switch (count)
  {
    case 0:
    gpsString = gpsString0;
    
    gpsString = "";
    break;
    case 1:
    gpsString = gpsString1;
    gpsString = "";
    break;
    case 2:
    gpsString = gpsString2;
    gpsString = "";
    break;
    case 3:
    gpsString = gpsString3;
    gpsString = "";
    break;
    case 4:
    gpsString = gpsString4;
    gpsString = "";
    break;
  }
    gpsString = "";
    
  if(count > 4)
  {
    //  sdDataWrite += time;
     // sdDataWrite += ",";

      sdDataWrite += gpsString0;
      sdDataWrite += gpsString1;
      sdDataWrite += gpsString2;
      sdDataWrite += gpsString3;
      sdDataWrite += gpsString4;
   //   sdDataWrite += sensorOne;
      sdWrite(sdDataWrite, dataFile);
      Serial.println(sdDataWrite);
      
      sdDataWrite = "";
      gpsString = "";
      count = 0;
  }
}



void sdWrite(String &sdDataWrite, File &dataFile)
{
  dataFile = SD.open("Test2.txt", O_CREAT | O_WRITE | O_APPEND);
  digitalWrite(sdWriteLed, HIGH);
  if(dataFile){
    dataFile.println(sdDataWrite);
    //dataFile.flush(); 
    dataFile.close();
    digitalWrite(sdWriteLed,LOW);
    }else{
     //  Serial.print("ERROR OPENING SD");
    }
}

//void sensor(int &sensorOne)
//{
//    sensorOne = analogRead(A0);
//}
