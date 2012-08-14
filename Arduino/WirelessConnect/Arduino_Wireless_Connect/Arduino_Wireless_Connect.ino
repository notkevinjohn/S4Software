#include "WiFly.h"
unsigned long time;
void setup() {
  
  Serial.begin(9600);

  WiFly.begin();
  
  WiFly.AddAdhoc();
  Serial.println("geting ip");
//  Serial.println(WiFly.ip());
  Serial.println("Starting program");
 
}

void loop()
{
  time = millis()/1000;
  while(SpiSerial.available() > 0) {
    Serial.write(SpiSerial.read());
  }
    SpiSerial.println(time);
    delay(1000);
}

