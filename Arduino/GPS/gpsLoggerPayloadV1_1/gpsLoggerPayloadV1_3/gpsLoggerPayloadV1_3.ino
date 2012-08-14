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
File dataFile;
String sdDataWrite;
int sensorOne;
char gpsChar;
int sdWriteLed = 8;
//unsigned long time;
byte c;
String gpsPassString;
#define BUFFSIZE 90
String buffer[5][BUFFSIZE];
String buff = buffer[5][BUFFSIZE];
uint8_t bufferidx = 0;


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
   Serial.begin(9600);
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
    if(gpsChar != '/n')
    {
        buffer[count][90] += gpsChar;
          Serial.println(buffer[count][90]);
    }else{
 
        count++;
    }
   // sensor(sensorOne);
  //  dataBuffer(count, gpsString, sdDataWrite, sensorOne, dataFile, buff);
  }
 
}


void dataBuffer(int &count, String &gpsString, String &sdDataWrite, int &sensorOne, File &dataFile, String buff)
{
  if(count > 4)
  {
    //  sdDataWrite += time;
     // sdDataWrite += ",";

      sdDataWrite += buffer[1][90];
      sdDataWrite += sensorOne;
      sdWrite(sdDataWrite, dataFile);
      Serial.println(buffer[1][90]);
      
      sdDataWrite = "";
      gpsString = "";
      count = 0;
  }
}



void sdWrite(String &sdDataWrite, File &dataFile)
{
  dataFile = SD.open("Test.txt", O_CREAT | O_WRITE | O_APPEND);
  digitalWrite(sdWriteLed, HIGH);
  if(dataFile){
    dataFile.println(sdDataWrite);
    //dataFile.flush(); 
    dataFile.close();
    digitalWrite(sdWriteLed, LOW);
    }else{
     //  Serial.print("ERROR OPENING SD");
    }
}

void sensor(int &sensorOne)
{
    sensorOne = analogRead(A0);
}
