
#include <SoftwareSerial.h>

#define RXPIN 2
#define TXPIN 3
#define GPSBUAD 4800
SoftwareSerial uart_gps(RXPIN, TXPIN);

byte gps;
int count;
long unsigned time;
char charGps;

void setup()
{
  Serial.begin(9600);
  uart_gps.begin(GPSBUAD); 
}

void loop()
{
time = millis();

  while(uart_gps.available())
  {
     gps = uart_gps.read();
    // Serial.write(gps);
     charGps = char(gps);
     
     if(charGps == '$')
     {
       Serial.println(time);
     }  
   }

}

