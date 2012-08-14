
#include "WirelessControl.h"

String WirelessControl::parseStart(int command, int pin, int value)
{
       Serial.println(command);
       Serial.println(pin);
       Serial.println(value);
     if(command == 1)
     {
        DigitalWrite(pin, value);     
     }
}

void WirelessControl::DigitalWrite(int pin, int value)
{
      digitalWrite(pin, HIGH);
      /*
     if(value == 1)
     {
         //digitalWrite(pin, Value) 
         digitalWrite(pin, HIGH);
     }
     else if(value == 0)
     {
          digitalWrite(pin, value) 
         // digitalWrite(pin,LOW);
     }
     */
}







