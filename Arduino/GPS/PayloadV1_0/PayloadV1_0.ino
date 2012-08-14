#include <SD.h>
#include <TinyGPS.h>
TinyGPS gps;
#include <SoftwareSerial.h>

int chipSelect = 4;
int warning = 13;
unsigned long time;
int sensor1, sensor2, sensor3;
String dataString, dataString1, dataString2, dataString3;  
int Sensor1 = A0;
int Sensor2 = A1;
int Sensor3 = A2;
File dataFileSensor;
String source;

#define RXPIN 3
#define TXPIN 2
SoftwareSerial nss(RXPIN, TXPIN);

void writeToFile(String dataSensor, int num);
void stringNumber(int i);
void serialPrint(unsigned long time, String dataString1, String dataSting2, String dataSting3);
void stringData();

/*************************************************************************************************
INITIAL SETUP
*************************************************************************************************/
void setup()
{
  pinMode(10, OUTPUT);
  pinMode(warning, OUTPUT);
  
  Serial.begin(4800);
  Serial.print("Initializing SD Card...");

  if (!SD.begin(chipSelect)){
    Serial.println("Card failed, not present");
    digitalWrite(warning, HIGH);
    return;
  }
  Serial.println("card initialized.");
  
}

/*************************************************************************************************
PROGRAM 
*************************************************************************************************/
void loop()
{
  delay(100);
  time = millis();

 while (nss.available())
  {
    int c = nss.read();
    if (gps.encode(c))
    {
      // process new gps info here
    }
  }

  stringData();
  serialPrint(time, dataString1, dataString2, dataString3);
    
  for(int i=1; i <=3 ;i++){ 
    stringNumber(i);      
    writeToFile(dataString,i);
  }
}

/*************************************************************************************************
FUNCTIONS FOR THE PROGRAM
*************************************************************************************************/

   void stringData()
   {  dataString1 =  String(analogRead(sensor1));
  dataString2 =  String(analogRead(sensor2));
  dataString3 =  String(analogRead(sensor3));
   }

   void serialPrint(unsigned long time, String dataString1, String dataSting2, String dataSting3)
   {
      Serial.print(time);
      Serial.print(", ");
      Serial.print(dataString1);
      Serial.print(", ");
      Serial.print(dataString2);
      Serial.print(", ");
      Serial.println(dataString3);
   }
     
   void writeToFile(String dataString, int i)
   {
      char format[] = "senData%d.txt";
      char filename[sizeof format+100];
      sprintf(filename,format,i);

     File dataFile = SD.open(filename, FILE_WRITE);
     if(dataFile){
        dataFile.print(time);
        dataFile.print(", ");
        dataFile.println(dataString1);
        dataFile.close();
      } else{
      Serial.print("error opening ");
      Serial.print(filename);
      Serial.println(dataString);
      }
   }
  void stringNumber(int i){
      char formatData[] = "source%d";
      char source[sizeof formatData+100];
      sprintf(source,formatData,i);
      
      if(source == "source1"){
        dataString = dataString1;
      }else if (source == "source2"){
        dataString = dataString2;
      }else if (source == "source3"){
        dataString = dataString3;
      }
  }
  
  
 
