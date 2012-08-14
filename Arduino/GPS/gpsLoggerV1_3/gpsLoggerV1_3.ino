/*
Written By Kevin Zack
Sonoma State University
NASA E/PO
April 2012

NOTE pins 10, 11, 12, 13 are needed for SD cannot put anything on these pins!!!!

Avalible pins for sensors: 7,8,9 + analog

Keep file name under 8 charaters.
*/


#include <SoftwareSerial.h>
#include <SD.h>

#define RXPIN 2
#define TXPIN 3
#define powerPin 4
#define led1Pin 6
#define led2Pin 5
#define chipSelect 10
#define error 5
#define sdWriteLed 6
#define GPSBUAD 4800

SoftwareSerial uart_gps(RXPIN, TXPIN);

File dataFile;
int count; 
int count2;
int sensorOne;
byte gps;
char dummy;
String gpsString;
String sdDataWrite;
String start;

char buffer[90];
byte bufferIndex = 0;
byte fix = 0;
//void sdDataWrite(String &sdDataWrite);
//void gpsSentence(char gpsChar, String &gpsString, String &gpsStringPass, int &count, File dataFile);
//void dataBuffer(int &count, String &gpsString, String &sdDataWrite, String sensorOne);

uint8_t parseHex(char c) {
  if (c < '0')
    return 0;
  if (c <= '9')
    return c - '0';
  if (c < 'A')
    return 0;
  if (c <= 'F')
    return (c - 'A')+10;
}

void setup()
{
  int count; 
  count=0;
  pinMode(10, OUTPUT);
  pinMode(sdWriteLed, OUTPUT);
  pinMode(powerPin, OUTPUT);
  digitalWrite(powerPin, LOW);
  
  
  uart_gps.begin(GPSBUAD);
  Serial.begin(9800);
  gpsString = "";

  
   if (!SD.begin(chipSelect)){
    digitalWrite(error, HIGH);
    return;
  }

  strcpy(buffer, "GPSLOG00.TXT");
  for (int i = 0; i < 100; i++) {
    buffer[6] = '0' + i/10;
    buffer[7] = '0' + i%10;
    // create if does not exist, do not open existing, write, sync after write
    if (! SD.exists(buffer)) {
      break;
    }
  }
        
//  dataFile = SD.open(buffer, FILE_WRITE);
   
  uart_gps.print("$PSRF100,01,4800,08,01,00*0E\r\n");
  delay(250);
   uart_gps.print("$PSRF105,01*3E\r\n");
  delay(250);
  uart_gps.print("$PSRF103,00,00,01,01*25\r\n");
  delay(250);
  uart_gps.print("$PSRF103,01,00,01,01*27\r\n");
  delay(250);
  uart_gps.print("$PSRF103,02,00,00,01*26\r\n");
  delay(250);
  uart_gps.print("$PSRF103,03,00,00,01*27\r\n");
  delay(250);
  uart_gps.print("$PSRF103,04,00,01,01*20\r\n");
  delay(250);
  uart_gps.print("$PSRF103,05,00,00,01*21\r\n");
  delay(250);
  uart_gps.print("$PSRF151,00*3E\r\n");
    delay(250);
    
}

void loop(){
  char gpsChar;
  byte sum;
        
     
  if(uart_gps.available())
  {
    gpsChar = uart_gps.read();
    // Serial.println(gpsChar);
     
    if(bufferIndex == 0){
        while(gpsChar != '$'){
          gpsChar = uart_gps.read();
        }
   
    }

    
    buffer[bufferIndex] = gpsChar;
    
    digitalWrite(led2Pin, HIGH);

    if(gpsChar == '\n'){
   //   Serial.print(buffer);
      buffer[bufferIndex + 1] = 0;
      
      if (buffer[bufferIndex - 4] != '*'){
        Serial.print('*');
        bufferIndex = 0;
        return;
      }
 
     // Serial.println(bufferIndex);
    
      sum = parseHex(buffer[bufferIndex-3]) * 16;
      sum += parseHex(buffer[bufferIndex-2]);

      // check checksum
      for (int i=1; i < (bufferIndex-4); i++) {
        sum ^= buffer[i];
      }
      if (sum != 0) {
        //putstring_nl("Cxsum mismatch");
        Serial.print('~');
        bufferIndex = 0;
        return;
      }
    
     
      if (strstr(buffer, "GPRMC")) {
        // find out if we got a fix
        char *p = buffer;
        p = strchr(p, ',')+1;
        p = strchr(p, ',')+1; // skip to 3rd item

        if (p[0] == 'V') {
          digitalWrite(led1Pin, LOW);
          fix = 0;
        } else {
          digitalWrite(led1Pin, HIGH);
          fix = 1;
        }
      }
  
    Serial.print(buffer);
    Serial.print('#');
    
    bufferIndex++;
    
   // dataFile.write((byte *) buffer, bufferIndex);
  //  dataFile.flush();
    
    bufferIndex = 0;
    }
    bufferIndex++;
    if (bufferIndex == 90-1) {
       Serial.print('!');
       bufferIndex = 0;
    }
        
   //   sensor(sensorOne);
  // dataBuffer(count, gpsString, sdDataWrite, sensorOne, dataFile);
  }
 
}

void dataBuffer(int &count, String &gpsString, String &sdDataWrite, int &sensorOne, File &dataFile)
{
  if(count > 2)
  {
      sdDataWrite += gpsString;
      sdWrite(sdDataWrite, dataFile);
      Serial.println(sdDataWrite);
      
      sdDataWrite = "";
      gpsString = "";
      count = 0;
  }
}


void sdWrite(String &sdDataWrite, File &dataFile)
{
  dataFile = SD.open("Test1.txt", O_CREAT | O_WRITE | O_APPEND);
  digitalWrite(sdWriteLed, LOW);
  if(dataFile){
    dataFile.println(sdDataWrite); 
    dataFile.close();
    digitalWrite(sdWriteLed, HIGH);
    }else{
       digitalWrite(error, HIGH);
       digitalWrite(sdWriteLed, HIGH);
    }
}

//void sensor(int &sensorOne)
//{
//    sensorOne = analogRead(A0);
//}

