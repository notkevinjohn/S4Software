#include "WiFly.h"

char tempChar;
char tempCommand[2];
int command = 0;
char tempPin[2];
int pin= 0;
char tempValue[4];
int value= 0;
boolean breakLoop;
char endChar;
long previousMillis = 0;
long interval = 1000;


void setup() {
  Serial.begin(9600);
  SpiSerial.begin();
  tempChar = ' ';
  endChar = ' ';
  Serial.println("begining");
  breakLoop = false;
  WiFly.begin();
  WiFly.JoinRouter("Zoe");
 // WiFly.AddAdhoc();
  Serial.println(WiFly.ip());
  Serial.println("Starting program");
  pinMode(5,OUTPUT);
  digitalWrite(5,LOW);
}

void loop()
{
   unsigned long currentMillis = millis();
   
   if(SpiSerial.available() > 0) 
   {
     
      tempChar = SpiSerial.read();
      while(SpiSerial.available() > 0 && tempChar != '!')
      {
            Serial.write(tempChar);
            tempChar = SpiSerial.read();
      }
      if(tempChar == '!')
      {
          delay(5);
          tempCommand[0] = SpiSerial.read();
          tempCommand[1] = SpiSerial.read();
          tempPin[0] = SpiSerial.read();
          tempPin[1] = SpiSerial.read();
          tempValue[0] = SpiSerial.read();
          tempValue[1] = SpiSerial.read();
          tempValue[2] = SpiSerial.read();
          tempValue[3] = SpiSerial.read();
          endChar = SpiSerial.read();
          command = atol(tempCommand);
          pin = atol(tempPin);
          value = atol (tempValue);
 
          if(endChar == '@')
          {
              switch (command)
              {
              case 1:
                    digitalWrite(pin,value);
                        //SpiSerial.print("Pin ");
                        //SpiSerial.print(pin);
                        //SpiSerial.print(": ");
                        if(value == 1)
                        {
                            //SpiSerial.println("HIGH");
                        }
                        else
                        {
                          //SpiSerial.println("LOW");
                    break;
              case 2:
                    //SpiSerial.println(digitalRead(pin));
                    break;
              case 3:
                    //SpiSerial.println(analogRead(pin));
                    Serial.println(analogRead(pin));
                    break;
              case 4:
                    analogWrite(pin,value);
                    //SpiSerial.print("Pin ");
                    //SpiSerial.print(pin);
                    //SpiSerial.print(": ");
                    //SpiSerial.println(value);
                  break;
              case 5:
                    delay(value);
                    //SpiSerial.print("Delay: ");
                    //SpiSerial.println(value);
                    break;
              case 6:
                  if(value == 1)
                  {
                      pinMode(pin,OUTPUT);
                      //SpiSerial.print("Pin output changed");
                  }
                  else
                  {
                      pinMode(pin,INPUT);
                  }
               }
              }
               if(currentMillis - previousMillis > interval) 
               {
                   previousMillis = currentMillis;   
                   SpiSerial.print("#");
               }
          }
          else
          {
            SpiSerial.println("Bad Command");
          }
      }
  }
  else
  {
     if(currentMillis - previousMillis > interval) 
     {
         previousMillis = currentMillis;   
         SpiSerial.print("#");
     }
  }
}
          

