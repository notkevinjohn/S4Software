#include <SoftwareSerial.h>
#include <SD.h>

#define RXPIN 2
#define TXPIN 3
#define GPSBUAD 4800
int chipSelect = 4;
int sdWriteLed = 8;
int error = 9;
int count; 
int sensorOne;
String sdDataWrite;
String gpsString;
File dataFile;
byte gps;
char gpsChar;

SoftwareSerial uart_gps(RXPIN, TXPIN);

void setup()
{
  pinMode(10, OUTPUT);
  pinMode(sdWriteLed, OUTPUT);
  pinMode(error, OUTPUT);
  
  uart_gps.begin(GPSBUAD);
  //Serial.begin(9800);
  
  gpsString = "";
  count=0;
  
   if (!SD.begin(chipSelect)){
    digitalWrite(error, HIGH);
    return;
  }
}

void loop()
{
  while (uart_gps.available() > 0 && count < 6)
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
  }
  
  if(count ==6){
     //Serial.println(gpsString);
    
     sdWrite(gpsString, dataFile);
     
     gpsString = "";
     count =0;
  }
}

void sdWrite(String &gpsString, File &dataFile)
{
  dataFile = SD.open("Test.txt", O_CREAT | O_WRITE | O_APPEND);
  
  digitalWrite(sdWriteLed, LOW);
  
  if(dataFile){
    dataFile.println(gpsString);
    dataFile.close();
    digitalWrite(sdWriteLed, HIGH);
    }else{
    digitalWrite(error, HIGH);
    }
}

