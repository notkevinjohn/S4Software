
#include <SoftwareSerial.h>

#define RXPIN 2
#define TXPIN 3
#define GPSBUAD 9600
SoftwareSerial uart_gps(RXPIN, TXPIN);

byte gps;


void setup()
{
  Serial.begin(9600);
  uart_gps.begin(GPSBUAD); 
}

void loop()
{

  while(uart_gps.available())
  {
     gps = uart_gps.read();
     Serial.write(gps);
  }

}

