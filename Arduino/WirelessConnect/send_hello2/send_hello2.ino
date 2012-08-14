#include "WiFly.h"
unsigned long time;
boolean start = false;
void setup() {
  
  Serial.begin(9600);

  WiFly.begin();
  WiFly.JoinRouter("Zoe");
 // WiFly.AddAdhoc();
  Serial.println("geting ip");
  //Serial.println(WiFly.ip());
//  Serial.println(WiFly.ip());
  Serial.println("Starting program");
}

void loop()
{
  if(start)
  {
    time = millis()/1000;
    while(SpiSerial.available() > 0) {
      char tempChar = SpiSerial.read();
      if(tempChar != '#')
      {
         Serial.print(tempChar);
      }
      else
      {
        //SpiSerial.print("Pong");
        delay(10);
      }
        
    }
     while(Serial.available()) 
     {
      SpiSerial.write(Serial.read());
     }
      SpiSerial.print("Hello ");
      SpiSerial.println(time);
      delay(1000);
  }
  else
  { 
    SpiSerial.println("open");
    while(SpiSerial.available() > 0) {
      char tempChar = SpiSerial.read();
      if(tempChar == '#')
      {
        start = true;
      }
    }
    }
  
}

