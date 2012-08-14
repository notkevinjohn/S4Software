#ifndef WirelessControl_H
#define WirelessControl_H

#include <Arduino.h"> 

class WirelessControl {
public:
        String parseStart(int &command, int &pin, int &value, String &returnString);
private:
        void digitalWrite(int pin, int Value, String &returnString);
        
        void parseCommand(String stringIn, String &returnString);
        void parseDigitalPin(String digitalOpperation, String &returnString);
        void parseAnalogPin(analogOpperation, String &returnString);
        
};
 
#endif
