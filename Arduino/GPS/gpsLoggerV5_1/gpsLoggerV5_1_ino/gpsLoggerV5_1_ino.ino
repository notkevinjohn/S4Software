#include <avr/sleep.h>
#include "GPSconfig.h"
#include <stdio.h>
#include <Wire.h>
#include "WiFly.h"
#include <SoftwareSerial.h>


// power saving modes
#define SLEEPDELAY 0
#define TURNOFFGPS 0
#define LOG_RMC_FIXONLY 0

// what to log
#define LOG_RMC 0 // RMC-Recommended Minimum Specific GNSS Data, message 103,04
#define LOG_GGA 1 // GGA-Global Positioning System Fixed Data, message 103,00
#define LOG_GLL 0 // GLL-Geographic Position-Latitude/Longitude, message 103,01
#define LOG_GSA 0 // GSA-GNSS DOP and Active Satellites, message 103,02
#define LOG_GSV 0 // GSV-GNSS Satellites in View, message 103,03
#define LOG_VTG 0 // VTG-Course Over Ground and Ground Speed, message 103,05


SoftwareSerial gpsSerial = SoftwareSerial(2, 3);
SoftwareSerial microSerial = SoftwareSerial(5, 4);

#define GPSRATE 4800
#define MICRORATE 9600
#define powerPin 6
#define BMP085_ADDRESS 0x77

String DeviceName = "SSU-01";
unsigned long time;
boolean start = false;
long previousMillis = 0;
long interval = 1000;

int ac1;
int ac2; 
int ac3; 
unsigned int ac4;
unsigned int ac5;
unsigned int ac6;
int b1; 
int b2;
int mb;
int mc;
int md;
long b5; 
short temperature;
long pressure;
String data;

  // I2C address of BMP085
const unsigned char OSS = 0;  // Oversampling Setting


#define BUFFSIZE 90
char buffer[BUFFSIZE];
uint8_t bufferidx = 0;
uint8_t fix = 0; // current fix data
uint8_t i;

// read a Hex value and return the decimal equivalent
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

// blink out an error code
void error(uint8_t errno) {
/*
if (SD.errorCode()) {
putstring("SD error: ");
Serial.print(card.errorCode(), HEX);
Serial.print(',');
Serial.println(card.errorData(), HEX);
}

  while(1) {
    for (i=0; i<errno; i++) {
      digitalWrite(led1Pin, HIGH);
      digitalWrite(led2Pin, HIGH);
      delay(100);
      digitalWrite(led1Pin, LOW);
      digitalWrite(led2Pin, LOW);
      delay(100);
    }
    for (; i<10; i++) {
      delay(200);
    }
  }
*/
}

void setup() {
  WDTCSR |= (1 << WDCE) | (1 << WDE);
  WDTCSR = 0;
  Serial.begin(9600);
  microSerial.begin(MICRORATE);
  Wire.begin();
  WiFly.begin();
  WiFly.JoinRouter("DeviceTesting");
  
  bmp085Calibration();
  Serial.println("\r\nGPSlogger");
 // pinMode(led1Pin, OUTPUT);
 // pinMode(led2Pin, OUTPUT);
  pinMode(powerPin, OUTPUT);
  digitalWrite(powerPin, LOW);
  data = "";

  gpsSerial.begin(GPSRATE);
  
  Serial.println("Ready!");
  gpsSerial.print(SERIAL_SET);
  delay(250);

#if (LOG_DDM == 1)
     gpsSerial.print(DDM_ON);
#else
     gpsSerial.print(DDM_OFF);
#endif
  delay(250);
#if (LOG_GGA == 1)
    gpsSerial.print(GGA_ON);
#else
    gpsSerial.print(GGA_OFF);
#endif
  delay(250);
#if (LOG_GLL == 1)
    gpsSerial.print(GLL_ON);
#else
    gpsSerial.print(GLL_OFF);
#endif
  delay(250);
#if (LOG_GSA == 1)
    gpsSerial.print(GSA_ON);
#else
    gpsSerial.print(GSA_OFF);
#endif
  delay(250);
#if (LOG_GSV == 1)
    gpsSerial.print(GSV_ON);
#else
    gpsSerial.print(GSV_OFF);
#endif
  delay(250);
#if (LOG_RMC == 1)
    gpsSerial.print(RMC_ON);
#else
    gpsSerial.print(RMC_OFF);
#endif
  delay(250);

#if (LOG_VTG == 1)
    gpsSerial.print(VTG_ON);
#else
    gpsSerial.print(VTG_OFF);
#endif
  delay(250);

#if (USE_WAAS == 1)
    gpsSerial.print(WAAS_ON);
#else
    gpsSerial.print(WAAS_OFF);
#endif
Serial.println("Done!");
}

