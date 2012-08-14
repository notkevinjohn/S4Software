//////// Librarys Needed //////////
#include <SD.h>
#include <SoftwareSerial.h>

//////// Pin Declaration ///////////
int chipSelect = 4;
int warning = 13;
int rayBurst = 5;
int Sensor1 = A0;
int Sensor2 = A1;
int Sensor3 = A2;
#define RXPIN 2
#define TXPIN 3
#define GPSBUAD 4800
String outputString;
byte gpsChar;

char myArray[300];


//////// Variable Declaration ///////// 
unsigned long time;
int sensor1, sensor2, sensor3, rayburst;
String dataString, dataString1;  
File dataFileSensor;
String source;
int timeTick;


String inputString = "";
boolean stringComplete = false;


//////// Function Declaration /////////
SoftwareSerial uart_gps(RXPIN, TXPIN);

void writeToFile(unsigned long time, String dataSensor, String inputString);
void stringNumber(int i);
void serialPrint(unsigned long time, String dataString1, String inputString);
void stringData();
void timeStamp(int time, int &timeTick);


void setup()
{ 
  /////// PinMaping //////
  pinMode(10, OUTPUT);
  pinMode(warning, OUTPUT);
  pinMode(rayBurst, INPUT);
  
  timeTick = 0;
  /// //// 
  uart_gps.begin(GPSBUAD);
  
  ///// Diagnostics //////
  Serial.begin(9600);
 
  Serial.print("Initializing SD Card...");

  if (!SD.begin(chipSelect)){
    Serial.println("Card failed, not present");
    digitalWrite(warning, HIGH);
    return;
  }
  Serial.println("card initialized.");
  

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
  time = millis();



}
   
   void stringData()
   {  
      dataString1 =  String("Hello");
      
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
       
     
   void serialPrint(unsigned long time, String dataString1, String outputString)
   {
        Serial.print(outputString);
   }
     
   void writeToFile(unsigned long time, String dataString, String outputString)
   {

     File dataFile = SD.open("Payl.txt", FILE_WRITE);
     if(dataFile){
        dataFile.println(outputString);
        dataFile.close();
      } else{
      Serial.print("error opening ");
      Serial.print("PayloadData.txt");
      Serial.println(outputString);
      }
   }
   
