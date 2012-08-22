#include <SoftwareSerial.h>
int time =0;
SoftwareSerial gpsSerial = SoftwareSerial(2, 3);
#define GPSRATE 4800
void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  gpsSerial.begin(GPSRATE);
}

void loop() {
  // put your main code here, to run repeatedly: 
  if(gpsSerial.available())
  {
    Serial.println(gpsSerial.read());
  }
  
}
