#include "WiFly.h"

/************Information Needed!***********************/
const char *Device_Name = "SSU-01";
//const char *Router_Name = "DeviceTesting";
const char *Router_Name = "Zoe";
boolean is_Wep = true;
///const char *Router_Key = "ac8afe4e5fb3931264f5df2f6f";
const char *Router_Key = "DE4992FA62EB5F4121468B66A6d";
void setup() 
{
  Serial.begin(9600);
  WiFly.setup(Device_Name, Router_Name, is_Wep, Router_Key);
  //WiFly.begin();
  //WiFly.JoinRouter(Router_Name);
}

void loop()
{
  while(SpiSerial.available() > 0) 
  {
     Serial.write(SpiSerial.read());
  }
   if(Serial.available()) 
   {
    SpiSerial.write(Serial.read());
   }
}

