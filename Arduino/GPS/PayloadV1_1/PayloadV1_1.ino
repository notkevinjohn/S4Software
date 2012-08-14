
//////// Librarys Needed //////////
#include <SD.h>
#include <TinyGPS.h>
TinyGPS gps;
#include <SoftwareSerial.h>

//////// Pin Declaration ///////////
int chipSelect = 4;
int warning = 13;
int rayBurst = 5;
int Sensor1 = A0;
int Sensor2 = A1;
int Sensor3 = A2;

//////// Variable Declaration ///////// 
unsigned long time;
int sensor1, sensor2, sensor3, rayburst;
String dataString, dataString1;  
File dataFileSensor;
String source;
int timeTick;

//////// GPS Definitions  /////////
#define RXPIN 2
#define TXPIN 3
#define GPSBAUD 4800
SoftwareSerial uart_gps(RXPIN, TXPIN);
void getgps(TinyGPS &gps);

//////// Function Declaration /////////
void writeToFile(String dataSensor, int num);
void stringNumber(int i);
void serialPrint(String currentDate, String currentTime, float longitude, float latitude, int currentSpeed, int currentAltitude);
void stringData();
void timeStamp(int time, int &timeTick);

/*************************************************************************************************
INITIAL SETUP
*************************************************************************************************/
void setup()
{ 
  /////// PinMaping //////
  pinMode(10, OUTPUT);
  pinMode(warning, OUTPUT);
  pinMode(rayBurst, INPUT);
  
  timeTick = 0;
  
  ///// Diagnostics //////
  Serial.begin(9600);
 
  Serial.print("Initializing SD Card...");

  if (!SD.begin(chipSelect)){
    Serial.println("Card failed, not present");
    digitalWrite(warning, HIGH);
    return;
  }
  Serial.println("card initialized.");
  
   uart_gps.begin(GPSBAUD);
    Serial.println("");
  Serial.println(" GPS QuickStart");
  Serial.println(" ...waiting for lock... ");
  Serial.println("");

}

/*************************************************************************************************
PROGRAM 
*************************************************************************************************/
void loop()
{ 
  
    while (uart_gps.available()) // While there is data on the RX pin...
  {
      int c = uart_gps.read(); // load the data into a variable...
      if (gps.encode(c)) // if there is a new valid sentence...
      {
          getgps(gps); // then grab the data.
          delay(10);
      }
  }
 


}

/*************************************************************************************************
FUNCTIONS FOR THE GPS PROGRAM
*************************************************************************************************/
void getgps(TinyGPS &gps)
{
  String currentDate, currentTime;
  int year, currentSpeed, currentAltitude;
  byte day, month, hour, minute, second, hundredths;
  float longitude, latitude;

  gpsDateTime(year, month, day, hour, minute, second, hundredths);
  
  gpsDate(year, month, day, currentDate);
  gpsTime(hour, minute, second, currentTime);
  gpsSpeed(gps, currentSpeed);
  gpsLocation(longitude, latitude);
  gpsAltitude(gps, currentAltitude);
  

  serialPrint(time, dataString1, currentDate, currentTime, longitude, latitude, currentSpeed, currentAltitude);
  //writeToFile(currentDate,  currentTime,  longitude,  latitude,  currentSpeed,  currentAltitude);


}     

void gpsDateTime(int &year, byte &month, byte &day, byte &hour, byte &minute, byte &second, byte &hundredths)
{
  int timeDiff;
  gps.crack_datetime(&year,&month,&day,&hour,&minute,&second,&hundredths);
  
  
  int localHour = hour - timeDiff;
  
  //You can get negative hours from the previous subtraction
  //In this case, just add 24 hours to be within a valid range again
  if (localHour < 0)
  {
    localHour = localHour + 24;
  }
}
 
void gpsDate(int year, int month, int day, String &currentDate)
{
  String slash;
  slash = "/";
  currentDate = String(month) + slash + String(day) + slash + String(year);
}

void gpsTime(int hour, int minute, int second, String &currentTime)
{
  String Seperate;
  Seperate = ",";
  currentTime = String(hour) + Seperate + String(minute) + Seperate + String(second);
}


void gpsLocation(float &longitude, float &latitude)
{
  gps.f_get_position(&latitude, &longitude);
}
  
  
 void gpsSpeed(TinyGPS &gps, int &currentSpd)
{
  currentSpd = gps.f_speed_kmph();
}


void gpsAltitude(TinyGPS &gps, int &currentAltitude)
{
  currentAltitude = gps.f_altitude();
}
  

////////////////////////////////////////////////////////////////////////////////




////////////////////////////////////////////////////////////////////////////////




////////////////////////////////////////////////////////////////////////////////








/*************************************************************************************************
FUNCTIONS FOR THE PROGRAM
*************************************************************************************************/









   void stringData()
   {  
      dataString1 =  "HI";
      
   }

   void timeStamp(int time, int &timeTick)
   {
     int priorTime, triggerTime;
     
     if(priorTime <= triggerTime){
       triggerTime = time;
     }else if(priorTime >= triggerTime){
       priorTime = time + 100;       //////////// Time delay
       timeTick++;
     }
   }
       
     
   void serialPrint(unsigned long time, String dataString1, String currentDate, String currentTime, float longitude, float latitude, int currentSpeed, int currentAltitude)
   {
        Serial.print(currentDate);
        Serial.print(",");
        Serial.print(currentTime);
        Serial.print(",");
        Serial.print(longitude);
        Serial.print(",");
        Serial.print(latitude);
        Serial.print(",");
        Serial.print(currentSpeed);
        Serial.print(",");
        Serial.println(currentAltitude);

  
   }
     
   void writeToFile(String currentDate, String currentTime, float longitude, float latitude, int currentSpeed, int currentAltitude)
   {

     File dataFile = SD.open("PData.txt", FILE_WRITE);
     if(dataFile){
        dataFile.print(currentDate);
        dataFile.print(",");
        dataFile.print(currentTime);
        dataFile.print(",");
        dataFile.print(longitude);
        dataFile.print(",");
        dataFile.print(latitude);
        dataFile.print(",");
        dataFile.print(currentSpeed);
        dataFile.print(",");
        dataFile.println(currentAltitude);
        dataFile.close();
      } else{
      Serial.print("error opening ");
      Serial.print("PayloadData.txt");
      }
      
   }
 
 
  void cosmicRay(int rayBurst)
  {
    if(rayBurst == HIGH)
    {
      rayburst = 1;
    }else{
      rayburst = 0;
    }
  }    

 

