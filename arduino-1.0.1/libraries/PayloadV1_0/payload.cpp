#include "payload.h"
#include <SoftwareSerial.h>
#include <SD.h>

#define RXPIN 2
#define TXPIN 3
#define GPSBUAD 4800

SoftwareSerial uart_gps(RXPIN,TXPIN);

int count;
int chipSelect = 4;
byte gps;
char gpsChar;
File dataFile;
String gpsString;
String sdDataWrite;
String sensorOne;
unsigned long time;
