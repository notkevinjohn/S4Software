#include "WiFly.h"
#include <SoftwareSerial.h>
unsigned long time;
boolean start = false;
long previousMillis = 0;
long interval = 1000;
String DeviceName = "SSU-01";
SoftwareSerial slaveSerial = SoftwareSerial(2, 3);
void setup() {
  
  Serial.begin(9600);
  slaveSerial.begin(9600);
  WiFly.begin();
  WiFly.JoinRouter("DeviceTesting");
}

void loop()
{
  unsigned long currentMillis = millis();
  
  if(start)
  {
    time = currentMillis/1000;
    while(SpiSerial.available() > 0) 
    {
      char tempChar = SpiSerial.read();
      if(tempChar != '#')
      {
         Serial.print(tempChar);
      }
      else
      {
        SpiSerial.print("Pong");
        delay(100); // handel this differently used to keep from over running out
      }
        
    }
    
     while(slaveSerial.available()) 
     {
      SpiSerial.write(slaveSerial.read());
     }   
  }
  
  else
  {
     if(currentMillis - previousMillis > interval) 
     {
        previousMillis = currentMillis;   
        SpiSerial.println("open");
        delay(100);
     }
     
    while(SpiSerial.available() > 0) 
    {
        char tempChar = SpiSerial.read();
        Serial.write(tempChar);
        if(tempChar == '#')
        {
         SpiSerial.print("DeviceName");
         SpiSerial.println(DeviceName);
        }
        else if(tempChar == '@')
        {
          delay (1000);
          start = true;
        }
        else
        {
        }
    }
  }
}

