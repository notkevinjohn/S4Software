#include <SD.h>
#include <Wire.h>


#define chipSelect 10

#define BUFFSIZE 90
char buffer[BUFFSIZE];
uint8_t bufferidx = 0;
uint8_t fix = 0; // current fix data
uint8_t i;
File logfile;
String tempData;
boolean print = false;

void setup()
{
  Wire.begin(2);                // join i2c bus with address #4
  Serial.begin(9600);           // start serial for output
  pinMode(10, OUTPUT);
  if (!SD.begin(chipSelect)) 
  {
    Serial.println("Card init. failed!");
  }
  strcpy(buffer, "GPSLOG00.TXT");
  for (i = 0; i < 100; i++) {
    buffer[6] = '0' + i/10;
    buffer[7] = '0' + i%10;
    // create if does not exist, do not open existing, write, sync after write
    if (! SD.exists(buffer)) {
      break;
    }
  }
  
   logfile = SD.open(buffer, FILE_WRITE);
  if( ! logfile ) {
    Serial.print("Couldnt create "); Serial.println(buffer);
  }
  Serial.print("Writing to "); Serial.println(buffer);
  
  
}

void loop()
{
  while(Wire.available() > 0) // loop through all but the last
  {
    char c = Wire.read(); // receive byte as a character
    tempData += c;
    Serial.print(c);         // print the character
    print = true;
  }
  if(print)
  {
      logfile.println(tempData);
      logfile.flush();
      Serial.println(tempData);
  }
  else
  {
    delay(100);
    Serial.println("Nothing to do");
  }
}
