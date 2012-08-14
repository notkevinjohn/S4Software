#ifndef WirelessControl_H
#define WirelessControl_H

#include "Arduino.h"

class WirelessControl {
public:
        String parseStart(int command, int pin, int value);
        
private:
      //  void DigitalWrite(int pin, int value);

        
};
 
#endif
