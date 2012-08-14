
#include "WirelessControl.h"

String parseStart(int &command, int &pin, int &value, String &returnString)
{
       
       Serial.println(command);
       Serial.println(pin);
       Serial.println(value);
     if(command == 1)
     {
        digitalWrite(pin, Value, returnString)        
     }
     
     return returnString;
}

void digitalWrite(int pin, int Value, String &returnString)
{
     if(Value == 1)
     {
         DigitalWrite(pin,HIGH);
     }
     else if(Value == 0)
     {
          DigitalWrite(pin,LOW);
     }
     returnString ==d "AOK";
}



void parseCommand(String stringIn, String &returnString)
{
     if(strinIn.size() == 12)
     {
         String io = stringIn.subst(2,2);
         if(io.compare("dp") == 0)    // if Io is digital pin
         {
               String digitalOpperation = stringIn.subst(4,8);
               parseDigitalPin(digitalOpperation, returnString);
         }
         else if(io.compare("ap") == 0
         {
              String analogOpperation = stringIn.subst(4,8);
              parseAnalogPin(analogOpperation, returnString);
         }
     }
     else
     {
         Serial.println("not a valid command");//ToDo: not a valid command!!
     }                    
}

void parseDigitalPin(String digitalOpperation, String &returnString)
{
     int pinNumber;
     
     if(digitalOpperation.subst(0,2).compare("dw") == 0)
     {
         pinNumber = atoi(digitalOpperation.subst(2,2));
         if(digitalOpperation.subst(4,4).compare("0001") == 0)
         {
             DigitalWrite(pinNumber,HIGH);
             returnString = "AOK";
         }
         else if(digitalOpperation.subst(4,4).compare("0000") == 0)
         {
              DigitalWrite(pinNumber,LOW);
              returnString = "AOK";
         }
         else
         {
              Serial.println("not a valid command");//ToDo: not a valid command!!
         }
     }
     else if(digitalOpperation.subst(0,2).compare("dr") == 0)
     {
         pinNumber = atoi(digitalOpperation.subst(2,2));
         returnString = (String)DigitalRead(pinNumber);
         }
         else
         {
              Serial.println("not a valid command");//ToDo: not a valid command!!
         }
     }
}

void parseAnalogPin(analogOpperation, String &returnString)
{
     int pinNumber;
     
     if(analogOpperation.subst(0,2).compare("aw") == 0)
     {
         pinNumber = atoi(analogOpperation.subst(2,2));
         AnalogWrite(pinNumber, atoi(digitalOpperation.subst(4,4)));
         returnString = "AOK";dd
     }
     else if(analogOpperation.subst(0,2).compare("ar") == 0)
     {
          returnString = AnalogRead(pinNumber);
     }
     else
     {
             Serial.println("not a valid command"); //ToDo: not a valid command!!
     }
}
