void loop() {
  
  
  
  if(start)
  {
     /* //////USED TO SEND DATA FROM SERIAL MONITOR TO SERVER  FOR TESTING ///////
     while(Serial.available()) 
      {
          SpiSerial.write(Serial.read());
      }
     */
    
      char c;
      uint8_t sum;

       if (gpsSerial.available()) 
       {
          c = gpsSerial.read();
        
          if (bufferidx == 0) 
          {
            while (c != '$')
            {
              c = gpsSerial.read(); 
            }
          }
          buffer[bufferidx] = c;
      
          if (c == '\n') 
          {
            buffer[bufferidx+1] = 0; // terminate it
      
            if (buffer[bufferidx-4] != '*') 
            {
              // no checksum?
              Serial.print('*');
              bufferidx = 0;
              return;
            }
            // get checksum
            sum = parseHex(buffer[bufferidx-3]) * 16;
            sum += parseHex(buffer[bufferidx-2]);
      
            // check checksum
            for (i=1; i < (bufferidx-4); i++) 
            {
              sum ^= buffer[i];
            }
            if (sum != 0) 
            {
              //putstring_nl("Cxsum mismatch");
              Serial.print('~');
              bufferidx = 0;
              return;
            }
            // got good data!
      
            if (strstr(buffer, "GPRMC")) 
            {
              // find out if we got a fix
              char *p = buffer;
              p = strchr(p, ',')+1;
              p = strchr(p, ',')+1; // skip to 3rd item
      
              if (p[0] == 'V') {
              //    digitalWrite(led1Pin, LOW);
                fix = 0;
              } else {
              //    digitalWrite(led1Pin, HIGH);
                fix = 1;
              }
            }
            if (LOG_RMC_FIXONLY) 
            {
              if (!fix) 
              {
                Serial.print('_');
                bufferidx = 0;
                return;
              }
            }
            
            Serial.print(buffer);
            Serial.print('#');
            
            bufferidx++;
         
            temperature = bmp085GetTemperature(bmp085ReadUT());
            pressure = bmp085GetPressure(bmp085ReadUP());
         
      
            data += temperature;
            data += ",";
            data += pressure;
           
            Serial.println(data);
           
            microSerial.println(data);
            microSerial.write((uint8_t *) buffer, bufferidx);
            SpiSerial.println(data);
            SpiSerial.write((uint8_t *) buffer, bufferidx);
            data = "";
      
            bufferidx = 0;
      
            // turn off GPS module?
            if (TURNOFFGPS) 
            {
              digitalWrite(powerPin, HIGH);
            }
      
            delay(SLEEPDELAY * 1000);
            digitalWrite(powerPin, LOW);
            return;
         }
      bufferidx++;
      if (bufferidx == BUFFSIZE-1) 
      {
         Serial.print('!');
         bufferidx = 0;
      }
    }
  }

////////////////////////Makes Connection to Server /////////////////////////
  else
  { 
      SpiSerial.println("open");
      delay(100);
      
      while(SpiSerial.available() > 0) 
      {
          char tempChar = SpiSerial.read();
          Serial.write(tempChar);
          if(tempChar == '#')
          {
           SpiSerial.print("DeviceName");
           SpiSerial.println(DeviceName);
          }
          else if(tempChar == '@')
          {
            delay (1000);
            start = true;
          }
      }
      delay(900);
  }
/////////////////////////////////////////////////// 
}
////////////////////////// Sleep Mode for GPS Not used currently ///////////////////////////////
void sleep_sec(uint8_t x) {
  while (x--) {
     // set the WDT to wake us up!
    WDTCSR |= (1 << WDCE) | (1 << WDE); // enable watchdog & enable changing it
    WDTCSR = (1<< WDE) | (1 <<WDP2) | (1 << WDP1);
    WDTCSR |= (1<< WDIE);
    set_sleep_mode(SLEEP_MODE_PWR_DOWN);
    sleep_enable();
    sleep_mode();
    sleep_disable();
  }
}

SIGNAL(WDT_vect) {
  WDTCSR |= (1 << WDCE) | (1 << WDE);
  WDTCSR = 0;
}

//////////////////////////////Calibration of the Barometric Sensor //////////////////////////
void bmp085Calibration()
{
  ac1 = bmp085ReadInt(0xAA);
  ac2 = bmp085ReadInt(0xAC);
  ac3 = bmp085ReadInt(0xAE);
  ac4 = bmp085ReadInt(0xB0);
  ac5 = bmp085ReadInt(0xB2);
  ac6 = bmp085ReadInt(0xB4);
  b1 = bmp085ReadInt(0xB6);
  b2 = bmp085ReadInt(0xB8);
  mb = bmp085ReadInt(0xBA);
  mc = bmp085ReadInt(0xBC);
  md = bmp085ReadInt(0xBE);
}

