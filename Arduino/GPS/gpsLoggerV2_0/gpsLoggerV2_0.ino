// Ladyada's logger modified by Bill Greiman to use the SdFat library

// this is a generic logger that does checksum testing so the data written should be always good
// Assumes a sirf III chipset logger attached to pin 2 and 3

#include <SD.h>
#include <avr/sleep.h>
#include "GPSconfig.h"
#include <stdio.h>

// If using Arduino IDE prior to 1.0,
// make sure to install newsoftserial from Mikal Hart
// http://arduiniana.org/libraries/NewSoftSerial/


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



// Use pins 2 and 3 to talk to the GPS. 2 is the TX pin, 3 is the RX pin

// Set the GPSRATE to the baud rate of the GPS module. Most are 4800
// but some are 38400 or other. Check the datasheet!
#define GPSRATE 4800

// Set the pins used
#define powerPin 4
#define led1Pin 5
#define led2Pin 6
#define chipSelect 10


#define BUFFSIZE 90
char buffer[BUFFSIZE];
char buffer2[BUFFSIZE];
uint8_t bufferidx = 0;
uint8_t fix = 0; // current fix data
uint8_t i;
File logfile;
int sensor;
int sensor2;
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
*/
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
}

void setup() {
  WDTCSR |= (1 << WDCE) | (1 << WDE);
  WDTCSR = 0;
  Serial.begin(9600);
  Serial.println("\r\nGPSlogger");
  pinMode(led1Pin, OUTPUT);
  pinMode(led2Pin, OUTPUT);
  pinMode(powerPin, OUTPUT);
  digitalWrite(powerPin, LOW);

  // make sure that the default chip select pin is set to
  // output, even if you don't use it:
  pinMode(53, OUTPUT);
  pinMode(10,OUTPUT);
  
  // see if the card is present and can be initialized:
  if (!SD.begin(chipSelect)) {
    Serial.println("Card init. failed!");
    error(1);
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
    error(3);
  }
  Serial.print("Writing to "); Serial.println(buffer);
  
  // connect to the GPS at the desired rate
  Serial1.begin(GPSRATE);
  
  Serial.println("Ready!");
  
  Serial1.print(SERIAL_SET);
  delay(250);

#if (LOG_DDM == 1)
     Serial1.print(DDM_ON);
#else
     Serial1.print(DDM_OFF);
#endif
  delay(250);
#if (LOG_GGA == 1)
    Serial1.print(GGA_ON);
#else
    Serial1.print(GGA_OFF);
#endif
  delay(250);
#if (LOG_GLL == 1)
    Serial1.print(GLL_ON);
#else
    Serial1.print(GLL_OFF);
#endif
  delay(250);
#if (LOG_GSA == 1)
    Serial1.print(GSA_ON);
#else
    Serial1.print(GSA_OFF);
#endif
  delay(250);
#if (LOG_GSV == 1)
    Serial1.print(GSV_ON);
#else
    Serial1.print(GSV_OFF);
#endif
  delay(250);
#if (LOG_RMC == 1)
    Serial1.print(RMC_ON);
#else
    Serial1.print(RMC_OFF);
#endif
  delay(250);

#if (LOG_VTG == 1)
    Serial1.print(VTG_ON);
#else
    Serial1.print(VTG_OFF);
#endif
  delay(250);

#if (USE_WAAS == 1)
    Serial1.print(WAAS_ON);
#else
    Serial1.print(WAAS_OFF);
#endif
}

void loop() {
  //Serial.println(Serial.available(), DEC);
  char c;
  uint8_t sum;

  // read one 'line'
  if (Serial1.available()) {
    c = Serial1.read();
#if ARDUINO >= 100
    //Serial.write(c);
#else
    //Serial.print(c, BYTE);
#endif
    if (bufferidx == 0) {
      while (c != '$')
        c = Serial1.read(); // wait till we get a $
    }
    buffer[bufferidx] = c;

#if ARDUINO >= 100
    //Serial.write(c);
#else
    //Serial.print(c, BYTE);
#endif
    digitalWrite(led2Pin, HIGH); //////////////
    if (c == '\n') {
      //putstring_nl("EOL");
      //Serial.print(buffer);
      buffer[bufferidx+1] = 0; // terminate it

      if (buffer[bufferidx-4] != '*') {
        // no checksum?
        Serial.print('*');
        bufferidx = 0;
        return;
      }
      // get checksum
      sum = parseHex(buffer[bufferidx-3]) * 16;
      sum += parseHex(buffer[bufferidx-2]);

      // check checksum
      for (i=1; i < (bufferidx-4); i++) {
        sum ^= buffer[i];
      }
      if (sum != 0) {
        //putstring_nl("Cxsum mismatch");
        Serial.print('~');
        bufferidx = 0;
        return;
      }
      // got good data!

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
      if (LOG_RMC_FIXONLY) {
        if (!fix) {
          Serial.print('_');
          bufferidx = 0;
          return;
        }
      }
      // rad. lets log it!
      Serial.print(buffer);
      
      Serial.print('#');
       // sets the digital pin as output

      // Bill Greiman - need to write bufferidx + 1 bytes to getCR/LF
      bufferidx++;
   
   
  
     Serial.print(buffer2);
      logfile.write((uint8_t *) buffer, bufferidx);
      logfile.flush();
      
      /*
putstring_nl("can't write!");
error(4);
}
*/

      digitalWrite(led2Pin, LOW);

      bufferidx = 0;

      // turn off GPS module?
      if (TURNOFFGPS) {
        digitalWrite(powerPin, HIGH);
      }

      delay(SLEEPDELAY * 1000);
      digitalWrite(powerPin, LOW);
      return;
    }
    bufferidx++;
    if (bufferidx == BUFFSIZE-1) {
       Serial.print('!');
       bufferidx = 0;
    }

  } else {

  }

}

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

/* End code */