// Calculate temperature given ut.
// Value returned will be in units of 0.1 deg C
short bmp085GetTemperature(unsigned int ut)
{
  long x1, x2;
  
  x1 = (((long)ut - (long)ac6)*(long)ac5) >> 15;
  x2 = ((long)mc << 11)/(x1 + md);
  b5 = x1 + x2;

  return ((b5 + 8)>>4);  
}

// Calculate pressure given up
// calibration values must be known
// b5 is also required so bmp085GetTemperature(...) must be called first.
// Value returned will be pressure in units of Pa.
long bmp085GetPressure(unsigned long up)
{
  long x1, x2, x3, b3, b6, p;
  unsigned long b4, b7;
  
  b6 = b5 - 4000;
  // Calculate B3
  x1 = (b2 * (b6 * b6)>>12)>>11;
  x2 = (ac2 * b6)>>11;
  x3 = x1 + x2;
  b3 = (((((long)ac1)*4 + x3)<<OSS) + 2)>>2;
  
  // Calculate B4
  x1 = (ac3 * b6)>>13;
  x2 = (b1 * ((b6 * b6)>>12))>>16;
  x3 = ((x1 + x2) + 2)>>2;
  b4 = (ac4 * (unsigned long)(x3 + 32768))>>15;
  
  b7 = ((unsigned long)(up - b3) * (50000>>OSS));
  if (b7 < 0x80000000)
    p = (b7<<1)/b4;
  else
    p = (b7/b4)<<1;
    
  x1 = (p>>8) * (p>>8);
  x1 = (x1 * 3038)>>16;
  x2 = (-7357 * p)>>16;
  p += (x1 + x2 + 3791)>>4;
  
  return p;
}

// Read 1 byte from the BMP085 at 'address'
char bmp085Read(unsigned char address)
{
  unsigned char data;
  
  Wire.beginTransmission(BMP085_ADDRESS);
  Wire.write(address);
  Wire.endTransmission();
  
  Wire.requestFrom(BMP085_ADDRESS, 1);
  while(!Wire.available())
    ;
    
  return Wire.read();
}

// Read 2 bytes from the BMP085
// First byte will be from 'address'
// Second byte will be from 'address'+1
int bmp085ReadInt(unsigned char address)
{
  unsigned char msb, lsb;
  
  Wire.beginTransmission(BMP085_ADDRESS);
  Wire.write(address);
  Wire.endTransmission();
  
  Wire.requestFrom(BMP085_ADDRESS, 2);
  while(Wire.available()<2)
    ;
  msb = Wire.read();
  lsb = Wire.read();
  
  return (int) msb<<8 | lsb;
}

// Read the uncompensated temperature value
unsigned int bmp085ReadUT()
{
  unsigned int ut;
  
  // Write 0x2E into Register 0xF4
  // This requests a temperature reading
  Wire.beginTransmission(BMP085_ADDRESS);
  Wire.write(0xF4);
  Wire.write(0x2E);
  Wire.endTransmission();
  
  // Wait at least 4.5ms
  delay(5);
  
  // Read two bytes from registers 0xF6 and 0xF7
  ut = bmp085ReadInt(0xF6);
  return ut;
}

// Read the uncompensated pressure value
unsigned long bmp085ReadUP()
{
  unsigned char msb, lsb, xlsb;
  unsigned long up = 0;
  
  // Write 0x34+(OSS<<6) into register 0xF4
  // Request a pressure reading w/ oversampling setting
  Wire.beginTransmission(BMP085_ADDRESS);
  Wire.write(0xF4);
  Wire.write(0x34 + (OSS<<6));
  Wire.endTransmission();
  
  // Wait for conversion, delay time dependent on OSS
  delay(2 + (3<<OSS));
  
  // Read register 0xF6 (MSB), 0xF7 (LSB), and 0xF8 (XLSB)
  Wire.beginTransmission(BMP085_ADDRESS);
  Wire.write(0xF6);
  Wire.endTransmission();
  Wire.requestFrom(BMP085_ADDRESS, 3);
  
  // Wait for data to become available
  while(Wire.available() < 3)
    ;
  msb = Wire.read();
  lsb = Wire.read();
  xlsb = Wire.read();
  
  up = (((unsigned long) msb << 16) | ((unsigned long) lsb << 8) | (unsigned long) xlsb) >> (8-OSS);
  
  return up;
}

/* End code */





